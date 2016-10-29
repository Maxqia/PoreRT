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

import blue.lapis.pore.converter.type.cause.SpawnReasonConverter;
import blue.lapis.pore.converter.vector.LocationConverter;
import blue.lapis.pore.event.PoreEvent;
import blue.lapis.pore.event.PoreEventRegistry;
import blue.lapis.pore.event.RegisterEvent;
import blue.lapis.pore.impl.entity.PoreLivingEntity;

import com.google.common.collect.ImmutableList;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.cause.entity.spawn.SpawnCause;
import org.spongepowered.api.event.entity.SpawnEntityEvent;

import java.util.ArrayList;

public final class PoreCreatureSpawnEvent extends CreatureSpawnEvent implements PoreEvent<SpawnEntityEvent> {

    private final SpawnEntityEvent handle;
    private final Living entity;

    public PoreCreatureSpawnEvent(SpawnEntityEvent handle, Living entity) {
        super(null, null);
        this.handle = checkNotNull(handle, "handle");
        this.entity = checkNotNull(entity, "entity");
    }

    public SpawnEntityEvent getHandle() {
        return handle;
    }

    @Override
    public LivingEntity getEntity() {
        return (LivingEntity) PoreLivingEntity.of(this.entity);
    }

    @Override
    public EntityType getEntityType() {
        return getEntity().getType();
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
    public Location getLocation() {
        return LocationConverter.of(entity.getLocation());
    }

    @Override
    public SpawnReason getSpawnReason() {
        return SpawnReasonConverter.of(getHandle().getCause()
                .get(NamedCause.SOURCE, SpawnCause.class).orElse(null).getType());
    }

    @Override
    public String toString() {
        return toStringHelper().toString();
    }

    @RegisterEvent
    public static void register() {
        PoreEventRegistry.register(PoreCreatureSpawnEvent.class, SpawnEntityEvent.class, event -> {
            ArrayList<PoreCreatureSpawnEvent> list = new ArrayList<PoreCreatureSpawnEvent>();
            for (Entity entity : event.getEntities()) {
                if (entity instanceof Living) {
                    list.add(new PoreCreatureSpawnEvent(event, (Living) entity));
                }
            }
            return ImmutableList.copyOf(list);
        });
    }
}
