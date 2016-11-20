/*
 * PoreRT - A Bukkit to Sponge Bridge
 *
 * Copyright (c) 2016, Maxqia <https://github.com/Maxqia> AGPLv3
 * Copyright (c) 2014-2016, Lapis <https://github.com/LapisBlue> MIT
 * Copyright (c) Spigot/Craftbukkit Project <https://hub.spigotmc.org/stash/projects/SPIGOT> LGPLv3
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

package blue.lapis.pore.impl.inventory;

import blue.lapis.pore.converter.type.material.DurabilityConverter;
import blue.lapis.pore.converter.type.material.ItemStackConverter;
import blue.lapis.pore.converter.type.material.MaterialConverter;
import blue.lapis.pore.converter.wrapper.WrapperConverter;
import blue.lapis.pore.impl.entity.PorePlayer;

import com.google.common.collect.Iterables;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.spongepowered.api.item.inventory.Slot;
import org.spongepowered.api.item.inventory.entity.Hotbar;
import org.spongepowered.api.item.inventory.entity.PlayerInventory;
import org.spongepowered.api.item.inventory.equipment.EquipmentTypes;
import org.spongepowered.api.item.inventory.property.SlotIndex;
import org.spongepowered.common.item.inventory.util.ItemStackUtil;

import java.util.Optional;

public class PorePlayerInventory extends PoreInventory implements org.bukkit.inventory.PlayerInventory {

    public static PorePlayerInventory of(PlayerInventory handle) {
        return WrapperConverter.of(PorePlayerInventory.class, handle);
    }

    protected PorePlayerInventory(PlayerInventory handle) {
        super(handle);
    }

    @Override
    public PlayerInventory getHandle() {
        return (PlayerInventory) super.getHandle();
    }

    @Override
    public ItemStack[] getArmorContents() {
        return new ItemStack[]{this.getHelmet(), this.getChestplate(), this.getLeggings(), this.getBoots()};
    }

    @Override
    public ItemStack getHelmet() {
        return ItemStackConverter.of(ItemStackUtil.fromNative(((EntityPlayerMP)this.getHandle()
                .getCarrier().orElse(null)).getItemStackFromSlot(EntityEquipmentSlot.HEAD)));
    } // .getItemInHand() and .peek() returns a copy instead of the original itemstack

    @Override
    public ItemStack getChestplate() {
        return ItemStackConverter.of(ItemStackUtil.fromNative(((EntityPlayerMP)this.getHandle()
                .getCarrier().orElse(null)).getItemStackFromSlot(EntityEquipmentSlot.CHEST)));
    } // .getItemInHand() and .peek() returns a copy instead of the original itemstack

    @Override
    public ItemStack getLeggings() {
        return ItemStackConverter.of(ItemStackUtil.fromNative(((EntityPlayerMP)this.getHandle()
                .getCarrier().orElse(null)).getItemStackFromSlot(EntityEquipmentSlot.LEGS)));
    } // .getItemInHand() and .peek() returns a copy instead of the original itemstack

    @Override
    public ItemStack getBoots() {
        return ItemStackConverter.of(ItemStackUtil.fromNative(((EntityPlayerMP)this.getHandle()
                .getCarrier().orElse(null)).getItemStackFromSlot(EntityEquipmentSlot.FEET)));
    } // .getItemInHand() and .peek() returns a copy instead of the original itemstack

    @Override
    public void setArmorContents(ItemStack[] items) {
        final int length = items.length;
        if (length > 0) {
            this.setHelmet(items[0]);
        }
        if (length > 1) {
            this.setChestplate(items[1]);
        }
        if (length > 2) {
            this.setLeggings(items[2]);
        }
        if (length > 3) {
            this.setBoots(items[3]);
        }
    }

    @Override
    public void setHelmet(ItemStack helmet) {
        // this code relies on the notion that Mojang won't implement hydra-people or something
        Iterables.get(this.getHandle().query(EquipmentTypes.HEADWEAR).<Slot>slots(), 0)
                .set(ItemStackConverter.of(helmet));
    }

    @Override
    public void setChestplate(ItemStack chestplate) {
        Iterables.get(this.getHandle().query(EquipmentTypes.CHESTPLATE).<Slot>slots(), 0)
                .set(ItemStackConverter.of(chestplate));
    }

    @Override
    public void setLeggings(ItemStack leggings) {
        Iterables.get(this.getHandle().query(EquipmentTypes.LEGGINGS).<Slot>slots(), 0)
                .set(ItemStackConverter.of(leggings));
    }

    @Override
    public void setBoots(ItemStack boots) {
        Iterables.get(this.getHandle().query(EquipmentTypes.BOOTS).<Slot>slots(), 0)
                .set(ItemStackConverter.of(boots));
    }

    @Override
    public ItemStack getItemInHand() {
        return this.getItemInMainHand();
    }

    @Override
    public void setItemInHand(ItemStack stack) {
        this.setItemInMainHand(stack);
    }

    @Override
    public int getHeldItemSlot() {
        return this.getHandle().getHotbar().getSelectedSlotIndex();
    }

    @Override
    public void setHeldItemSlot(int slot) {
        Validate.isTrue(slot >= 0 || slot <= 8, "Invalid hotbar slot index");
        this.getHandle().getHotbar().setSelectedSlotIndex(slot);
    }

    @Override
    @SuppressWarnings("deprecation")
    public int clear(int id, int data) {
        int removed = 0;
        for (Slot slot : this.getHandle().<Slot>slots()) {
            Optional<org.spongepowered.api.item.inventory.ItemStack> stackOptional = slot.peek();
            if (stackOptional.isPresent()) {
                org.spongepowered.api.item.inventory.ItemStack stack = stackOptional.get();
                if (id == -1 || stack.getItem() == MaterialConverter.asItem(Material.getMaterial(id))) {
                    int damage = DurabilityConverter.getDamageValue(stack);
                    if (data == -1 || damage == data) {
                        removed += stack.getQuantity();
                        slot.clear();
                    }
                }
            }
        }
        return removed;
    }

    @Override
    public Player getHolder() {
        if (this.getHandle().getCarrier().isPresent()) {
            if (this.getHandle().getCarrier().get() instanceof org.spongepowered.api.entity.living.player.Player) {
                return PorePlayer.of(this.getHandle().getCarrier().get());
            }
        }
        return null;
    }

    @Override
    public ItemStack[] getExtraContents() {
        // TODO Auto-generated method stub
        throw new NotImplementedException("TODO");
    }

    @Override
    public void setExtraContents(ItemStack[] items) {
        // TODO Auto-generated method stub
        throw new NotImplementedException("TODO");
    }

    @Override
    public ItemStack getItemInMainHand() {
        return ItemStackConverter.of(ItemStackUtil.fromNative(((EntityPlayerMP)
                this.getHandle().getCarrier().orElse(null)).getHeldItemMainhand()));
    } // .getItemInHand() and .peek() returns a copy instead of the original itemstack

    @Override
    public void setItemInMainHand(ItemStack item) {
        Hotbar hotbar = getHandle().getHotbar();
        Optional<Slot> mainHand = hotbar.getSlot(SlotIndex.of(hotbar.getSelectedSlotIndex()));
        mainHand.get().offer(ItemStackConverter.of(item));
    }

    @Override
    public ItemStack getItemInOffHand() {
        return ItemStackConverter.of(ItemStackUtil.fromNative(((EntityPlayerMP)
                this.getHandle().getCarrier().orElse(null)).getHeldItemOffhand()));
    } // .getItemInHand() and .peek() returns a copy instead of the original itemstack

    @Override
    public void setItemInOffHand(ItemStack item) {
        getHandle().getOffhand().set(ItemStackConverter.of(item));
    }

    @Override
    protected net.minecraft.item.ItemStack[] getInternalContents() {
        System.out.println(getHandle().getClass());
        InventoryPlayer inv = (InventoryPlayer) getHandle();
        net.minecraft.item.ItemStack[] combined = new net.minecraft.item.ItemStack[
                     inv.mainInventory.length + inv.armorInventory.length + inv.offHandInventory.length];
        System.arraycopy(inv.mainInventory, 0, combined, 0, inv.mainInventory.length);
        System.arraycopy(inv.armorInventory, 0, combined, inv.mainInventory.length, inv.armorInventory.length);
        System.arraycopy(inv.offHandInventory, 0, combined,
                inv.mainInventory.length + inv.armorInventory.length, inv.offHandInventory.length);
        return combined;
    }

    @Override
    public InventoryType getType() {
        return InventoryType.PLAYER;
    }
}
