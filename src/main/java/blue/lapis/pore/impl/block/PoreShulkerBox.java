/*
 * PoreRT - A Bukkit to Sponge Bridge
 *
 * Copyright (c) 2016, Maxqia <https://github.com/Maxqia> AGPLv3
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

package blue.lapis.pore.impl.block;

import blue.lapis.pore.impl.inventory.PoreInventory;

import org.apache.commons.lang.NotImplementedException;
import org.bukkit.DyeColor;
import org.bukkit.inventory.Inventory;
import org.spongepowered.api.block.tileentity.carrier.ShulkerBox;

public class PoreShulkerBox extends PoreContainer implements org.bukkit.block.ShulkerBox {

    protected PoreShulkerBox(ShulkerBox handle) {
        super(handle);
    }

    @Override
    ShulkerBox getTileEntity() {
        return (ShulkerBox) super.getTileEntity();
    }

    @Override
    public Inventory getInventory() {
        return PoreInventory.of(getTileEntity().getInventory());
    }

    @Override
    public DyeColor getColor() {
        throw new NotImplementedException("Not available in Sponge");
    }

}
