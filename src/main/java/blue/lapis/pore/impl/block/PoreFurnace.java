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

package blue.lapis.pore.impl.block;

import blue.lapis.pore.converter.wrapper.WrapperConverter;
import blue.lapis.pore.impl.inventory.PoreFurnaceInventory;

import org.bukkit.inventory.FurnaceInventory;
import org.spongepowered.api.block.tileentity.carrier.Furnace;

public class PoreFurnace extends PoreContainer implements org.bukkit.block.Furnace {

    public static PoreFurnace of(Furnace handle) {
        return WrapperConverter.of(PoreFurnace.class, handle);
    }

    protected PoreFurnace(Furnace handle) {
        super(handle);
    }

    @Override
    Furnace getTileEntity() {
        return (Furnace) super.getTileEntity();
    }

    @Override
    public short getBurnTime() { //TODO is this correct
        return getTileEntity().passedBurnTime().get().shortValue();
    }

    @Override
    public void setBurnTime(short burnTime) {
        getTileEntity().passedBurnTime().set((int)burnTime);
    }

    @Override
    public short getCookTime() {
        return getTileEntity().passedCookTime().get().shortValue();
    }

    @Override
    public void setCookTime(short cookTime) {
        getTileEntity().passedCookTime().set((int)cookTime);
    }

    @Override
    public FurnaceInventory getInventory() {
        return PoreFurnaceInventory.of(getTileEntity().getInventory());
    }

}
