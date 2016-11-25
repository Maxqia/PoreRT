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
import blue.lapis.pore.impl.inventory.PoreInventory;

import org.apache.commons.lang.NotImplementedException;
import org.bukkit.entity.AbstractHorse;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse.Variant;
import org.bukkit.inventory.Inventory;
import org.spongepowered.api.entity.living.animal.Horse;

@SuppressWarnings("deprecation")
public class PoreAbstractHorse extends PoreTameable implements AbstractHorse {

    public static PoreAbstractHorse of(Horse handle) {
        return WrapperConverter.of(PoreAbstractHorse.class, handle);
    }

    protected PoreAbstractHorse(Horse handle) {
        super(handle);
    }

    @Override
    public Horse getHandle() {
        return (Horse) super.getHandle();
    }

    @Override
    public EntityType getType() {
        return EntityType.HORSE;
    }

    @Override
    public Variant getVariant() {
        return HorseConverter.of(getHandle().getHorseData().variant().get());
    }

    @Override
    public void setVariant(Variant variant) {
        getHandle().getHorseData().variant().set(HorseConverter.of(variant));
    }

    @Override
    public int getDomestication() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public void setDomestication(int level) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public int getMaxDomestication() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public void setMaxDomestication(int level) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public double getJumpStrength() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public void setJumpStrength(double strength) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public Inventory getInventory() {
        return PoreInventory.of(getHandle().getInventory());
    }
}
