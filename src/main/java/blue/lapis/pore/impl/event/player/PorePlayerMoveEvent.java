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

package blue.lapis.pore.impl.event.player;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.spongepowered.api.event.cause.NamedCause.SOURCE;

import blue.lapis.pore.converter.vector.LocationConverter;
import blue.lapis.pore.event.PoreEvent;
import blue.lapis.pore.event.PoreEventRegistry;
import blue.lapis.pore.event.RegisterEvent;
import blue.lapis.pore.impl.entity.PorePlayer;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.spongepowered.api.event.entity.MoveEntityEvent;



public final class PorePlayerMoveEvent extends PlayerMoveEvent implements PoreEvent<MoveEntityEvent> {

    private final MoveEntityEvent handle;

    public PorePlayerMoveEvent(MoveEntityEvent handle) {
        super(null, null, null);
        this.handle = checkNotNull(handle, "handle");
    }

    public MoveEntityEvent getHandle() {
        return handle;
    }

    @Override
    public Player getPlayer() {
        return (Player) PorePlayer.of(getHandle().getTargetEntity());
    }

    @Override
    public Location getFrom() {
        return LocationConverter.fromTransform(getHandle().getFromTransform());
    }

    @Override
    public void setFrom(Location from) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public Location getTo() {
        return LocationConverter.fromTransform(getHandle().getToTransform());
    }

    @Override
    public void setTo(Location to) {
        getHandle().setToTransform(LocationConverter.toTransform(to));
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
        return toStringHelper().toString();
    }

    @RegisterEvent
    public static void register() {
        PoreEventRegistry.register(PorePlayerMoveEvent.class, MoveEntityEvent.class, event -> {
            org.spongepowered.api.entity.living.player.Player player =
                    event.getCause().get(SOURCE, org.spongepowered.api.entity.living.player.Player.class).orElse(null);
            if (player != null) {
                return ImmutableList.of(new PorePlayerMoveEvent(event));
            } else {
                return ImmutableList.of();
            }
        });
    }
}
