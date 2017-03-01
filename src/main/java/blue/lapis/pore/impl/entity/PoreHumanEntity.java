/*
 * PoreRT - A Bukkit to Sponge Bridge
 *
 * Copyright (c) 2016-2017, Maxqia <https://github.com/Maxqia> AGPLv3
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

package blue.lapis.pore.impl.entity;

import static org.spongepowered.api.data.manipulator.catalog.CatalogEntityData.EXPERIENCE_HOLDER_DATA;

import blue.lapis.pore.converter.type.entity.player.GameModeConverter;
import blue.lapis.pore.converter.vector.LocationConverter;
import blue.lapis.pore.converter.wrapper.WrapperConverter;
import blue.lapis.pore.impl.inventory.PoreInventory;
import blue.lapis.pore.impl.inventory.PoreInventoryView;
import blue.lapis.pore.impl.inventory.PorePlayerInventory;

import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.GameMode;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MainHand;
import org.bukkit.inventory.Merchant;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.Humanoid;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.item.inventory.Carrier;
import org.spongepowered.api.item.inventory.entity.PlayerInventory;
import org.spongepowered.api.world.Location;

public class PoreHumanEntity extends PoreLivingEntity implements HumanEntity {

    protected PorePlayerInventory inventory;

    public static PoreHumanEntity of(Humanoid handle) {
        return WrapperConverter.of(PoreHumanEntity.class, handle);
    }

    protected PoreHumanEntity(Humanoid handle) {
        super(handle);
    }

    @Override
    public Humanoid getHandle() {
        return (Humanoid) super.getHandle();
    }

    @Override
    public String getName() {
        return getHandle().getName();
    }

    @Override
    public org.bukkit.inventory.PlayerInventory getInventory() {
        return PorePlayerInventory.of((PlayerInventory) this.getHandle().getInventory());
    }

    @Override
    public Inventory getEnderChest() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public boolean setWindowProperty(InventoryView.Property prop, int value) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public InventoryView getOpenInventory() {
        if (getHandle() instanceof org.spongepowered.api.entity.living.player.Player) {
            return PoreInventoryView.builder()
                    .setPlayer(this)
                    .setTopInventory(((org.spongepowered.api.entity.living.player.Player) getHandle())
                            .getOpenInventory().orElse(null))
                    .setBottomInventory(this.getHandle().getInventory())
                    .build();
        }

        // TODO
        throw new NotImplementedException("TODO");
    }

    @Override
    public InventoryView openInventory(Inventory inventory) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public void openInventory(InventoryView inventory) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public InventoryView openWorkbench(org.bukkit.Location location, boolean force) {
        Location<?> block = LocationConverter.of(location);
        if (force || block.getBlockType() == BlockTypes.CRAFTING_TABLE) {
            return openBlockInventory(block, force);
        }
        return null;
    }

    @Override
    public InventoryView openEnchanting(org.bukkit.Location location, boolean force) {
        Location<?> block = LocationConverter.of(location);
        if (force || block.getBlockType() == BlockTypes.ENCHANTING_TABLE) {
            return openBlockInventory(block, force);
        }
        return null;
    }

    private InventoryView openBlockInventory(Location<?> location, boolean force) {
        if (location.getTileEntity().isPresent() && location.getTileEntity().get() instanceof Carrier) {
            return this.openInventory(PoreInventory.of(
                    ((Carrier) location.getTileEntity().get()).getInventory()
            ));
        } else {
            throw new UnsupportedOperationException("Block is not an inventory carrier");
        }
    }

    @Override
    public void closeInventory() {
        if (getHandle() instanceof org.spongepowered.api.entity.living.player.Player) {
            ((org.spongepowered.api.entity.living.player.Player) getHandle())
                .closeInventory(Cause.of(NamedCause.source(this)));
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public ItemStack getItemInHand() {
        return getInventory().getItemInHand();
    }

    @Override
    @SuppressWarnings("deprecation")
    public void setItemInHand(ItemStack item) {
        getInventory().setItemInHand(item);
    }

    @Override
    public ItemStack getItemOnCursor() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public void setItemOnCursor(ItemStack item) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public boolean isSleeping() {
        return getHandle().get(Keys.IS_SLEEPING).get();
    }

    @Override
    public int getSleepTicks() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public GameMode getGameMode() {
        return GameModeConverter.of(getHandle().get(Keys.GAME_MODE).get());
    }

    @Override
    public void setGameMode(GameMode mode) {
        getHandle().offer(Keys.GAME_MODE, GameModeConverter.of(mode));
    }

    @Override
    public boolean isBlocking() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public int getExpToLevel() {
        return getHandle().get(EXPERIENCE_HOLDER_DATA).get().getExperienceBetweenLevels().get()
                - getHandle().get(EXPERIENCE_HOLDER_DATA).get().experienceSinceLevel().get();
    }

    @Override
    public MainHand getMainHand() {
        throw new NotImplementedException("CANTDO"); // Sponge API doesn't have this
    }

    @Override
    public InventoryView openMerchant(Villager trader, boolean force) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public boolean isHandRaised() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public InventoryView openMerchant(Merchant merchant, boolean force) {
        throw new NotImplementedException("TODO");
    }
}
