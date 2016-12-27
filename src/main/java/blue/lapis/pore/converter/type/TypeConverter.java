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

package blue.lapis.pore.converter.type;

import com.google.common.base.Converter;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import javax.annotation.Nonnull;

public final class TypeConverter<B, S> extends Converter<B, S> {

    private final BiMap<B, S> registry = HashBiMap.create();

    private TypeConverter() {
    }

    public static <B, S> TypeConverter<B, S> builder(Class<B> bukkit, Class<S> sponge) {
        return new TypeConverter<B,S>();
    }

    public TypeConverter<B, S> build() {
        return this;
    }

    private static <T> T checkDefined(T result, Object input) {
        if (result == null) {
            throw new UnsupportedOperationException(input.toString());
        }
        return result;
    }

    @Override
    protected S doForward(B bukkit) {
        if (bukkit == null) {
            return null;
        }
        return checkDefined(registry.get(bukkit), bukkit);
    }

    @Override
    protected B doBackward(S sponge) {
        if (sponge == null) {
            return null;
        }
        return checkDefined(registry.inverse().get(sponge), sponge);
    }

    public TypeConverter<B, S> add(@Nonnull B bukkit, @Nonnull S sponge) {
        registry.put(bukkit, sponge);
        return this;
    }
}
