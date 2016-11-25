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

import blue.lapis.pore.converter.type.material.PotionEffectTypeConverter;
import blue.lapis.pore.converter.wrapper.WrapperConverter;
import blue.lapis.pore.impl.inventory.PoreInventory;

import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.tileentity.carrier.Beacon;

import java.util.Collection;

public class PoreBeacon extends PoreContainer implements org.bukkit.block.Beacon {

    public static PoreBeacon of(BlockState handle) {
        return WrapperConverter.of(PoreBeacon.class, handle);
    }

    protected PoreBeacon(Beacon handle) {
        super(handle);
    }

    @Override
    Beacon getTileEntity() {
        return (Beacon) super.getTileEntity();
    }

    @Override
    public Inventory getInventory() {
        return PoreInventory.of(getTileEntity().getInventory());
    }

    @Override
    public Collection<LivingEntity> getEntitiesInRange() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public int getTier() {
        return getTileEntity().getCompletedLevels();
    }

    @Override
    public PotionEffect getPrimaryEffect() {
        return new PotionEffect(PotionEffectTypeConverter.of(getTileEntity().primaryEffect().get().get()), 0,0);
    } //TODO sponge api PotionEffectType -> PotionEffect?

    @Override
    public void setPrimaryEffect(PotionEffectType effect) {
        getTileEntity().primaryEffect().setTo(PotionEffectTypeConverter.of(effect));
    }

    @Override
    public PotionEffect getSecondaryEffect() {
        return new PotionEffect(PotionEffectTypeConverter.of(getTileEntity().secondaryEffect().get().get()), 0,0);
    }

    @Override
    public void setSecondaryEffect(PotionEffectType effect) {
        getTileEntity().secondaryEffect().setTo(PotionEffectTypeConverter.of(effect));
    }

}
