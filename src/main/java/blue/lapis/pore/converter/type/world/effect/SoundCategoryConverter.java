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

package blue.lapis.pore.converter.type.world.effect;

import blue.lapis.pore.converter.type.TypeConverter;

import com.google.common.base.Converter;

import org.spongepowered.api.effect.sound.SoundCategories;
import org.spongepowered.api.effect.sound.SoundCategory;

public final class SoundCategoryConverter {

    private SoundCategoryConverter() {
    }

    private static final Converter<org.bukkit.SoundCategory, SoundCategory> CONVERTER =
            TypeConverter.builder(org.bukkit.SoundCategory.class, SoundCategory.class)
                    .add(org.bukkit.SoundCategory.MASTER, SoundCategories.MASTER)
                    .add(org.bukkit.SoundCategory.MUSIC, SoundCategories.MUSIC)
                    .add(org.bukkit.SoundCategory.RECORDS, SoundCategories.RECORD)
                    .add(org.bukkit.SoundCategory.WEATHER, SoundCategories.WEATHER)
                    .add(org.bukkit.SoundCategory.BLOCKS, SoundCategories.BLOCK)
                    .add(org.bukkit.SoundCategory.HOSTILE, SoundCategories.HOSTILE)
                    .add(org.bukkit.SoundCategory.NEUTRAL, SoundCategories.NEUTRAL)
                    .add(org.bukkit.SoundCategory.PLAYERS, SoundCategories.PLAYER)
                    .add(org.bukkit.SoundCategory.AMBIENT, SoundCategories.AMBIENT)
                    .add(org.bukkit.SoundCategory.VOICE, SoundCategories.VOICE)
                    .build();

    public static SoundCategory of(org.bukkit.SoundCategory sound) {
        return CONVERTER.convert(sound);
    }

    public static org.bukkit.SoundCategory of(SoundCategory sound) {
        return CONVERTER.reverse().convert(sound);
    }
}
