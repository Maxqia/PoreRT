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
package blue.lapis.pore.impl.event.block;

import static com.google.common.base.Preconditions.checkNotNull;
import blue.lapis.pore.event.PoreEvent;
import blue.lapis.pore.event.PoreEventRegistry;
import blue.lapis.pore.event.RegisterEvent;
import blue.lapis.pore.impl.block.PoreBlock;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.BlockFromToEvent;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.event.block.ChangeBlockEvent;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.util.GuavaCollectors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;

public final class PoreBlockFromToEvent extends BlockFromToEvent implements PoreEvent<ChangeBlockEvent.Pre> {

    private final ChangeBlockEvent.Pre handle;
    private final Location<World> to;

    public PoreBlockFromToEvent(ChangeBlockEvent.Pre handle, PoreBlock source, Location<World> block) {
        super(source, (Block) null); // type casting removes ambiguity
        this.handle = checkNotNull(handle, "handle");
        this.to = checkNotNull(block, "block");
    }

    public ChangeBlockEvent.Pre getHandle() {
        return handle;
    }

    @Override
    public Block getBlock() {
        return super.getBlock();
    }

    @Override
    public BlockFace getFace() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public Block getToBlock() {
        return PoreBlock.of(to);
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
                .add("toBlock", this.to)
                .toString();
    }

    @RegisterEvent
    public static void register() {
        PoreEventRegistry.register(PoreBlockFromToEvent.class, ChangeBlockEvent.Pre.class, event -> {
            if (event.getCause().containsNamed(NamedCause.LIQUID_FLOW)) {
                System.out.println(event);
                Optional<BlockSnapshot> optSource = event.getCause().get(NamedCause.SOURCE, BlockSnapshot.class);
                if (optSource.isPresent() && optSource.get().getLocation().isPresent()) {
                    PoreBlock source = PoreBlock.of(optSource.get().getLocation().get());
                    return event.getLocations().stream()
                            .map(location -> new PoreBlockFromToEvent(event, source, location))
                            .collect(GuavaCollectors.toImmutableList());
                }
            }
            return ImmutableList.of();
        });
    }
}
