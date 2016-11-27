/*
 * PoreRT - A Bukkit to Sponge Bridge
 *
 * Copyright (c) 2016, Maxqia <https://github.com/Maxqia> AGPLv3
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

package blue.lapis.pore.impl.enchantments;

import blue.lapis.pore.converter.type.material.ItemStackConverter;

import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;
import org.spongepowered.api.item.Enchantment;

public class PoreEnchantment extends org.bukkit.enchantments.Enchantment {

    private Enchantment handle;

    public PoreEnchantment(Enchantment enchantment) {
        super(net.minecraft.enchantment.Enchantment.getEnchantmentID(
                (net.minecraft.enchantment.Enchantment) enchantment));
        this.handle = enchantment;
    }

    public Enchantment getHandle() {
        return this.handle;
    }

    @Override
    public String getName() {
        return getHandle().getName();
    }

    @Override
    public int getMaxLevel() {
        return getHandle().getMaximumLevel();
    }

    @Override
    public int getStartLevel() {
        return getHandle().getMinimumLevel();
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        switch (((net.minecraft.enchantment.Enchantment)getHandle()).type) {
        case ALL:
            return EnchantmentTarget.ALL;
        case ARMOR:
            return EnchantmentTarget.ARMOR;
        case ARMOR_FEET:
            return EnchantmentTarget.ARMOR_FEET;
        case ARMOR_HEAD:
            return EnchantmentTarget.ARMOR_HEAD;
        case ARMOR_LEGS:
            return EnchantmentTarget.ARMOR_LEGS;
        case ARMOR_CHEST:
            return EnchantmentTarget.ARMOR_TORSO;
        case DIGGER:
            return EnchantmentTarget.TOOL;
        case WEAPON:
            return EnchantmentTarget.WEAPON;
        case BOW:
            return EnchantmentTarget.BOW;
        case FISHING_ROD:
            return EnchantmentTarget.FISHING_ROD;
        case BREAKABLE:
            return EnchantmentTarget.BREAKABLE;
        default:
            return null;
        }
    } // Taken from CB, no Sponge equivalent

    @Override
    public boolean isTreasure() {
        return ((net.minecraft.enchantment.Enchantment) getHandle()).isTreasureEnchantment();
    }

    @Override
    public boolean conflictsWith(org.bukkit.enchantments.Enchantment other) {
        return getHandle().isCompatibleWith(((PoreEnchantment) other).getHandle());
    }

    @Override
    public boolean canEnchantItem(ItemStack item) {
        return getHandle().canBeAppliedToStack(ItemStackConverter.of(item));
    }

}
