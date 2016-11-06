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

import blue.lapis.pore.converter.type.material.MaterialConverter;
import blue.lapis.pore.impl.inventory.meta.PoreItemMeta;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFactory;
import org.bukkit.inventory.meta.ItemMeta;
import org.spongepowered.api.item.inventory.ItemStack;

public class PoreItemFactory implements ItemFactory {

    @Override
    public ItemMeta getItemMeta(Material material) {
        return new PoreItemMeta(ItemStack.of(MaterialConverter.asItem(material), 1));
    }

    @Override
    public boolean isApplicable(ItemMeta meta, org.bukkit.inventory.ItemStack stack) throws IllegalArgumentException {
        return isApplicable(meta, stack.getType());
    }

    @Override
    public boolean isApplicable(ItemMeta meta, Material material) throws IllegalArgumentException {
        /*if (meta instanceof PoreItemMeta) {
            PoreItemMeta internalMeta = (PoreItemMeta) meta;
            DataTransactionResult result = ItemStack.of(MaterialConverter.asItem(material), 1)
                    .copyFrom(internalMeta.getHandle().copy());
            return result.isSuccessful();
        }
        throw new IllegalArgumentException("ItemMeta not from this ItemFactory!");*/
        return true; //TODO
    }

    @Override
    public boolean equals(ItemMeta meta1, ItemMeta meta2) throws IllegalArgumentException {
        //return (meta1 == meta2);
        return true; //TODO
    }

    @Override
    public ItemMeta asMetaFor(ItemMeta meta, org.bukkit.inventory.ItemStack stack) throws IllegalArgumentException {
        return asMetaFor(meta, stack.getType());
    }

    @Override
    public ItemMeta asMetaFor(ItemMeta meta, Material material) throws IllegalArgumentException {
        /*if (meta instanceof PoreItemMeta) {
            PoreItemMeta internalMeta = (PoreItemMeta) meta;
            ItemStack holder = ItemStack.of(MaterialConverter.asItem(material), 1);
            holder.copyFrom(internalMeta.getHandle().copy());
            return new PoreItemMeta(holder);
        }
        throw new IllegalArgumentException("ItemMeta not from this ItemFactory!");*/
        return meta; //TODO
    }

    @Override // Taken from CB
    public Color getDefaultLeatherColor() {
        return Color.fromRGB(0xA06540);
    }
}
