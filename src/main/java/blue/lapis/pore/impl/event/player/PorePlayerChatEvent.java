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

package blue.lapis.pore.impl.event.player;

import static com.google.common.base.Preconditions.checkNotNull;

import blue.lapis.pore.event.PoreEvent;

import org.bukkit.event.player.PlayerChatEvent;
import org.spongepowered.api.event.message.MessageChannelEvent;

import java.util.Set;

@Deprecated
public final class PorePlayerChatEvent extends PlayerChatEvent implements PoreEvent<MessageChannelEvent.Chat> {

    private final MessageChannelEvent.Chat handle;

    public PorePlayerChatEvent(PoreAsyncPlayerChatEvent event) {
        super(event.getPlayer(), event.getMessage(), event.getFormat(), event.getRecipients());
        this.handle = checkNotNull(event.getHandle(), "handle");
    }

    @Override
    public MessageChannelEvent.Chat getHandle() {
        return handle;
    }

    @Override
    public org.bukkit.entity.Player getPlayer() {
        return super.getPlayer();
    }

    @Override
    public void setPlayer(org.bukkit.entity.Player player) {
        super.setPlayer(player);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public void setMessage(String message) {
        super.setMessage(message);
    }

    @Override
    public String getFormat() {
        return super.getFormat();
    }

    @Override
    public void setFormat(String format) {
        super.setFormat(format);
    }

    @Override
    public Set<org.bukkit.entity.Player> getRecipients() {
        return super.getRecipients();
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
