/*
 * Pore(RT)
 * Copyright (c) 2014-2016, Lapis <https://github.com/LapisBlue>
 * Copyright (c) 2014-2016, Contributors
 *
 * The MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package blue.lapis.pore.converter.type.attribute;

import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.event.inventory.InventoryType;
import org.spongepowered.api.item.inventory.InventoryArchetype;
import org.spongepowered.api.item.inventory.InventoryArchetypes;

public class InventoryTypeConverter {
    private InventoryTypeConverter() {
    }

    public static InventoryArchetype of(InventoryType type) {
        switch (type) {
            case CHEST:
            case ENDER_CHEST:
                return InventoryArchetypes.CHEST;
            case DISPENSER:
            case DROPPER:
                return InventoryArchetypes.DISPENSER;
            case FURNACE:
                return InventoryArchetypes.FURNACE;
            case WORKBENCH:
                return InventoryArchetypes.WORKBENCH;
            case CRAFTING:
                return InventoryArchetypes.CRAFTING;
            case ENCHANTING:
                return InventoryArchetypes.ENCHANTING_TABLE;
            case BREWING:
                return InventoryArchetypes.BREWING_STAND;
            case PLAYER:
                return InventoryArchetypes.PLAYER;
            case CREATIVE:
                throw new NotImplementedException("Creative inventory not available!");
            case MERCHANT:
                return InventoryArchetypes.VILLAGER;
            case ANVIL:
                return InventoryArchetypes.ANVIL;
            case BEACON:
                return InventoryArchetypes.BEACON;
            case HOPPER:
                return InventoryArchetypes.HOPPER;
            default:
                throw new NotImplementedException(type.name());
        }
    }
}
