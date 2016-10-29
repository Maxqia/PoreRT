/*
 * PoreRT - A Bukkit to Sponge Bridge
 *
 * Copyright (c) 2016, Maxqia <https://github.com/Maxqia> AGPLv3
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

package blue.lapis.pore.impl.block;

import blue.lapis.pore.converter.wrapper.WrapperConverter;
import blue.lapis.pore.util.PoreText;

import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;

public class PoreSign extends PoreBlockState implements org.bukkit.block.Sign {

    public static PoreSign of(Sign handle) {
        return WrapperConverter.of(PoreSign.class, handle);
    }

    protected PoreSign(Sign handle) {
        super(handle);
    }

    @Override
    Sign getTileEntity() {
        return (Sign) super.getTileEntity();
    }

    @Override
    public String[] getLines() {
        ArrayList<String> array = new ArrayList<String>();
        for (Text text : getTileEntity().lines()) {
            array.add(PoreText.convert(text));
        }
        return array.toArray(new String[array.size()]);
    }

    @Override
    public String getLine(int index) throws IndexOutOfBoundsException {
        return PoreText.convert(getTileEntity().lines().get(index));
    }

    @Override
    public void setLine(int index, String line) throws IndexOutOfBoundsException {
        getTileEntity().getSignData().setElement(index, PoreText.convert(line));
    }
}
