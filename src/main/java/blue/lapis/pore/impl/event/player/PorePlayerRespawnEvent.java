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

import blue.lapis.pore.converter.vector.LocationConverter;
import blue.lapis.pore.event.PoreEvent;
import blue.lapis.pore.event.RegisterEvent;
import blue.lapis.pore.impl.entity.PorePlayer;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.spongepowered.api.event.entity.living.humanoid.player.RespawnPlayerEvent;

@RegisterEvent
public final class PorePlayerRespawnEvent extends PlayerRespawnEvent implements PoreEvent<RespawnPlayerEvent> {

    private final RespawnPlayerEvent handle;

    public PorePlayerRespawnEvent(RespawnPlayerEvent handle) {
        super(null, null, false);
        this.handle = checkNotNull(handle, "handle");
    }

    public RespawnPlayerEvent getHandle() {
        return handle;
    }

    @Override
    public Player getPlayer() {
        return PorePlayer.of(getHandle().getTargetEntity());
    }

    @Override
    public Location getRespawnLocation() {
        return LocationConverter.of(getHandle().getToTransform().getLocation());
    }

    @Override
    public void setRespawnLocation(Location respawnLocation) {
        getHandle().getToTransform().setLocation(LocationConverter.of(respawnLocation));
    }

    @Override
    public boolean isBedSpawn() {
        return getHandle().isBedSpawn();
    }

    @Override
    public String toString() {
        return toStringHelper().toString();
    }

}
