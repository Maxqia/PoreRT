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

package blue.lapis.pore.impl.event.player;

import static com.google.common.base.Preconditions.checkNotNull;

import blue.lapis.pore.converter.type.world.DirectionConverter;
import blue.lapis.pore.event.PoreEvent;
import blue.lapis.pore.event.RegisterEvent;
import blue.lapis.pore.event.Source;
import blue.lapis.pore.impl.block.PoreBlock;
import blue.lapis.pore.impl.entity.PorePlayer;

import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.property.entity.DominantHandProperty;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.block.InteractBlockEvent;

import java.util.Optional;

public abstract class PorePlayerInteractEvent<T extends InteractBlockEvent> extends PlayerInteractEvent
        implements PoreEvent<T> {

    private final T handle;
    private final Player player;

    private PorePlayerInteractEvent(T handle, @Source Player player) {
        super(null, null, null, null, null);
        this.handle = checkNotNull(handle, "handle");
        this.player = checkNotNull(player, "player");
    }

    @Override
    public T getHandle() {
        return this.handle;
    }

    @Override
    public org.bukkit.entity.Player getPlayer() {
        return PorePlayer.of(this.player);
    }

    @Override
    @SuppressWarnings("deprecation") //TODO fix this
    public ItemStack getItem() {
        return getPlayer().getItemInHand();
    }

    @Override
    public Material getMaterial() {
        ItemStack item = getItem();
        return item != null ? item.getType() : null;
    }

    @Override
    public boolean hasBlock() {
        return this.handle.getTargetBlock().getState().getType() != BlockTypes.AIR;
    }

    @Override
    public boolean hasItem() {
        return false; //TODO idk
    }

    @Override
    public boolean isBlockInHand() {
        return false; //TODO bug sponge devs about this
    }

    @Override
    public Block getClickedBlock() {
        return PoreBlock.of(handle.getTargetBlock().getLocation().orElse(null));
    }

    @Override
    public BlockFace getBlockFace() {
        return DirectionConverter.of(handle.getTargetSide());
    }

    @Override
    public Result useInteractedBlock() {
        return isCancelled() ? Result.DENY : Result.ALLOW; // TODO: This is kinda wrong
    }

    @Override
    public void setUseInteractedBlock(Result useInteractedBlock) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public Result useItemInHand() {
        return isCancelled() ? Result.DENY : Result.ALLOW; // TODO: This is kinda wrong
    }

    @Override
    public void setUseItemInHand(Result useItemInHand) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public boolean isCancelled() {
        return getHandle().isCancelled();
    }

    @Override
    public void setCancelled(boolean cancel) {
        getHandle().setCancelled(cancel);
    }

    @Override
    public String toString() {
        return toStringHelper().toString();
    }

    @Override
    public EquipmentSlot getHand() {
        Optional<DominantHandProperty> hand = player.getProperty(DominantHandProperty.class);
        return !hand.isPresent() || hand.get().getValue() == HandTypes.MAIN_HAND
                ? EquipmentSlot.HAND : EquipmentSlot.OFF_HAND;
    }

    @RegisterEvent
    public static final class Primary extends PorePlayerInteractEvent<InteractBlockEvent.Primary> {

        public Primary(InteractBlockEvent.Primary handle, @Source Player player) {
            super(handle, player);
        }

        @Override
        public Action getAction() {
            return hasBlock() ? Action.LEFT_CLICK_BLOCK : Action.LEFT_CLICK_AIR;
        }

    }

    @RegisterEvent
    public static final class Secondary extends PorePlayerInteractEvent<InteractBlockEvent.Secondary> {

        public Secondary(InteractBlockEvent.Secondary handle, @Source Player player) {
            super(handle, player);
        }

        @Override
        public Action getAction() {
            return hasBlock() ? Action.RIGHT_CLICK_BLOCK : Action.RIGHT_CLICK_AIR;
        }

    }

}
