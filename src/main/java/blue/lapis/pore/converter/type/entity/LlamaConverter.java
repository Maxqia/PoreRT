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

package blue.lapis.pore.converter.type.entity;

import blue.lapis.pore.converter.type.TypeConverter;

import com.google.common.base.Converter;
import org.bukkit.entity.Llama;
import org.spongepowered.api.data.type.LlamaVariant;
import org.spongepowered.api.data.type.LlamaVariants;

public final class LlamaConverter {

    private static final Converter<Llama.Color, LlamaVariant> CONVERTER =
            TypeConverter.builder(Llama.Color.class, LlamaVariant.class)
                    .add(Llama.Color.CREAMY, LlamaVariants.CREAMY)
                    .add(Llama.Color.WHITE, LlamaVariants.WHITE)
                    .add(Llama.Color.BROWN, LlamaVariants.BROWN)
                    .add(Llama.Color.GRAY, LlamaVariants.GRAY)
                    .build();

    public static LlamaVariant of(Llama.Color color) {
        return CONVERTER.convert(color);
    }

    public static Llama.Color of(LlamaVariant color) {
        return CONVERTER.reverse().convert(color);
    }
}
