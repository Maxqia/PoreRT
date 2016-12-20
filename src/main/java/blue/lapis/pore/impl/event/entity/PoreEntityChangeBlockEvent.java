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

    @SuppressWarnings({ "deprecation", "null" })
    public PoreEntityChangeBlockEvent(ChangeBlockEvent handle, Entity entity, Transaction<BlockSnapshot> transaction) {
        super(null, null, null, (Byte) null);
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
