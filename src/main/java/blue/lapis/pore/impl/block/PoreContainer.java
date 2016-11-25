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

package blue.lapis.pore.impl.block;

import org.bukkit.block.Lockable;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.data.manipulator.mutable.tileentity.LockableData;

import java.util.Optional;

public class PoreContainer extends PoreBlockState implements Lockable {

    protected PoreContainer(TileEntity handle) {
        super(handle);
    }

    @Override
    public boolean isLocked() {
        Optional<LockableData> lock = getTileEntity().get(LockableData.class);
        if (lock.isPresent()) {
            if (lock.get().lockToken().exists()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getLock() {
        return getTileEntity().get(LockableData.class).get().lockToken().get();
    }

    @Override
    public void setLock(String key) {
        getTileEntity().get(LockableData.class).get().lockToken().set(key);
    }

}
