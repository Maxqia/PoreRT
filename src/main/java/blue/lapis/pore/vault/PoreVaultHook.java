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

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;

public class PoreVaultHook {
    public static void hook() {
        Plugin vault = Pore.getServer().getPluginManager().getPlugin("Vault");
        if (vault == null || !vault.isEnabled()) return;

        final ServicesManager sm = Bukkit.getServer().getServicesManager();

        PoreVaultPermissions permissions = new PoreVaultPermissions();
        PoreVaultChat chat = new PoreVaultChat(permissions);

        sm.register(Permission.class, permissions, vault, ServicePriority.Highest);
        sm.register(Chat.class, chat, vault, ServicePriority.Highest);
        Pore.getLogger().info("Registered Vault Permission and Chat Hook");
    }
}
