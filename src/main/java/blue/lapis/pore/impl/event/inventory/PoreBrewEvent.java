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

package blue.lapis.pore.impl.event.inventory;

import static com.google.common.base.Preconditions.checkNotNull;

import blue.lapis.pore.event.PoreEvent;
import blue.lapis.pore.event.RegisterEvent;
import blue.lapis.pore.impl.block.PoreBlock;
import blue.lapis.pore.impl.inventory.PoreBrewerInventory;

import org.bukkit.block.Block;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.inventory.BrewerInventory;
import org.spongepowered.api.event.block.tileentity.BrewingEvent;

// I know the bukkit javadocs says end, but you can't cancel BrewingEvent.Finish
@RegisterEvent // TODO Not implemented in Sponge
public final class PoreBrewEvent extends BrewEvent implements PoreEvent<BrewingEvent.Start> {

    private final BrewingEvent.Start handle;

    public PoreBrewEvent(BrewingEvent.Start handle) {
        super(null, null, 0);
        this.handle = checkNotNull(handle, "handle");
    }

    public BrewingEvent.Start getHandle() {
        return this.handle;
    }

    @Override
    public Block getBlock() {
        return PoreBlock.of(this.getHandle().getTargetTile().getLocation());
    }

    @Override
    public BrewerInventory getContents() {
        return PoreBrewerInventory.of(this.getHandle().getTargetTile().getInventory());
    }

    @Override
    public int getFuelLevel() {
        return getContents().getFuel().getAmount();
    }

    @Override
    public boolean isCancelled() {
        return this.getHandle().isCancelled();
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.getHandle().setCancelled(cancel);
    }

    @Override
    public String toString() {
        return toStringHelper().toString();
    }
}
