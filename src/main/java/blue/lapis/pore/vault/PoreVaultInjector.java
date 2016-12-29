/*
 * PoreRT - A Bukkit to Sponge Bridge
 *
 * Copyright (c) 2016, Maxqia <https://github.com/Maxqia> AGPLv3
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

package blue.lapis.pore.vault;

import blue.lapis.pore.Pore;
import blue.lapis.pore.util.classloader.LocalClassLoader;
import blue.lapis.pore.util.classloader.LocalReflectClassLoader;

import org.apache.commons.io.IOUtils;
import org.bukkit.plugin.Plugin;

import java.net.URL;

public class PoreVaultInjector {

    public static final String START = "blue.lapis.pore.vault.PoreVault";
    public static final String HOOK = START + "Hook";

    public static void inject() throws Exception {
        Plugin vault = Pore.getServer().getPluginManager().getPlugin("Vault");
        if (vault == null) return;

        ClassLoader loader = vault.getClass().getClassLoader();
        LocalClassLoader injector = new LocalReflectClassLoader(loader);
        for (String addition : new String[]{"Hook", "Chat", "Economy", "Permissions"}) {
            String name = START + addition;
            URL url = Pore.class.getClassLoader().getResource(name.replace('.', '/') + ".class");
            byte[] clazz = IOUtils.toByteArray(url);
            injector.defineClass(name, clazz);
        }
        Class.forName(HOOK, true, loader).getMethod("hook").invoke(null);
    }
}
