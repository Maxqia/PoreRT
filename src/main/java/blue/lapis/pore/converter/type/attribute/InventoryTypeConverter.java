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


package blue.lapis.pore.converter.type.attribute;

import blue.lapis.pore.converter.type.TypeConverter;

import com.google.common.base.Converter;
import org.apache.commons.lang.NotImplementedException;
import org.bukkit.event.inventory.InventoryType;
import org.spongepowered.api.item.inventory.InventoryArchetype;
import org.spongepowered.api.item.inventory.InventoryArchetypes;

public class InventoryTypeConverter {
    private InventoryTypeConverter() {
    }

    private static final Converter<InventoryType, InventoryArchetype> CONVERTER =
            TypeConverter.builder(InventoryType.class, InventoryArchetype.class)
                    .add(InventoryType.CHEST, InventoryArchetypes.CHEST)
                    //.add(InventoryType.ENDER_CHEST, InventoryArchetypes.CHEST)
                    .add(InventoryType.DISPENSER, InventoryArchetypes.DISPENSER)
                    //.add(InventoryType.DROPPER, InventoryArchetypes.DISPENSER)
                    .add(InventoryType.FURNACE, InventoryArchetypes.FURNACE)
                    .add(InventoryType.WORKBENCH, InventoryArchetypes.WORKBENCH)
                    .add(InventoryType.CRAFTING, InventoryArchetypes.CRAFTING)
                    .add(InventoryType.ENCHANTING, InventoryArchetypes.ENCHANTING_TABLE)
                    .add(InventoryType.BREWING, InventoryArchetypes.BREWING_STAND)
                    .add(InventoryType.PLAYER, InventoryArchetypes.PLAYER)
                    //.add(InventoryType.CREATIVE, InventoryArchetypes.)
                    .add(InventoryType.MERCHANT, InventoryArchetypes.VILLAGER)
                    .add(InventoryType.ANVIL, InventoryArchetypes.ANVIL)
                    .add(InventoryType.BEACON, InventoryArchetypes.BEACON)
                    .add(InventoryType.HOPPER, InventoryArchetypes.HOPPER)
                    //.add(InventoryType, InventoryArchetypes)
                    .build();

    public static InventoryArchetype of(InventoryType type) {
        switch (type) {
            case ENDER_CHEST:
                return InventoryArchetypes.CHEST;
            case DROPPER:
                return InventoryArchetypes.DISPENSER;
            case CREATIVE:
                throw new NotImplementedException("Creative inventory not available!");
            default:
                return CONVERTER.convert(type);
        }
    }

    public static InventoryType of(InventoryArchetype type) {
        try {
            return CONVERTER.reverse().convert(type);
        } catch (UnsupportedOperationException e) {
            return InventoryType.CHEST;
        }
    }
}
