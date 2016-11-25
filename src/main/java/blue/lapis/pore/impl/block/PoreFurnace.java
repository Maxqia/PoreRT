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

package blue.lapis.pore.impl.block;

import blue.lapis.pore.converter.wrapper.WrapperConverter;
import blue.lapis.pore.impl.inventory.PoreFurnaceInventory;

import org.bukkit.inventory.FurnaceInventory;
import org.spongepowered.api.block.tileentity.carrier.Furnace;

public class PoreFurnace extends PoreContainer implements org.bukkit.block.Furnace {

    public static PoreFurnace of(Furnace handle) {
        return WrapperConverter.of(PoreFurnace.class, handle);
    }

    protected PoreFurnace(Furnace handle) {
        super(handle);
    }

    @Override
    Furnace getTileEntity() {
        return (Furnace) super.getTileEntity();
    }

    @Override
    public short getBurnTime() { //TODO is this correct
        return getTileEntity().passedBurnTime().get().shortValue();
    }

    @Override
    public void setBurnTime(short burnTime) {
        getTileEntity().passedBurnTime().set((int)burnTime);
    }

    @Override
    public short getCookTime() {
        return getTileEntity().passedCookTime().get().shortValue();
    }

    @Override
    public void setCookTime(short cookTime) {
        getTileEntity().passedCookTime().set((int)cookTime);
    }

    @Override
    public FurnaceInventory getInventory() {
        return PoreFurnaceInventory.of(getTileEntity().getInventory());
    }

}
