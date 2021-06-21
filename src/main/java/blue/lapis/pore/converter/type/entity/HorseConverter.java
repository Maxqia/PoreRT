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


package blue.lapis.pore.converter.type.entity;

import blue.lapis.pore.converter.type.TypeConverter;

import com.google.common.base.Converter;
import org.bukkit.entity.Horse;
import org.spongepowered.api.data.type.HorseColor;
import org.spongepowered.api.data.type.HorseColors;
import org.spongepowered.api.data.type.HorseStyle;
import org.spongepowered.api.data.type.HorseStyles;

public final class HorseConverter {

    private HorseConverter() {
    }

    private static final Converter<Horse.Color, HorseColor> COLOR_CONVERTER =
            TypeConverter.builder(Horse.Color.class, HorseColor.class)
                    .add(Horse.Color.WHITE, HorseColors.WHITE)
                    .add(Horse.Color.CREAMY, HorseColors.CREAMY)
                    .add(Horse.Color.CHESTNUT, HorseColors.CHESTNUT)
                    .add(Horse.Color.BROWN, HorseColors.BROWN)
                    .add(Horse.Color.BLACK, HorseColors.BLACK)
                    .add(Horse.Color.GRAY, HorseColors.GRAY)
                    .add(Horse.Color.DARK_BROWN, HorseColors.DARK_BROWN)
                    .build();

    public static HorseColor of(Horse.Color color) {
        return COLOR_CONVERTER.convert(color);
    }

    public static Horse.Color of(HorseColor color) {
        return COLOR_CONVERTER.reverse().convert(color);
    }

    private static final Converter<Horse.Style, HorseStyle> STYLE_CONVERTER =
            TypeConverter.builder(Horse.Style.class, HorseStyle.class)
                    .add(Horse.Style.NONE, HorseStyles.NONE)
                    .add(Horse.Style.WHITE, HorseStyles.WHITE)
                    .add(Horse.Style.WHITEFIELD, HorseStyles.WHITEFIELD)
                    .add(Horse.Style.WHITE_DOTS, HorseStyles.WHITE_DOTS)
                    .add(Horse.Style.BLACK_DOTS, HorseStyles.BLACK_DOTS)
                    .build();

    public static HorseStyle of(Horse.Style style) {
        return STYLE_CONVERTER.convert(style);
    }

    public static Horse.Style of(HorseStyle style) {
        return STYLE_CONVERTER.reverse().convert(style);
    }

}
