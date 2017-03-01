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
import blue.lapis.pore.impl.entity.PoreEntity;
import blue.lapis.pore.impl.entity.PoreProjectile;

import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.spongepowered.api.entity.projectile.Projectile;
import org.spongepowered.api.event.action.CollideEvent;

@RegisterEvent
public final class PoreProjectileHitEvent extends ProjectileHitEvent implements PoreEvent<CollideEvent.Impact> {

    private final CollideEvent.Impact handle;
    private final Projectile projectile;

    public PoreProjectileHitEvent(CollideEvent.Impact handle, @Source Projectile projectile) {
        super(null);
        this.handle = checkNotNull(handle, "handle");
        this.projectile = projectile;
    }

    public CollideEvent.Impact getHandle() {
        return this.handle;
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
    public org.bukkit.entity.Projectile getEntity() {
        return PoreProjectile.of(projectile);
    }

    @Override
    public EntityType getEntityType() {
        return EntityConverter.of(projectile.getType());
    }

    @Override
    public String toString() {
        return toStringHelper().toString();
    }
}
