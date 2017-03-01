/*
 * Pore(RT)
 * Copyright (c) 2016-2017, Maxqia <https://github.com/Maxqia> AGPLv3
 * Copyright (c) 2014-2016, Lapis <https://github.com/LapisBlue>
 * Copyright (c) 2014-2016, Contributors
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

import blue.lapis.pore.converter.wrapper.WrapperConverter;

import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.inventory.AnvilInventory;
import org.spongepowered.api.item.inventory.Inventory;

public class PoreAnvilInventory extends PoreInventory implements AnvilInventory {

    public static PoreAnvilInventory of(Inventory handle) {
        return WrapperConverter.of(PoreAnvilInventory.class, handle);
    }

    protected PoreAnvilInventory(Inventory handle) {
        super(handle);
    }


    @Override
    public String getRenameText() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public int getRepairCost() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public void setRepairCost(int levels) {
        throw new NotImplementedException("TODO");
    }
}
