/*
 * PoreRT - A Bukkit to Sponge Bridge
 *
 * Copyright (c) 2016-2017, Maxqia <https://github.com/Maxqia> AGPLv3
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

package blue.lapis.pore.impl.event.player;

import static com.google.common.base.Preconditions.checkNotNull;

import blue.lapis.pore.event.PoreEvent;
import blue.lapis.pore.event.RegisterEvent;
import blue.lapis.pore.event.Source;
import blue.lapis.pore.impl.entity.PorePlayer;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.command.SendCommandEvent;

import java.util.Set;

@RegisterEvent
public final class PorePlayerCommandPreprocessEvent extends PlayerCommandPreprocessEvent
        implements PoreEvent<SendCommandEvent> {

    private final SendCommandEvent handle;
    private final Player source;

    public PorePlayerCommandPreprocessEvent(SendCommandEvent handle, @Source Player source) {
        super(null, null, null);
        this.handle = checkNotNull(handle, "handle");
        this.source = checkNotNull(source, "source");
    }

    @Override
    public SendCommandEvent getHandle() {
        return handle;
    }

    @Override
    public org.bukkit.entity.Player getPlayer() {
        return PorePlayer.of(source);
    }

    @Override
    public void setPlayer(org.bukkit.entity.Player player) throws IllegalArgumentException {
        throw new NotImplementedException("TODO"); // TODO
    }

    @Override
    public String getMessage() {
        return getHandle().getCommand();
    }

    @Override
    public void setMessage(String command) throws IllegalArgumentException {
        // TODO
    }

    @Override
    public Set<org.bukkit.entity.Player> getRecipients() {
        return ImmutableSet.of();
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

}
