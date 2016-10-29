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

package blue.lapis.pore.converter.type.cause;

import blue.lapis.pore.converter.type.TypeConverter;

import com.google.common.base.Converter;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.spongepowered.api.event.cause.entity.spawn.SpawnType;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;

public class SpawnReasonConverter {
    private SpawnReasonConverter() {
    }

    private static final Converter<SpawnType, SpawnReason> CONVERTER =
            TypeConverter.builder(SpawnType.class, SpawnReason.class)
                    .add(SpawnTypes.BLOCK_SPAWNING, SpawnReason.SILVERFISH_BLOCK)
                    .add(SpawnTypes.BREEDING, SpawnReason.BREEDING)
                    .add(SpawnTypes.CHUNK_LOAD, SpawnReason.CHUNK_GEN)
                    .add(SpawnTypes.CUSTOM, SpawnReason.CUSTOM)
                    .add(SpawnTypes.DISPENSE, SpawnReason.DISPENSE_EGG)
                    //.add(SpawnTypes.DROPPED_ITEM, SpawnReason.DEFAULT)
                    //.add(SpawnTypes.EXPERIENCE, SpawnReason.DEFAULT)
                    //.add(SpawnTypes.FALLING_BLOCK, SpawnReason.DEFAULT)
                    .add(SpawnTypes.MOB_SPAWNER, SpawnReason.SPAWNER)
                    //.add(SpawnTypes.PASSIVE, SpawnReason.DEFAULT)
                    //.add(SpawnTypes.PLACEMENT, SpawnReason.CUSTOM)
                    //.add(SpawnTypes.PLUGIN, SpawnReason.CUSTOM)
                    //.add(SpawnTypes.PROJECTILE, SpawnReason.DEFAULT)
                    .add(SpawnTypes.SPAWN_EGG, SpawnReason.SPAWNER_EGG)
                    //.add(SpawnTypes.STRUCTURE, SpawnReason.DEFAULT)
                    //.add(SpawnTypes.TNT_IGNITE, SpawnReason.DEFAULT)
                    //.add(SpawnTypes.WEATHER, SpawnReason.NATURAL)
                    .add(SpawnTypes.WORLD_SPAWNER, SpawnReason.NATURAL)
                    .build();

    public static SpawnReason of(SpawnType type) {
        try {
            return CONVERTER.convert(type);
        } catch (UnsupportedOperationException ex) {
            return SpawnReason.DEFAULT;
        }
    }
}
