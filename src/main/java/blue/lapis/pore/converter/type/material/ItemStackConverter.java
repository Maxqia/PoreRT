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

package blue.lapis.pore.converter.type.material;

import blue.lapis.pore.impl.inventory.PoreItemStack;
import blue.lapis.pore.impl.inventory.meta.PoreItemMeta;

import org.bukkit.Material;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.DataManipulator;
import org.spongepowered.api.data.property.item.UseLimitProperty;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;

import java.util.Optional;

public final class ItemStackConverter {

    private ItemStackConverter() {
    }

    public static org.bukkit.inventory.ItemStack of(org.spongepowered.api.item.inventory.ItemStack stack) {
        if (stack == null) {
            return new org.bukkit.inventory.ItemStack(Material.AIR);
        }
        return new PoreItemStack(stack);
    }

    public static org.spongepowered.api.item.inventory.ItemStack of(org.bukkit.inventory.ItemStack stack) {
        if (stack instanceof PoreItemStack) {
            return ((PoreItemStack) stack).getHandle();
        }

        if (stack == null) {
            return null;
        }
        ItemType type = MaterialConverter.asItem(stack.getType());
        if (type == null) {
            throw new UnsupportedOperationException();
        }
        // IntelliJ doesn't recognize the above check and thinks withItemType() may throw an NPE
        //noinspection ConstantConditions
        ItemStack.Builder builder = ItemStack.builder()
                .fromItemStack(((PoreItemMeta)stack.getItemMeta()).getHandle())
                .itemType(type)
                .quantity(stack.getAmount());
                //.maxQuantity(stack.getType().getMaxStackSize()) //TODO

        Optional<UseLimitProperty> useLimit = type.getDefaultProperty(UseLimitProperty.class);
        if (useLimit.isPresent()) {
            builder = builder.add(Keys.ITEM_DURABILITY, useLimit.get().getValue() - stack.getDurability());
        }

        DataManipulator<?,?> maniuplator = DurabilityConverter.getItemData(stack);
        if (maniuplator != null) {
            builder = builder.itemData(maniuplator);
        }

        return builder.build();
    }

}
