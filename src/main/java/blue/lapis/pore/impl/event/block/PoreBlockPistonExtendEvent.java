/*
 * PoreRT - A Bukkit to Sponge Bridge
 *
 * Copyright (c) 2016, Maxqia <https://github.com/Maxqia> AGPLv3
 * Copyright (c) 2014-2016, Lapis <https://github.com/LapisBlue> MIT
 * Copyright (c) Contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * An exception applies to this license, see the LICENSE file in the main directory for more information.
 */

package blue.lapis.pore.impl.event.block;

import static com.google.common.base.Preconditions.checkNotNull;

import blue.lapis.pore.converter.type.world.DirectionConverter;
import blue.lapis.pore.event.PoreEvent;
import blue.lapis.pore.event.PoreEventRegistry;
import blue.lapis.pore.event.RegisterEvent;
import blue.lapis.pore.impl.block.PoreBlock;

import com.google.common.collect.ImmutableList;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.block.tileentity.Piston;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.PistonTypes;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.cause.NamedCause;

import java.util.ArrayList;
import java.util.List;

public final class PoreBlockPistonExtendEvent extends BlockPistonExtendEvent
    implements PoreEvent<ChangeBlockEvent.Post> {

    private final ChangeBlockEvent.Post handle;

    public PoreBlockPistonExtendEvent(ChangeBlockEvent.Post handle) {
        super(null, new ArrayList<Block>(), null);
        this.handle = checkNotNull(handle, "handle");
    }

    public ChangeBlockEvent.Post getHandle() {
        return handle;
    }

    public Piston getPiston() {
        return getHandle().getCause().get(NamedCause.SOURCE, Piston.class).get();
    }

    @Override
    public Block getBlock() {
        return PoreBlock.of(getPiston().getLocation());
    } //TODO check which block this is, the piston or the block moved

    @Override
    public boolean isSticky() {
        return getPiston().get(Keys.PISTON_TYPE).get().equals(PistonTypes.STICKY);
    }

    @Override
    public BlockFace getDirection() {
        return DirectionConverter.of(getPiston().getBlock().get(Keys.DIRECTION).get());
    }

    @Override
    public int getLength() {
        return getBlocks().size();
    }

    @Override
    public List<Block> getBlocks() {
        ArrayList<Block> blocks = new ArrayList<Block>();
        for (Transaction<BlockSnapshot> trans : getHandle().getTransactions()) {
            if (trans.getOriginal().getExtendedState().getType()
                    .toString().toLowerCase().contains("piston")) {
                continue;
            }
            blocks.add(PoreBlock.of(trans.getOriginal().getLocation().get()));
        }
        return ImmutableList.copyOf(blocks);
    }

    @Override
    public boolean isCancelled() {
        return getHandle().isCancelled();
    }

    @Override
    public void setCancelled(boolean cancelled) {
        getHandle().setCancelled(cancelled);
    }

    @Override
    public String toString() {
        return toStringHelper().toString();
    }

    @RegisterEvent
    public static void register() {
        PoreEventRegistry.register(PoreBlockPistonExtendEvent.class, ChangeBlockEvent.Post.class, event -> {
            for (Transaction<BlockSnapshot> trans : event.getTransactions()) {
                if (!trans.getOriginal().getExtendedState().getType().equals(BlockTypes.PISTON_HEAD)
                        && trans.getFinal().getExtendedState().getType().equals(BlockTypes.PISTON_HEAD)) {
                    return ImmutableList.of(new PoreBlockPistonExtendEvent(event));
                }
            }
            return ImmutableList.of();
        });
    }

}
