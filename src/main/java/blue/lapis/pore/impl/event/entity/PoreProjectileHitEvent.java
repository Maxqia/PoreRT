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

package blue.lapis.pore.impl.event.entity;

import static com.google.common.base.Preconditions.checkNotNull;

import blue.lapis.pore.converter.type.entity.EntityConverter;
import blue.lapis.pore.event.PoreEvent;
import blue.lapis.pore.event.RegisterEvent;
import blue.lapis.pore.event.Source;
import blue.lapis.pore.impl.entity.PoreProjectile;

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
