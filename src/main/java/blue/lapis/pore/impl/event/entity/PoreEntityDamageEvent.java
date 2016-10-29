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

import blue.lapis.pore.converter.type.cause.DamageCauseConverter;
import blue.lapis.pore.converter.type.entity.EntityConverter;
import blue.lapis.pore.event.PoreEvent;
import blue.lapis.pore.event.RegisterEvent;
import blue.lapis.pore.impl.entity.PoreEntity;

import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageEvent;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;
import org.spongepowered.api.event.entity.DamageEntityEvent;

import java.util.Optional;

//TODO: Note: do not implement this or its children yet if you do not want a headache
@RegisterEvent
public final class PoreEntityDamageEvent extends EntityDamageEvent implements PoreEvent<DamageEntityEvent> {

    private final DamageEntityEvent handle;

    @SuppressWarnings("deprecation")
    public PoreEntityDamageEvent(DamageEntityEvent handle) {
        super(null, null, -1.0);
        this.handle = checkNotNull(handle, "handle");
    }

    public DamageEntityEvent getHandle() {
        return this.handle;
    }

    @Override
    public Entity getEntity() {
        return PoreEntity.of(this.getHandle().getTargetEntity());
    }

    @Override
    public EntityType getEntityType() {
        return EntityConverter.of(this.getHandle().getTargetEntity().getType());
    }

    @Override //TODO DamageModifer Conversion
    public double getOriginalDamage(DamageModifier type) throws IllegalArgumentException {
        throw new NotImplementedException("TODO");
    }

    @Override
    public void setDamage(DamageModifier type, double damage) throws IllegalArgumentException {
        throw new NotImplementedException("TODO");
    }

    @Override
    public double getDamage(DamageModifier type) throws IllegalArgumentException {
        throw new NotImplementedException("TODO");
    }

    @Override
    public boolean isApplicable(DamageModifier type) throws IllegalArgumentException {
        throw new NotImplementedException("TODO");
    }

    @Override
    public double getDamage() {
        return getHandle().getBaseDamage();
    }

    @Override
    public double getFinalDamage() {
        return getHandle().getFinalDamage();
    }

    @Override
    public void setDamage(double damage) {
        getHandle().setBaseDamage(damage);
    }

    @Override
    public DamageCause getCause() {
        DamageSource cause = null;
        Optional<DamageSource> source = getHandle().getCause().get(NamedCause.SOURCE, DamageSource.class);
        if (source.isPresent()) {
            cause = source.get();
        }
        return DamageCauseConverter.of(cause);
    }

    @Override
    public boolean isCancelled() {
        return this.getHandle().isCancelled();
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.getHandle().setCancelled(cancel);
    }

    @Override
    public String toString() {
        return toStringHelper().toString();
    }
}
