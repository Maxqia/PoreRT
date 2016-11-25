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
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.type.PistonTypes;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class PoreBlockPistonRetractEvent extends BlockPistonRetractEvent
    implements PoreEvent<ChangeBlockEvent.Pre> {

    private final ChangeBlockEvent.Pre handle;
    private final BlockSnapshot block;

    public PoreBlockPistonRetractEvent(ChangeBlockEvent.Pre handle, BlockSnapshot block) {
        super(null, new ArrayList<Block>(), null);
        this.handle = checkNotNull(handle, "handle");
        this.block = checkNotNull(block, "block");
    }

    public ChangeBlockEvent.Pre getHandle() {
        return handle;
    }

    @Override
    public Block getBlock() {
        return PoreBlock.of(block.getLocation().get());
    } //TODO check which block this is, the piston or the block moved

    @Override
    public boolean isSticky() {
        return block.get(Keys.PISTON_TYPE).get().equals(PistonTypes.STICKY);
    }

    @Override
    public BlockFace getDirection() {
        return DirectionConverter.of(block.get(Keys.DIRECTION).get());
    }

    @Override
    @Deprecated
    public org.bukkit.Location getRetractLocation() {
        return super.getRetractLocation();
    }

    @Override
    public List<Block> getBlocks() {
        ArrayList<Block> blocks = new ArrayList<Block>();
        for (Location<World> trans : getHandle().getLocations()) {
            blocks.add(PoreBlock.of(trans));
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
        PoreEventRegistry.register(PoreBlockPistonRetractEvent.class, ChangeBlockEvent.Pre.class, event -> {
            Optional<BlockSnapshot> block = event.getCause().get(NamedCause.SOURCE, BlockSnapshot.class);
            if (block.isPresent() && block.get().getExtendedState().getType().equals(BlockTypes.PISTON)) {
                if (block.get().get(Keys.EXTENDED).get()) {
                    return ImmutableList.of(new PoreBlockPistonRetractEvent(event, block.get()));
                }
            }
            return ImmutableList.of();
        });
    }

}
