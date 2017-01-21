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

package blue.lapis.pore.impl.command;

import blue.lapis.pore.Pore;
import blue.lapis.pore.command.PoreCommandCallable;
import blue.lapis.pore.converter.vector.LocationConverter;
import blue.lapis.pore.util.PoreWrapper;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginIdentifiableCommand;
import org.bukkit.command.SimpleCommandMap;
import org.spongepowered.api.command.CommandCallable;
import org.spongepowered.api.command.CommandManager;
import org.spongepowered.api.command.CommandMapping;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.plugin.PluginContainer;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class PoreCommandMap extends SimpleCommandMap {

    private static final CommandManager handle = Pore.getGame().getCommandManager();

    public PoreCommandMap(Server server) {
        super(server);
    }

    @Override
    public void setFallbackCommands() {
        // Don't register help command, let Sponge provide it instead
    }

    @Override
    public boolean register(String label, String fallbackPrefix, Command command) {
        // TODO: Fallback prefix
        Object plugin = Pore.getPlugin();
        if (command instanceof PluginIdentifiableCommand) {
            plugin = Pore.getPlugin(((PluginIdentifiableCommand) command).getPlugin());
        }

        boolean registered = registerCommand(label, false, command, plugin);

        Iterator<String> iter = command.getAliases().iterator();
        while (iter.hasNext()) {
            if (!registerCommand(iter.next(), true, command, plugin))
                iter.remove();
        }

        command.setLabel((!registered ? fallbackPrefix + ":" : "")+ label);
        command.register(this);
        return registered;
    }

    private boolean registerCommand(String label, boolean isAlias, Command command, Object plugin) {
        Optional<? extends CommandMapping> optMapping = handle.get(label);
        if (optMapping.isPresent()) {
            if (isAlias) return false;

            CommandMapping mapping = optMapping.get();
            if (mapping.getPrimaryAlias().equals(label)) return false;

            Optional<PluginContainer> optPlugin = handle.getOwner(mapping);
            if (optPlugin.isPresent() && optPlugin.get().equals(plugin)) return false;
        }
        handle.register(plugin, new PoreCommandCallable(command, label), label);
        return true;
    }

    @Override
    public Command getCommand(String name) {
        Optional<? extends CommandMapping> command = handle.get(name);
        if (command.isPresent()) {
            CommandCallable callable = command.get().getCallable();
            if (callable instanceof PoreCommandCallable) {
                return ((PoreCommandCallable) callable).getHandle();
            }
        }

        return null;
    }

    @Override
    public Collection<Command> getCommands() {
        // TODO: Support all commands
        return Collections2.transform(Collections2.filter(handle.getCommands(),
                PORE_COMMAND_CALLABLE), GET_PORE_COMMAND);
    }

    private static final Predicate<CommandMapping> PORE_COMMAND_CALLABLE =
            input -> input.getCallable() instanceof PoreCommandCallable;

    private static final Function<CommandMapping, Command> GET_PORE_COMMAND =
            input -> ((PoreCommandCallable) input).getHandle();

    @Override
    public synchronized void clearCommands() {
        throw new UnsupportedOperationException(); // Unsupported for now
    }

    @Override
    public boolean dispatch(CommandSender sender, String commandLine) throws CommandException {
        CommandResult result = handle.process((CommandSource) ((PoreWrapper<?>) sender).getHandle(), commandLine);
        return result.getSuccessCount().isPresent() && result.getSuccessCount().get() > 0;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String cmdLine, Location location) {
        return handle.getSuggestions((CommandSource) ((PoreWrapper<?>) sender).getHandle(), cmdLine, location == null ? null : LocationConverter.of(location));
    }

    @Override
    public void registerServerAliases() {
        throw new NotImplementedException("TODO");
    }

}
