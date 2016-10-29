/*
 * Pore(RT)
 * Copyright (c) 2014-2016, Lapis <https://github.com/LapisBlue>
 * Copyright (c) 2014-2016, Contributors
 *
 * The MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package blue.lapis.pore.impl.event.block;

import static com.google.common.base.Preconditions.checkNotNull;

import blue.lapis.pore.event.PoreEvent;
import blue.lapis.pore.event.RegisterEvent;
import blue.lapis.pore.event.Source;
import blue.lapis.pore.impl.block.PoreSign;
import blue.lapis.pore.impl.entity.PorePlayer;

import org.bukkit.block.Block;
import org.bukkit.event.block.SignChangeEvent;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.block.tileentity.ChangeSignEvent;

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
        return getTileEntity().getLine(index);
    }

    @Override
    public void setLine(int index, String line) throws IndexOutOfBoundsException {
        getTileEntity().setLine(index, line);;
    }

    @Override
    public String[] getLines() {
        return getTileEntity().getLines();
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
