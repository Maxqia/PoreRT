/*
 * PoreRT - A Bukkit to Sponge Bridge
 *
 * Copyright (c) 2016-2017, Maxqia <https://github.com/Maxqia> AGPLv3
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

package blue.lapis.pore.impl.event.block;

import static com.google.common.base.Preconditions.checkNotNull;
import blue.lapis.pore.event.PoreEvent;
import blue.lapis.pore.event.PoreEventRegistry;
import blue.lapis.pore.event.RegisterEvent;
import blue.lapis.pore.impl.block.PoreBlock;

import com.google.common.collect.ImmutableList;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.cause.NamedCause;
import java.util.stream.Collectors;

public final class PoreBlockRedstoneEvent extends BlockRedstoneEvent
        implements PoreEvent<ChangeBlockEvent.Modify>{

    private final ChangeBlockEvent.Modify handle;
    private final Transaction<BlockSnapshot> transaction;

    public PoreBlockRedstoneEvent(ChangeBlockEvent.Modify handle, Transaction<BlockSnapshot> transaction) {
        super(null, -1, -1);
        this.handle = checkNotNull(handle, "handle");
        this.transaction = checkNotNull(transaction, "transaction");
    }

    public ChangeBlockEvent.Modify getHandle() {
        return handle;
    }

    @Override
    public Block getBlock() {
        return PoreBlock.of(getHandle().getCause().get(NamedCause.SOURCE, BlockSnapshot.class).get().getLocation().get());
    }

    @Override
    public int getOldCurrent() {
        return transaction.getOriginal().get(Keys.POWER).orElse(0);
    }

    @Override
    public int getNewCurrent() {
        return transaction.getFinal().get(Keys.POWER).orElse(0);
    }

    @Override
    public void setNewCurrent(int newCurrent) {
        transaction.setCustom(transaction.getFinal().with(Keys.POWER, newCurrent).get());
    }

    @Override
    public String toString() {
        return toStringHelper()
                .add("transaction", this.transaction)
                .toString();
    }

    @RegisterEvent
    public static void register() {
        PoreEventRegistry.register(PoreBlockRedstoneEvent.class, ChangeBlockEvent.Modify.class, event -> {
            return ImmutableList.copyOf(event.getTransactions().stream()
                .filter(trans -> trans.getDefault().supports(Keys.POWER))
                .map(trans -> new PoreBlockRedstoneEvent(event, trans))
                .collect(Collectors.toList())); // guava isn't updated for the ImmutableList collector :(

        });
    }

}
