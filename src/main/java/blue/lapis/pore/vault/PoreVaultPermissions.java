/*
 * PoreRT - A Bukkit to Sponge Bridge
 *
 * Copyright (c) 2016, Maxqia <https://github.com/Maxqia> AGPLv3
 * Copyright (C) zml and PermissionsEx contributors Apache License 2
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
import blue.lapis.pore.util.PoreWrapper;

import net.milkbowl.vault.permission.Permission;
import org.bukkit.OfflinePlayer;
import org.spongepowered.api.service.context.Context;
import org.spongepowered.api.service.permission.PermissionService;
import org.spongepowered.api.service.permission.Subject;
import org.spongepowered.api.service.permission.SubjectData;
import org.spongepowered.api.util.Tristate;

import java.util.ArrayList;
import java.util.Set;

public class PoreVaultPermissions extends Permission {

    @Override
    public String getName() {
        return "Sponge";
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean hasSuperPermsCompat() {
        return true;
    }

    @Override
    public boolean hasGroupSupport() {
        return true;
    }

    public static Subject getUserByOfflinePlayer(OfflinePlayer player) {
        return (Subject) ((PoreWrapper<?>) player).getHandle();
    }

    public static PermissionService getPermsService() {
        return Pore.getGame().getServiceManager()
             .getRegistration(PermissionService.class)
             .get().getProvider();
    }

    public static Subject getGroupByName(String string) {
        return string == null ? null : getPermsService().getGroupSubjects().get(string);
    }

    public static Set<Context> getContextByWorldName(String world) {
        //return Collections.singleton(new Context(Context.WORLD_KEY, world));
        return SubjectData.GLOBAL_CONTEXT; // global context due to my laziness
    }

    @Override
    public String[] getGroups() {
        ArrayList<String> groups = new ArrayList<String>();
        for (Subject subject : getPermsService().getGroupSubjects().getAllSubjects()) {
            groups.add(subject.getIdentifier());
        }
        return groups.toArray(new String[groups.size()]);
    }

    // -- start PEX format copy -- //
    @Override
    public boolean groupHas(String world, String name, String permission) {
        return getGroupByName(name).hasPermission(getContextByWorldName(world), permission);
    }

    @Override
    public boolean groupAdd(final String world, String name, final String permission) {
        return getGroupByName(name).getSubjectData().setPermission(getContextByWorldName(world), permission, Tristate.TRUE);
    }

    @Override
    public boolean groupRemove(final String world, String name, final String permission) {
        return getGroupByName(name).getSubjectData().setPermission(getContextByWorldName(world), permission, Tristate.FALSE);
    }

    @Override
    public boolean playerHas(String world, OfflinePlayer player, String permission) {
        return getUserByOfflinePlayer(player).hasPermission(getContextByWorldName(world), permission);
    }

    @Override
    public boolean playerAdd(final String world, OfflinePlayer player, final String permission) {
        return getUserByOfflinePlayer(player).getSubjectData().setPermission(getContextByWorldName(world), permission, Tristate.TRUE);
    }

    @Override
    public boolean playerRemove(final String world, OfflinePlayer player, final String permission) {
        return getUserByOfflinePlayer(player).getSubjectData().setPermission(getContextByWorldName(world), permission, Tristate.FALSE);
    }


    @Override
    public boolean playerInGroup(String world, OfflinePlayer player, String group) {
        return getUserByOfflinePlayer(player).isChildOf(getContextByWorldName(world), getGroupByName(group));
    }

    @Override
    public boolean playerAddGroup(final String world, OfflinePlayer player, final String group) {
        return getUserByOfflinePlayer(player).getSubjectData().addParent(getContextByWorldName(world), getGroupByName(group));
    }

    @Override
    public boolean playerRemoveGroup(final String world, OfflinePlayer player, final String group) {
        return getUserByOfflinePlayer(player).getSubjectData().removeParent(getContextByWorldName(world), getGroupByName(group));
    }

    @Override
    public String[] getPlayerGroups(String world, OfflinePlayer player) {
        return getUserByOfflinePlayer(player).getParents(getContextByWorldName(world))
                .stream().map(subject -> {
                    return subject.getIdentifier();
                }).toArray(size -> new String[size]);
    }

    @Override // copied
    public String getPrimaryGroup(String world, OfflinePlayer player) {
        String[] groups = getPlayerGroups(world, player);
        return groups.length > 0 ? groups[0] : null;
    }

    // -- Deprecated methods (copied from pex)

    @SuppressWarnings("deprecation")
    private OfflinePlayer pFromName(String name) {
        return this.plugin.getServer().getOfflinePlayer(name);
    }

    @Override
    public boolean playerHas(String world, String name, String permission) {
        return playerHas(world, pFromName(name), permission);
    }

    @Override
    public boolean playerAdd(String world, String name, String permission) {
        return playerAdd(world, pFromName(name), permission);
    }

    @Override
    public boolean playerRemove(String world, String name, String permission) {
        return playerRemove(world, pFromName(name), permission);
    }

    @Override
    public boolean playerInGroup(String world, String player, String group) {
        return playerInGroup(world, pFromName(player), group);
    }

    @Override
    public boolean playerAddGroup(String world, String player, String group) {
        return playerAddGroup(world, pFromName(player), group);
    }

    @Override
    public boolean playerRemoveGroup(String world, String player, String group) {
        return playerRemoveGroup(world, pFromName(player), group);
    }

    @Override
    public String[] getPlayerGroups(String world, String player) {
        return getPlayerGroups(world, pFromName(player));
    }

    @Override
    public String getPrimaryGroup(String world, String player) {
        return getPrimaryGroup(world, pFromName(player));
    }

    @Override
    @Deprecated
    public boolean playerAddTransient(String worldName, String player, String permission) {
        return playerAddTransient(worldName, pFromName(player), permission);
    }

    @Override
    @Deprecated
    public boolean playerRemoveTransient(String worldName, String player, String permission) {
        return playerRemoveTransient(worldName, pFromName(player), permission);
    }

}
