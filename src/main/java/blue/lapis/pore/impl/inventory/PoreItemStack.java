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

package blue.lapis.pore.impl.inventory;

import blue.lapis.pore.converter.type.material.MaterialConverter;
import blue.lapis.pore.impl.inventory.meta.PoreItemMeta;


import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.property.item.UseLimitProperty;
import org.spongepowered.api.item.inventory.ItemStack;

import java.lang.reflect.Field;
import java.util.Optional;

public class PoreItemStack extends org.bukkit.inventory.ItemStack {

    private ItemStack handle;

    public PoreItemStack(ItemStack handle) {
        super(MaterialConverter.of(handle.getItem()));
        this.handle = handle;
        try {
            Field field = this.getClass().getSuperclass().getDeclaredField("meta");
            field.setAccessible(true);
            field.set(this, new PoreItemMeta(handle));
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
            this.setItemMeta(new PoreItemMeta(handle)); //wat
        }
    }

    public ItemStack getHandle() {
        return handle;
    }

    @Override
    public short getDurability() {
        Optional<UseLimitProperty> maxdur = getHandle().getProperty(UseLimitProperty.class);
        if (maxdur.isPresent()) {
            int dur = getHandle().get(Keys.ITEM_DURABILITY).orElse(0);
            return (short) (maxdur.get().getValue() - dur);
        }
        return 0;
    }

    @Override
    public void setDurability(short durability) {
        int maxdur = getHandle().getProperty(UseLimitProperty.class).get().getValue();
        getHandle().offer(Keys.ITEM_DURABILITY, maxdur - durability);
        return;
    }

    @Override
    public int getAmount() {
        return getHandle().getQuantity();
    }

    @Override
    public void setAmount(int amount) {
        getHandle().setQuantity(amount);
    }
}
