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
import blue.lapis.pore.impl.entity.PoreThrownPotion;

import org.apache.commons.lang3.NotImplementedException;
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
    public String toString() {
        return toStringHelper().toString();
    }
}
