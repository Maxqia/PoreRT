/*
 * PoreRT - A Bukkit to Sponge Bridge
 *
 * Copyright (c) 2016-2017, Maxqia <https://github.com/Maxqia> AGPLv3
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

package blue.lapis.pore.impl.event.entity;

import static com.google.common.base.Preconditions.checkNotNull;

import blue.lapis.pore.converter.type.entity.EntityConverter;
import blue.lapis.pore.event.PoreEvent;
import blue.lapis.pore.event.RegisterEvent;
import blue.lapis.pore.event.Source;
import blue.lapis.pore.impl.entity.PoreThrownPotion;

import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.PotionSplashEvent;
import org.spongepowered.api.entity.projectile.ThrownPotion;
import org.spongepowered.api.event.entity.CollideEntityEvent;

import java.util.Collection;

@RegisterEvent //TODO better event?
public final class PorePotionSplashEvent extends PotionSplashEvent implements PoreEvent<CollideEntityEvent.Impact> {

    private final CollideEntityEvent.Impact handle;
    private final ThrownPotion potion;

    //TODO Double check that the ThrownPotion is the source
    public PorePotionSplashEvent(CollideEntityEvent.Impact handle, @Source ThrownPotion potion) {
        super(null, null);
        this.handle = checkNotNull(handle, "handle");
        this.potion = checkNotNull(potion, "potion");
    }

    public CollideEntityEvent.Impact getHandle() {
        return this.handle;
    }

    @Override
    public org.bukkit.entity.ThrownPotion getEntity() {
        return PoreThrownPotion.of(this.potion);
    }

    @Override
    public EntityType getEntityType() {
        return EntityConverter.of(this.potion.getType());
    }

    @Override
    public org.bukkit.entity.ThrownPotion getPotion() {
        return this.getEntity();
    }

    @Override
    public Collection<LivingEntity> getAffectedEntities() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public double getIntensity(LivingEntity entity) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public void setIntensity(LivingEntity entity, double intensity) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public boolean isCancelled() {
        return this.getHandle().isCancelled();
    }

    @Override
    public void setCancelled(boolean cancel) {
        getHandle().setCancelled(cancel);
    }

    @Override
    public Block getHitBlock() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public Entity getHitEntity() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public String toString() {
        return toStringHelper().toString();
    }
}
