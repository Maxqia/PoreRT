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

import static org.spongepowered.api.event.cause.NamedCause.SOURCE;

import blue.lapis.pore.converter.data.block.BlockDataConverter;
import blue.lapis.pore.converter.type.entity.EntityConverter;
import blue.lapis.pore.converter.type.material.MaterialConverter;
import blue.lapis.pore.event.PoreEvent;
import blue.lapis.pore.event.PoreEventRegistry;
import blue.lapis.pore.event.RegisterEvent;
import blue.lapis.pore.impl.block.PoreBlock;
import blue.lapis.pore.impl.entity.PoreEntity;

import com.google.common.collect.ImmutableList;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.util.GuavaCollectors;

public final class PoreEntityChangeBlockEvent extends EntityChangeBlockEvent implements PoreEvent<ChangeBlockEvent> {

    private final ChangeBlockEvent handle;
    private final Entity entity;
    private final Transaction<BlockSnapshot> transaction;

    @SuppressWarnings("deprecation")
    public PoreEntityChangeBlockEvent(ChangeBlockEvent handle, Entity entity, Transaction<BlockSnapshot> transaction) {
        super(null, null, null);
        this.handle = checkNotNull(handle, "handle");
        this.entity = checkNotNull(entity, "entity");
        this.transaction = checkNotNull(transaction, "transaction");
    }

    public ChangeBlockEvent getHandle() {
        return this.handle;
    }

    @Override
    public org.bukkit.entity.Entity getEntity() {
        return PoreEntity.of(this.entity);
    }

    @Override
    public EntityType getEntityType() {
        return EntityConverter.of(this.entity.getType());
    }

    @Override
    public Block getBlock() {
       return PoreBlock.of(transaction.getOriginal().getLocation().orElse(null));
    }

    @Override
    public Material getTo() {
        return MaterialConverter.of(transaction.getFinal().getState().getType());
    }

    @Override
    public byte getData() {
        return BlockDataConverter.INSTANCE.getDataValue(transaction.getFinal().getState());
    }

    @Override
    public boolean isCancelled() {
        return transaction.isValid();
    }

    @Override
    public void setCancelled(boolean cancel) {
        transaction.setValid(cancel);
    }

    @Override
    public String toString() {
        return toStringHelper().toString();
    }

    @RegisterEvent
    public static void register() {
        PoreEventRegistry.register(PoreEntityChangeBlockEvent.class, ChangeBlockEvent.class, event -> {
            if (!(event instanceof ChangeBlockEvent.Post)) { // post creates duplicate events
                Entity entity = event.getCause().get(SOURCE, Entity.class).orElse(null);
                if (entity != null && !(entity instanceof Player)) {
                    return event.getTransactions().stream()
                            .map(transaction -> new PoreEntityChangeBlockEvent(event, entity, transaction))
                            .collect(GuavaCollectors.toImmutableList());
                }
            }
            return ImmutableList.of();
        });
    }

}
