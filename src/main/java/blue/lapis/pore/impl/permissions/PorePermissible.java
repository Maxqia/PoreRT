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

package blue.lapis.pore.impl.permissions;

import blue.lapis.pore.Pore;
import blue.lapis.pore.util.PoreWrapper;

import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.permissions.Permissible;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.spongepowered.api.service.permission.Subject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

// We extend PoreWrapper<Object> here because Entity is permissable, while sponge's isn't
public class PorePermissible extends PoreWrapper<Object> implements Permissible {

    private List<PermissionAttachment> attachments = new ArrayList<>();

    protected PorePermissible(Subject handle) {
        super(handle);
    }

    protected PorePermissible(Object handle) {
        super(handle);
    }

    private boolean isSubject() {
        return getHandle() instanceof Subject;
    }

    private Subject getSubject() {
        return (Subject) getHandle();
    }

    @Override
    public boolean isOp() {
        return hasPermission("pore.op"); // Sponge defaults to true for an unknown permission node if you're an op
    }

    @Override
    public void setOp(boolean value) {
        /*
         * In the hundreds of plugins I reviewed while BukkitDev staff, I
         * literally only saw this method used by force-op plugins. I know
         * because the first thing I would do when reviewing the source was
         * CTRL+F -> setOp. Any matches and it was almost sure to be rejected.
         * That's why I don't think an UOE will be too problemeatic here.
         * - caseif
         */
        throw new UnsupportedOperationException("Sponge does not support server operators");
    }

    @Override
    public boolean isPermissionSet(String name) {
        if (isSubject()) {
            Subject subject = getSubject();
            return subject.getPermissionValue(subject.getActiveContexts(), name).asBoolean();
        }
        return false;
    }

    @Override
    public boolean isPermissionSet(Permission perm) {
        return isPermissionSet(perm.getName());
    }

    @Override
    public boolean hasPermission(String name) {
        if (isSubject()) {
            Subject subject = getSubject();
            return subject.hasPermission(name);
        }
        return false;
    }

    @Override
    public boolean hasPermission(Permission perm) {
        return hasPermission(perm.getName());
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, String name, boolean value) {
        return addAttachment(plugin, name, value, -1);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin) {
        return addAttachment(plugin, -1);
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, final String name, boolean value, int ticks) {
        PermissionAttachment attachment = addAttachment(plugin, ticks);
        attachment.setPermission(name, value);
        return attachment;
    }

    @Override
    public PermissionAttachment addAttachment(Plugin plugin, int ticks) {
        final PermissionAttachment attachment = new PermissionAttachment(plugin, this);
        attachments.add(attachment);
        if (ticks > 0) {
            Pore.getGame().getScheduler().createTaskBuilder()
                    .delayTicks(ticks)
                    .execute(() -> removeAttachment(attachment)).submit(Pore.getPlugin());
        }
        recalculatePermissions();
        return attachment;
    }

    @Override
    public void removeAttachment(PermissionAttachment attachment) {
        attachments.remove(attachment);
        recalculatePermissions();
    }

    @Override
    public void recalculatePermissions() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        Set<PermissionAttachmentInfo> perms = new HashSet<>();
        for (PermissionAttachment attachment : attachments) {
            for (Entry<String, Boolean> entry : attachment.getPermissions().entrySet()) {
                perms.add(new PermissionAttachmentInfo(this, entry.getKey(), attachment, entry.getValue()));
            }
        }
        return perms;
    }

}
