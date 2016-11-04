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

package blue.lapis.pore;

import static com.google.common.base.Preconditions.checkNotNull;

import blue.lapis.pore.event.PoreEventRegistry;
import blue.lapis.pore.impl.PoreServer;
import blue.lapis.pore.launch.PoreEventManager;
import blue.lapis.pore.lib.org.slf4j.bridge.SLF4JBridgeHandler;
import blue.lapis.pore.plugin.PorePluginContainer;
import blue.lapis.pore.util.PoreText;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginLoadOrder;
import org.slf4j.Logger;
import org.slf4j.helpers.NOPLogger;
import org.spongepowered.api.Game;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.game.state.GameAboutToStartServerEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.plugin.PluginContainer;

import java.util.Optional;

/**
 * An implementation of the Bukkit API built on Sponge.
 *
 * @author Lapis Blue
 */
public final class Pore implements PoreEventManager {

    static Pore instance;

    private final Game game;
    private final Logger logger;
    private final PluginContainer plugin;

    private PoreServer server;

    @Inject
    public Pore(Game game, Logger logger, PluginContainer plugin) {
        Preconditions.checkState(instance == null, "Pore is already initialized");
        instance = this;

        this.game = checkNotNull(game, "game");
        this.logger = checkNotNull(logger, "logger");
        this.plugin = checkNotNull(plugin, "plugin");
    }

    public static Pore getInstance() {
        return checkNotNull(instance, "instance");
    }

    public static Game getGame() {
        return getInstance().game;
    }

    public static Logger getLogger() {
        return getInstance().logger;
    }

    public static PluginContainer getPlugin() {
        return getInstance().plugin;
    }

    public static PoreServer getServer() {
        return getInstance().server;
    }

    public static PluginContainer getPlugin(Plugin plugin) {
        return new PorePluginContainer(plugin);
    }

    @Override
    public void onPreInit(GamePreInitializationEvent event) throws Exception {
        // Initialize logging
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        logger.info("Loading Pore server, please wait...");

        server = new PoreServer(game, logger);
        PoreEventRegistry.register();

        server.getLogger().info("Loading plugins");
        server.loadPlugins();
    }

    @Override
    public void onAboutToStart(GameAboutToStartServerEvent event) throws Exception {
        server.enablePlugins(PluginLoadOrder.STARTUP);
    }

    @Override
    public void onStarting(GameStartingServerEvent event) throws Exception {
        server.enablePlugins(PluginLoadOrder.POSTWORLD);
    }

    @Override
    public void onShutdown(GameStoppingServerEvent event) throws Exception {
        logger.info("Disabling Bukkit plugins, please wait...");
        server.disablePlugins();
        logger.info("Finished disabling Bukkit plugins!");
    }

    static Logger testLogger = NOPLogger.NOP_LOGGER;

    public static Logger getTestLogger() {
        return testLogger;
    }

    @Override // This is horrible but it's needed for setDisplayName ...
    @SuppressWarnings("deprecation")
    public void onChatEvent(MessageChannelEvent.Chat event) {
        Optional<Player> optPlayer = event.getCause().get(NamedCause.SOURCE, Player.class);
        if (optPlayer.isPresent()) {
            blue.lapis.pore.impl.event.player.PoreAsyncPlayerChatEvent asyncBukkitEvent =
                    new blue.lapis.pore.impl.event.player.PoreAsyncPlayerChatEvent(event, optPlayer.get());
            Bukkit.getServer().getPluginManager().callEvent(asyncBukkitEvent);

            blue.lapis.pore.impl.event.player.PorePlayerChatEvent bukkitEvent =
                    new blue.lapis.pore.impl.event.player.PorePlayerChatEvent(asyncBukkitEvent);
            Bukkit.getServer().getPluginManager().callEvent(bukkitEvent);

            event.setMessage(PoreText.convert(String.format(bukkitEvent.getFormat(),
                    bukkitEvent.getPlayer().getDisplayName(), bukkitEvent.getMessage())));
        }
    }
}
