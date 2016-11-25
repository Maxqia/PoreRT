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


package blue.lapis.pore.impl.entity;

import blue.lapis.pore.converter.type.entity.HorseConverter;
import blue.lapis.pore.converter.wrapper.WrapperConverter;
import blue.lapis.pore.impl.inventory.PoreHorseInventory;

import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.inventory.HorseInventory;
import org.spongepowered.api.entity.living.animal.RideableHorse;

public class PoreHorse extends PoreAbstractHorse implements org.bukkit.entity.Horse {

    public static PoreHorse of(RideableHorse handle) {
        return WrapperConverter.of(PoreHorse.class, handle);
    }

    protected PoreHorse(RideableHorse handle) {
        super(handle);
    }

    @Override
    public RideableHorse getHandle() {
        return (RideableHorse) super.getHandle();
    }

    @Override
    public Color getColor() {
        return HorseConverter.of(getHandle().getHorseData().color().get());
    }

    @Override
    public void setColor(Color color) {
        getHandle().getHorseData().color().set(HorseConverter.of(color));
    }

    @Override
    public Style getStyle() {
        return HorseConverter.of(getHandle().getHorseData().style().get());
    }

    @Override
    public void setStyle(Style style) {
        getHandle().getHorseData().style().set(HorseConverter.of(style));
    }

    @Override
    public boolean isCarryingChest() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public void setCarryingChest(boolean chest) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public HorseInventory getInventory() {
        return PoreHorseInventory.of(getHandle().getInventory());
    }

}
