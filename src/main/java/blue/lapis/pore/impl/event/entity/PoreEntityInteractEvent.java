/*
 * Pore
 * Copyright (c) 2014-2015, Lapis <https://github.com/LapisBlue>
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
import blue.lapis.pore.impl.block.PoreBlock;
import blue.lapis.pore.impl.entity.PoreLivingEntity;

import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityInteractEvent;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.event.block.InteractBlockEvent;

@RegisterEvent //TODO Double check that this is the correct Event
public final class PoreEntityInteractEvent extends EntityInteractEvent implements PoreEvent<InteractBlockEvent> {

    private final InteractBlockEvent handle;
    private final Living cause;

    //TODO Double check that it is the source
    public PoreEntityInteractEvent(InteractBlockEvent handle, @Source Living living) {
        super(null, null);
        this.handle = checkNotNull(handle, "handle");
        this.cause = checkNotNull(living, "living");
    }

    public InteractBlockEvent getHandle() {
        return this.handle;
    }

    @Override
    public LivingEntity getEntity() {
        return PoreLivingEntity.of(cause);
    }

    @Override
    public EntityType getEntityType() {
        return EntityConverter.of(cause.getType());
    }

    @Override
    public Block getBlock() {
        return PoreBlock.of(this.getHandle().getTargetBlock().getLocation().orElse(null));
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
