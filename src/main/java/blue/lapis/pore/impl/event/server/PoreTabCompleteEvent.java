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

package blue.lapis.pore.impl.event.server;

import static com.google.common.base.Preconditions.checkNotNull;

import blue.lapis.pore.event.PoreEvent;
import blue.lapis.pore.event.RegisterEvent;
import blue.lapis.pore.impl.command.PoreCommandSender;
import org.apache.commons.lang.Validate;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.command.TabCompleteEvent;

import java.util.Collections;
import java.util.List;

@RegisterEvent
public final class PoreTabCompleteEvent extends org.bukkit.event.server.TabCompleteEvent
        implements PoreEvent<TabCompleteEvent> {

    private final TabCompleteEvent handle;

    public PoreTabCompleteEvent(TabCompleteEvent.Chat handle) {
        super(null, "", Collections.<String>emptyList());
        this.handle = checkNotNull(handle, "handle");
    }

    @Override
    public TabCompleteEvent getHandle() {
        return handle;
    }

    @Override
    public org.bukkit.command.CommandSender getSender() {
        return PoreCommandSender.of(getHandle().getCause().get(NamedCause.SOURCE, CommandSource.class).get());
    }

    @Override
    public String getBuffer() {
        return getHandle().getRawMessage();
    }

    @Override
    public List<String> getCompletions() {
        return getHandle().getTabCompletions();
    }

    @Override
    public void setCompletions(List<String> completions) {
        Validate.notNull(completions);
        List<String> eventCompletions = getHandle().getTabCompletions();
        eventCompletions.clear();
        eventCompletions.addAll(completions);
    }

    @Override
    public boolean isCancelled() {
        return getHandle().isCancelled();
    }

    @Override
    public void setCancelled(boolean cancelled) {
        getHandle().setCancelled(cancelled);
    }

    @Override
    public String toString() {
        return toStringHelper().toString();
    }

}
