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
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.spongepowered.api.event.cause.entity.damage.DamageType;
import org.spongepowered.api.event.cause.entity.damage.DamageTypes;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSources;

public class DamageCauseConverter {
    private DamageCauseConverter() {
    }

    private static final Converter<DamageCause, DamageSource> CONVERTER1 =
            TypeConverter.builder(DamageCause.class, DamageSource.class)
                    //.add(DamageCause.CONTACT, DamageSources.)
                    //.add(DamageCause.ENTITY_ATTACK, DamageSources.)
                    //.add(DamageCause.PROJECTILE, DamageSources.)
                    //.add(DamageCause.SUFFOCATION, DamageSources.)
                    .add(DamageCause.FALL, DamageSources.FALLING)
                    //.add(DamageCause.FIRE, DamageSources.)
                    .add(DamageCause.FIRE_TICK, DamageSources.FIRE_TICK)
                    .add(DamageCause.MELTING, DamageSources.MELTING)
                    //.add(DamageCause.LAVA, DamageSources.)
                    .add(DamageCause.DROWNING, DamageSources.DROWNING)
                    //.add(DamageCause.BLOCK_EXPLOSION, DamageSources.)
                    //.add(DamageCause.ENTITY_EXPLOSION, DamageSources.)
                    .add(DamageCause.VOID, DamageSources.VOID)
                    //.add(DamageCause.LIGHTNING, DamageSources.)
                    //.add(DamageCause.SUICIDE, DamageSources.)
                    .add(DamageCause.STARVATION, DamageSources.STARVATION)
                    .add(DamageCause.POISON, DamageSources.POISON)
                    .add(DamageCause.MAGIC, DamageSources.MAGIC)
                    .add(DamageCause.WITHER, DamageSources.WITHER)
                    //.add(DamageCause.FALLING_BLOCK, DamageSources.)
                    //.add(DamageCause.THORNS, DamageSources.)
                    //.add(DamageCause.DRAGON_BREATH, DamageSources.)
                    .add(DamageCause.CUSTOM, DamageSources.GENERIC)
                    //.add(DamageCause.FLY_INTO_WALL, DamageSources.)
                    //.add(DamageCause.HOT_FLOOR, DamageSources.)
                    .build();

    private static final Converter<DamageCause, DamageType> CONVERTER2 =
            TypeConverter.builder(DamageCause.class, DamageType.class)
                    .add(DamageCause.CONTACT, DamageTypes.CONTACT)
                    //.add(DamageCause.ENTITY_ATTACK, DamageTypes.)
                    .add(DamageCause.PROJECTILE, DamageTypes.PROJECTILE)
                    .add(DamageCause.SUFFOCATION, DamageTypes.SUFFOCATE)
                    .add(DamageCause.FALL, DamageTypes.FALL)
                    .add(DamageCause.FIRE, DamageTypes.FIRE)
                    //.add(DamageCause.FIRE_TICK, DamageTypes.)
                    //.add(DamageCause.MELTING, DamageTypes.MELTING)
                    //.add(DamageCause.LAVA, DamageTypes.)
                    .add(DamageCause.DROWNING, DamageTypes.DROWN)
                    //.add(DamageCause.BLOCK_EXPLOSION, DamageTypes.)
                    //.add(DamageCause.ENTITY_EXPLOSION, DamageTypes.)
                    .add(DamageCause.VOID, DamageTypes.VOID)
                    //.add(DamageCause.LIGHTNING, DamageTypes.)
                    //.add(DamageCause.SUICIDE, DamageTypes.)
                    .add(DamageCause.STARVATION, DamageTypes.HUNGER)
                    //.add(DamageCause.POISON, DamageTypes.)
                    .add(DamageCause.MAGIC, DamageTypes.MAGIC)
                    //.add(DamageCause.WITHER, DamageTypes.WITHER)
                    //.add(DamageCause.FALLING_BLOCK, DamageType.)
                    //.add(DamageCause.THORNS, DamageType.)
                    //.add(DamageCause.DRAGON_BREATH, DamageType.)
                    .add(DamageCause.CUSTOM, DamageTypes.GENERIC)
                    //.add(DamageCause.FLY_INTO_WALL, DamageTypes.)
                    .add(DamageCause.HOT_FLOOR, DamageTypes.MAGMA)
                    .build();

    public static DamageCause of(DamageSource type) {
        try {
            return CONVERTER1.reverse().convert(type);
        } catch (UnsupportedOperationException ex) {
            try {
                return CONVERTER2.reverse().convert(type.getType());
            } catch (UnsupportedOperationException exp) {
                return DamageCause.CUSTOM;
            }
        }
    }
}
