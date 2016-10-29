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

import blue.lapis.pore.converter.vector.VectorConverter;

import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.potion.Potion;
import org.spongepowered.api.effect.Viewer;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleType;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.effect.sound.SoundType;
import org.spongepowered.api.effect.sound.SoundTypes;

@SuppressWarnings("deprecation") //TODO fix this
public final class EffectConverter {

    private EffectConverter() {
    }

    public static <T> void playEffect(Viewer viewer, Location location, Effect effect, T data, int radius) {
        if ((data != null && data.getClass().equals(effect.getData()))
                || (data == null && effect.getData() == null)) {
            EffectConverter.playEffect(viewer, location, effect,
                    data == null ? 0 : EffectConverter.getDataValue(effect, data), radius);
        } else {
            throw new IllegalArgumentException("Invalid data type for effect!");
        }
    }

    public static void playEffect(Viewer viewer, Location location, Effect effect, int data, int radius) {
        if (effect.getType() == Effect.Type.SOUND && effect != Effect.STEP_SOUND) {
            //noinspection ConstantConditions
            viewer.playSound(EffectConverter.toSound(effect, data), VectorConverter.create3d(location), radius);
        } else {
            //noinspection ConstantConditions
            //TODO: define a quantity
            viewer.spawnParticles(
                    ParticleEffect.builder().type(EffectConverter.toParticle(effect)).build(),
                    VectorConverter.create3d(location),
                    radius);
        }
    }

    public static SoundType toSound(Effect effect, int data) {
        if (effect.getType() == Effect.Type.SOUND) {
            switch (effect) {
                case CLICK2:
                    return SoundTypes.BLOCK_WOOD_BUTTON_CLICK_ON; //TODO check if it's the right sound
                case CLICK1:
                    return SoundTypes.BLOCK_STONE_BUTTON_CLICK_ON;
                case BOW_FIRE:
                    return SoundTypes.ENTITY_ARROW_SHOOT;
                case DOOR_TOGGLE:
                    return Math.random() >= 0.5
                        ? SoundTypes.BLOCK_WOODEN_DOOR_OPEN : SoundTypes.BLOCK_WOODEN_DOOR_CLOSE;
                case IRON_DOOR_TOGGLE:
                    return Math.random() >= 0.5
                    ? SoundTypes.BLOCK_IRON_DOOR_OPEN : SoundTypes.BLOCK_IRON_DOOR_CLOSE;
                case TRAPDOOR_TOGGLE:
                    return Math.random() >= 0.5
                    ? SoundTypes.BLOCK_WOODEN_TRAPDOOR_OPEN : SoundTypes.BLOCK_WOODEN_TRAPDOOR_CLOSE;
                case IRON_TRAPDOOR_TOGGLE:
                    return Math.random() >= 0.5
                    ? SoundTypes.BLOCK_IRON_TRAPDOOR_OPEN : SoundTypes.BLOCK_IRON_TRAPDOOR_CLOSE;
                case FENCE_GATE_TOGGLE:
                    return Math.random() >= 0.5
                    ? SoundTypes.BLOCK_FENCE_GATE_OPEN : SoundTypes.BLOCK_FENCE_GATE_CLOSE;
                case DOOR_CLOSE:
                    return SoundTypes.BLOCK_WOODEN_DOOR_CLOSE;
                case IRON_DOOR_CLOSE:
                    return SoundTypes.BLOCK_IRON_DOOR_CLOSE;
                case TRAPDOOR_CLOSE:
                    return SoundTypes.BLOCK_IRON_TRAPDOOR_CLOSE;
                case IRON_TRAPDOOR_CLOSE:
                    return SoundTypes.BLOCK_IRON_TRAPDOOR_CLOSE;
                case FENCE_GATE_CLOSE:
                    throw new NotImplementedException("TODO");
                case EXTINGUISH:
                    return SoundTypes.BLOCK_FIRE_EXTINGUISH;
                case RECORD_PLAY:
                    throw new NotImplementedException("TODO");
                case GHAST_SHRIEK:
                    return SoundTypes.ENTITY_GHAST_SCREAM;
                case GHAST_SHOOT:
                    return SoundTypes.ENTITY_GHAST_SHOOT;
                case BLAZE_SHOOT:
                    return SoundTypes.ENTITY_BLAZE_SHOOT;
                case ZOMBIE_CHEW_WOODEN_DOOR:
                    return SoundTypes.ENTITY_ZOMBIE_ATTACK_DOOR_WOOD;
                case ZOMBIE_CHEW_IRON_DOOR:
                    return SoundTypes.ENTITY_ZOMBIE_ATTACK_IRON_DOOR;
                case ZOMBIE_DESTROY_DOOR:
                    return SoundTypes.ENTITY_ZOMBIE_BREAK_DOOR_WOOD;
                case BREWING_STAND_BREW:
                    return SoundTypes.BLOCK_BREWING_STAND_BREW;
                case CHORUS_FLOWER_GROW:
                    return SoundTypes.BLOCK_CHORUS_FLOWER_GROW;
                case CHORUS_FLOWER_DEATH:
                    return SoundTypes.BLOCK_CHORUS_FLOWER_DEATH;
                case PORTAL_TRAVEL:
                    return SoundTypes.BLOCK_PORTAL_TRAVEL;
                case ENDEREYE_LAUNCH:
                    return SoundTypes.ENTITY_ENDEREYE_LAUNCH;
                case FIREWORK_SHOOT:
                    return SoundTypes.ENTITY_FIREWORK_SHOOT;
                case ANVIL_BREAK:
                    return SoundTypes.BLOCK_ANVIL_BREAK;
                case ANVIL_USE:
                    return SoundTypes.BLOCK_ANVIL_USE;
                case ANVIL_LAND:
                    return SoundTypes.BLOCK_ANVIL_LAND;
                case ENDERDRAGON_SHOOT:
                    return SoundTypes.ENTITY_ENDERDRAGON_SHOOT;
                case WITHER_BREAK_BLOCK:
                    return SoundTypes.ENTITY_WITHER_BREAK_BLOCK;
                case WITHER_SHOOT:
                    return SoundTypes.ENTITY_WITHER_SHOOT;
                case ZOMBIE_INFECT:
                    return SoundTypes.ENTITY_ZOMBIE_INFECT;
                case ZOMBIE_CONVERTED_VILLAGER:
                    return SoundTypes.ENTITY_ZOMBIE_VILLAGER_CONVERTED;
                case BAT_TAKEOFF:
                    return SoundTypes.ENTITY_BAT_TAKEOFF;
                case ENDERDRAGON_GROWL:
                    return SoundTypes.ENTITY_ENDERDRAGON_GROWL;
                default:
                    return null;
            }
        }
        return null;
    }

    public static ParticleType toParticle(Effect effect) {
        if (effect.getType() == Effect.Type.VISUAL) {
            switch (effect) {
                case SMOKE:
                    return ParticleTypes.SMOKE;
                case ENDER_SIGNAL:
                    return ParticleTypes.ENDER_TELEPORT;
                case MOBSPAWNER_FLAMES:
                    return ParticleTypes.MOBSPAWNER_FLAMES;
                case VILLAGER_PLANT_GROW: //TODO check
                    return ParticleTypes.FERTILIZER;
                case DRAGON_BREATH: //TODO check
                    return ParticleTypes.DRAGON_BREATH_ATTACK;
                case END_GATEWAY_SPAWN: //TODO actually do
                    throw new NotImplementedException("TODO");
                case STEP_SOUND:
                    return ParticleTypes.BREAK_BLOCK;
                default:
                    return null;
            }
        }
        return null;
    }

    public static <T> int getDataValue(Effect effect, T data) {
        switch (effect) {
            case POTION_BREAK:
                return ((Potion) data).toDamageValue() & Integer.parseInt("111111", 2);
            case RECORD_PLAY:
                if (((Material) data).isRecord()) {
                    return ((Material) data).getId();
                } else {
                    throw new IllegalArgumentException("Data must be a record type!");
                }
            case SMOKE:
                switch ((BlockFace) data) {
                    case UP:
                        return 0;
                    case DOWN:
                        return 0;
                    case SOUTH_EAST:
                        return 0;
                    case SOUTH_SOUTH_EAST:
                        return 0;
                    case SOUTH:
                        return 1;
                    case SOUTH_SOUTH_WEST:
                        return 1;
                    case SOUTH_WEST:
                        return 2;
                    case WEST_SOUTH_WEST:
                        return 2;
                    case EAST:
                        return 3;
                    case EAST_SOUTH_EAST:
                        return 3;
                    case SELF:
                        return 4;
                    case WEST:
                        return 5;
                    case WEST_NORTH_WEST:
                        return 5;
                    case NORTH_EAST:
                        return 6;
                    case EAST_NORTH_EAST:
                        return 6;
                    case NORTH:
                        return 7;
                    case NORTH_NORTH_EAST:
                        return 7;
                    case NORTH_WEST:
                        return 8;
                    case NORTH_NORTH_WEST:
                        return 8;
                    default:
                        throw new IllegalArgumentException("Invalid smoke direction!");
                }
            default:
                return 0;
        }
    }

}
