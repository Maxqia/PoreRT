/*
 * Pore
 * Copyright (c) 2014-2016, Lapis <https://github.com/LapisBlue>
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
import blue.lapis.pore.impl.block.PoreBlockState;

import com.google.common.base.Preconditions;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.block.BlockGrowEvent;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.util.GuavaCollectors;

public final class PoreBlockGrowEvent extends BlockGrowEvent implements PoreEvent<ChangeBlockEvent.Grow> {

    private final ChangeBlockEvent.Grow handle;
    private final Transaction<BlockSnapshot> transaction;

    public PoreBlockGrowEvent(ChangeBlockEvent.Grow handle, Transaction<BlockSnapshot> transaction) {
        super(null, null);
        this.handle = Preconditions.checkNotNull(handle, "handle");
        this.transaction = checkNotNull(transaction, "transaction");
    }

    public ChangeBlockEvent.Grow getHandle() {
        return handle;
    }

    @Override
    public Block getBlock() {
        return PoreBlock.of(handle.getCause().get(SOURCE, BlockSnapshot.class).orElse(null).getLocation().orElse(null));
    }

    @Override
    public BlockState getNewState() {
        return PoreBlockState.of(transaction.getOriginal());
    }

    @Override
    public boolean isCancelled() {
        return getHandle().isCancelled();
    }

    @Override
    public void setCancelled(boolean cancel) {
        getHandle().setCancelled(cancel);
    }

    @Override
    public String toString() {
        return toStringHelper()
                .add("transaction", this.transaction)
                .toString();
    }

    @RegisterEvent
    public static void register() { // TODO ChangeBlockEvent.Grow isn't implemented yet ...
        PoreEventRegistry.register(PoreBlockGrowEvent.class, ChangeBlockEvent.Grow.class, event -> {
            System.out.println(event.getCause().toString());
            return event.getTransactions().stream()
                    .map(transaction -> new PoreBlockGrowEvent(event, transaction))
                    .collect(GuavaCollectors.toImmutableList());
        });
    }
}
