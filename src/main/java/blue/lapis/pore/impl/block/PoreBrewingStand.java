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
import blue.lapis.pore.impl.inventory.PoreBrewerInventory;

import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.inventory.BrewerInventory;
import org.spongepowered.api.block.tileentity.carrier.BrewingStand;
import org.spongepowered.api.data.manipulator.mutable.tileentity.BrewingStandData;

public class PoreBrewingStand extends PoreContainer implements org.bukkit.block.BrewingStand {

    public static PoreBrewingStand of(BrewingStand handle) {
        return WrapperConverter.of(PoreBrewingStand.class, handle);
    }

    protected PoreBrewingStand(BrewingStand handle) {
        super(handle);
    }


    @Override
    BrewingStand getTileEntity() {
        return (BrewingStand) super.getTileEntity();
    }

    @Override
    public int getBrewingTime() {
        return getTileEntity().get(BrewingStandData.class).get().remainingBrewTime().get();
    }

    @Override
    public void setBrewingTime(int brewTime) {
        getTileEntity().get(BrewingStandData.class).get().remainingBrewTime().set(brewTime);
    }

    @Override
    public BrewerInventory getInventory() {
        return PoreBrewerInventory.of(getTileEntity().getInventory());
    }

    @Override
    public int getFuelLevel() { //TODO bug Sponge about this
        throw new NotImplementedException("TODO");
    }

    @Override
    public void setFuelLevel(int level) {
        throw new NotImplementedException("TODO");
    }
}
