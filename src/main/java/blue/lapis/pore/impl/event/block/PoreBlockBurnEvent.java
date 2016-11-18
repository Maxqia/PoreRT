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

package blue.lapis.pore.impl.event.block;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.spongepowered.api.event.cause.NamedCause.SOURCE;

import blue.lapis.pore.event.PoreEvent;
import blue.lapis.pore.event.PoreEventRegistry;
import blue.lapis.pore.event.RegisterEvent;
import blue.lapis.pore.impl.block.PoreBlock;

import com.google.common.collect.ImmutableList;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBurnEvent;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockType;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.event.block.ChangeBlockEvent;

import java.util.ArrayList;

public final class PoreBlockBurnEvent extends BlockBurnEvent implements PoreEvent<ChangeBlockEvent> {

    private final ChangeBlockEvent handle;
    private final Transaction<BlockSnapshot> transaction;

    public PoreBlockBurnEvent(ChangeBlockEvent handle, Transaction<BlockSnapshot> transaction) {
        super(null);
        this.handle = checkNotNull(handle, "handle");
        this.transaction = checkNotNull(transaction, "transaction");
    }

    @Override
    public ChangeBlockEvent getHandle() {
        return handle;
    }

    @Override
    public Block getBlock() {
        return PoreBlock.of(this.transaction.getOriginal().getLocation().get());
    }

    @Override
    public boolean isCancelled() {
        return transaction.isValid();
    }

    @Override
    public void setCancelled(boolean cancel) {
        transaction.setValid(cancel);;
    }

    @Override
    public String toString() {
        return toStringHelper()
                .add("transaction", this.transaction)
                .toString();
    }

    @RegisterEvent
    public static void register() {
        PoreEventRegistry.register(PoreBlockBurnEvent.class, ChangeBlockEvent.class, event -> {
            ArrayList<PoreBlockBurnEvent> events = new ArrayList<PoreBlockBurnEvent>();
            BlockSnapshot fire = event.getCause().get(SOURCE, BlockSnapshot.class).orElse(null);
            if (fire != null && fire.getState().getType() == BlockTypes.FIRE) {
                for (Transaction<BlockSnapshot> trans : event.getTransactions()) {
                    BlockType from = trans.getOriginal().getState().getType();
                    BlockType to = trans.getFinal().getState().getType();
                    if (from != to) {
                        events.add(new PoreBlockBurnEvent(event, trans));
                    }
                }
            }
            return ImmutableList.copyOf(events);
        });
    }

}
