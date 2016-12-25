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

package blue.lapis.pore.launch;

//import blue.lapis.pore.util.classloader.PoreClassLoader;

import com.google.inject.Inject;
import com.google.inject.Injector;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.game.state.GameAboutToStartServerEvent;
import org.spongepowered.api.event.game.state.GamePreInitializationEvent;
import org.spongepowered.api.event.game.state.GameStartingServerEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.event.message.MessageChannelEvent;
import org.spongepowered.api.plugin.Plugin;

//import java.net.MalformedURLException;
import java.net.URL;

/**
 * Launches Pore in a Classloader
 * with Listener redirects
 * @See Pore
 */
@Plugin(id = "pore", name = "PoreRT")
public class PoreBootstrap implements PoreEventManager {

    private static final String IMPLEMENTATION_CLASS = "blue.lapis.pore.Pore";

    private final PoreEventManager pore;

    @Inject
    public PoreBootstrap(Injector injector) {
        URL location = getClass().getProtectionDomain().getCodeSource().getLocation();
        String path = location.getPath();
        if (location.getProtocol().equals("jar")) {
            int pos = path.lastIndexOf('!');
            if (pos >= 0) {
                path = path.substring(0, pos + 2);
            }
        } else {
            path = StringUtils.removeEnd(path, getClass().getName().replace('.', '/') + ".class");
        }

        try {
            ClassLoader loader = getClass().getClassLoader()/*new PoreClassLoader(getClass().getClassLoader(),
                    new URL(location.getProtocol(), location.getHost(), location.getPort(), path))*/;
            Class<?> poreClass = Class.forName(IMPLEMENTATION_CLASS, true, loader);
            pore = (PoreEventManager) injector.getInstance(poreClass);
        } catch (ClassNotFoundException /*| MalformedURLException*/ e) {
            throw new RuntimeException("Failed to load Pore implementation", e);
        }
    }

    @Listener
    @Override
    public void onPreInit(GamePreInitializationEvent event) throws Exception {
        pore.onPreInit(event);
    }

    @Listener
    @Override
    public void onAboutToStart(GameAboutToStartServerEvent event) throws Exception {
        pore.onAboutToStart(event);
    }

    @Listener
    @Override
    public void onStarting(GameStartingServerEvent event) throws Exception {
        pore.onStarting(event);
    }

    @Listener
    @Override
    public void onShutdown(GameStoppingServerEvent event) throws Exception {
        pore.onShutdown(event);
    }

    @Listener(order = Order.POST)
    @Override
    public void onChatEvent(MessageChannelEvent.Chat event) throws Exception {
        pore.onChatEvent(event);
    }

}
