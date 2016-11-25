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

import blue.lapis.pore.converter.type.entity.LlamaConverter;
import blue.lapis.pore.converter.wrapper.WrapperConverter;
import blue.lapis.pore.impl.inventory.PoreInventory;

import org.bukkit.inventory.LlamaInventory;
import org.spongepowered.api.entity.living.animal.Llama;

public class PoreLlama extends PoreChestedHorse implements org.bukkit.entity.Llama {

    public static PoreLlama of(Llama handle) {
        return WrapperConverter.of(PoreLlama.class, handle);
    }

    protected PoreLlama(Llama handle) {
        super(handle);
    }

    @Override
    public Llama getHandle() {
        return (Llama) super.getHandle();
    }

    @Override
    public Color getColor() {
        return LlamaConverter.of(getHandle().llamaVariant().get());
    }

    @Override
    public void setColor(Color color) {
        getHandle().llamaVariant().set(LlamaConverter.of(color));
    }

    @Override
    public int getStrength() {
        return getHandle().strength().get();
    }

    @Override
    public void setStrength(int strength) {
        getHandle().strength().set(strength);

    }

    @Override
    public LlamaInventory getInventory() { // TODO LlamaInventory
        return (LlamaInventory) PoreInventory.of(getHandle().getInventory());
    }

}
