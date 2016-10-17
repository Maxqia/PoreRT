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
package blue.lapis.pore.impl.event.player;

import static com.google.common.base.Preconditions.checkNotNull;

import blue.lapis.pore.event.PoreEvent;
import blue.lapis.pore.event.PoreEventRegistry;
import blue.lapis.pore.event.RegisterEvent;
import blue.lapis.pore.impl.PoreWorld;
import blue.lapis.pore.impl.entity.PorePlayer;

import com.google.common.collect.ImmutableList;
import org.bukkit.World;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.entity.MoveEntityEvent;

@RegisterEvent
public final class PorePlayerChangedWorldEvent extends PlayerChangedWorldEvent
    implements PoreEvent<MoveEntityEvent.Teleport> {

    private final MoveEntityEvent.Teleport handle;

    public PorePlayerChangedWorldEvent(MoveEntityEvent.Teleport handle) {
        super(null, null);
        this.handle = checkNotNull(handle, "handle");
    }

    public MoveEntityEvent.Teleport getHandle() {
        return handle;
    }

    @Override
    public org.bukkit.entity.Player getPlayer() {
        return PorePlayer.of((Player) getHandle().getTargetEntity());
    }

    @Override
    public World getFrom() {
        return PoreWorld.of(getHandle().getFromTransform().getExtent());
    }

    @Override
    public String toString() {
        return toStringHelper().toString();
    }

    @RegisterEvent
    public static void register() {
        PoreEventRegistry.register(PorePlayerChangedWorldEvent.class, MoveEntityEvent.Teleport.class, event -> {
            if (event.getTargetEntity() instanceof Player) {
                return ImmutableList.of(new PorePlayerChangedWorldEvent(event));
            }
            return ImmutableList.of();
        });
    }

}
