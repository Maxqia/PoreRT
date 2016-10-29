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

package blue.lapis.pore.impl.event.block;

import static com.google.common.base.Preconditions.checkNotNull;

import blue.lapis.pore.event.PoreEvent;
import blue.lapis.pore.event.RegisterEvent;
import blue.lapis.pore.event.Source;
import blue.lapis.pore.impl.block.PoreSign;
import blue.lapis.pore.impl.entity.PorePlayer;
import blue.lapis.pore.util.PoreText;

import org.bukkit.block.Block;
import org.bukkit.event.block.SignChangeEvent;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.block.tileentity.ChangeSignEvent;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;

@RegisterEvent
public final class PoreSignChangeEvent extends SignChangeEvent implements PoreEvent<ChangeSignEvent> {

    private final ChangeSignEvent handle;
    private final Player player;

    public PoreSignChangeEvent(ChangeSignEvent handle, @Source Player player) {
        super(null, null, null);
        this.handle = checkNotNull(handle, "handle");
        this.player = checkNotNull(player, "player");
    }

    private PoreSign getTileEntity() {
        return PoreSign.of(getHandle().getTargetTile());
    }

    @Override
    public ChangeSignEvent getHandle() {
        return this.handle;
    }

    @Override
    public Block getBlock() {
        return getTileEntity().getBlock();
    }

    @Override
    public String getLine(int index) throws IndexOutOfBoundsException {
        return PoreText.convert(getHandle().getText().lines().get(index));
    }

    @Override
    public void setLine(int index, String line) throws IndexOutOfBoundsException {
        getHandle().getText().setElement(index, PoreText.convert(line));
    }

    @Override
    public String[] getLines() {
        ArrayList<String> array = new ArrayList<String>();
        for (Text text : getHandle().getText().lines()) {
            array.add(PoreText.convert(text));
        }
        return array.toArray(new String[array.size()]);
    }

    @Override
    public org.bukkit.entity.Player getPlayer() {
        return PorePlayer.of(player);
    }

    @Override
    public boolean isCancelled() {
        return getHandle().isCancelled();
    }

    @Override
    public void setCancelled(boolean cancelled) {
        getHandle().setCancelled(cancelled);
    }

    @Override
    public String toString() {
        return toStringHelper().toString();
    }
}
