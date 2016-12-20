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

package blue.lapis.pore.impl.entity;

import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.entity.ChestedHorse;
import org.spongepowered.api.entity.living.animal.Horse;

public class PoreChestedHorse extends PoreAbstractHorse implements ChestedHorse {

    protected PoreChestedHorse(Horse handle) {
        super(handle);
    }

    @Override
    public boolean isCarryingChest() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public void setCarryingChest(boolean chest) {
        throw new NotImplementedException("TODO");
    }

}
