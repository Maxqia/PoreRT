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

import blue.lapis.pore.converter.type.material.MaterialConverter;
import blue.lapis.pore.converter.type.material.PotionEffectTypeConverter;
import blue.lapis.pore.converter.type.world.DirectionConverter;
import blue.lapis.pore.converter.vector.VectorConverter;

import net.minecraft.potion.PotionType;
import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffectType;
import org.spongepowered.api.effect.Viewer;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleOption;
import org.spongepowered.api.effect.particle.ParticleOptions;
import org.spongepowered.api.effect.particle.ParticleType;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.effect.sound.SoundType;
import org.spongepowered.api.effect.sound.SoundTypes;
import org.spongepowered.api.util.Direction;

@SuppressWarnings("deprecation") //TODO fix this
public final class EffectConverter {

    private EffectConverter() {
    }

    public static <T> void playEffect(Viewer viewer, Location location, Effect effect, T data, int radius) {
        if ((data != null && data.getClass().equals(effect.getData()))
                || (data == null && effect.getData() == null)) {
            EffectConverter.playInternal(viewer, location, effect, data, radius);
        } else {
            throw new IllegalArgumentException("Invalid data type for effect!");
        }
    }

    public static void playEffect(Viewer viewer, Location location, Effect effect, int data, int radius) {
        EffectConverter.playInternal(viewer, location, effect, data, radius);
    }

    private static <T> void playInternal(Viewer viewer, Location location, Effect effect, T data, int radius) {
        if (effect.getType() == Effect.Type.SOUND && effect != Effect.STEP_SOUND) {
            //noinspection ConstantConditions
            viewer.playSound(EffectConverter.toSound(effect, 0), VectorConverter.create3d(location), radius);
        } else {
            ParticleEffect.Builder builder = ParticleEffect.builder().type(EffectConverter.toParticle(effect));
            if (data instanceof Integer) addIntegerOptions(builder, effect, (Integer)data);
            else addGenericOptions(builder, effect, data);
            viewer.spawnParticles(
                    builder.build(),
                    VectorConverter.create3d(location),
                    radius);
        }
    }

    public static SoundType toSound(Effect effect, int data) {
        //if (effect.getType() == Effect.Type.SOUND) {
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
                    throw new NotImplementedException("Unknown Sound Effect : " + effect.getName());
            }
        //}
        //throw new RuntimeException("Requested Sound for type \"" + effect.getType() + "\" effect");
    }

    public static ParticleType toParticle(Effect effect) {
        //if (effect.getType() == Effect.Type.VISUAL) {
            switch (effect) {
                case POTION_BREAK:
                    return ParticleTypes.SPLASH_POTION;
                case SMOKE:
                    return ParticleTypes.FIRE_SMOKE;
                case ENDER_SIGNAL:
                    return ParticleTypes.ENDER_TELEPORT;
                case MOBSPAWNER_FLAMES:
                    return ParticleTypes.MOBSPAWNER_FLAMES;
                case VILLAGER_PLANT_GROW:
                    return ParticleTypes.FERTILIZER;
                case DRAGON_BREATH:
                    return ParticleTypes.DRAGON_BREATH_ATTACK;
                case END_GATEWAY_SPAWN: //TODO actually do
                    throw new NotImplementedException("TODO");
                case STEP_SOUND:
                    return ParticleTypes.BREAK_BLOCK;
                default:
                    throw new NotImplementedException("Unknown Particle Effect : " + effect.getName());
            }
        //}
        //throw new RuntimeException("Requested Particle for type \"" + effect.getType() + "\" effect");
    }

    public static <T> void addGenericOptions(ParticleEffect.Builder builder, Effect effect, T data) {
        switch (effect) {
            case POTION_BREAK:
                builder.option(ParticleOptions.POTION_EFFECT_TYPE, PotionEffectTypeConverter.of(((Potion) data).getType().getEffectType()));
                break;
            /*case RECORD_PLAY: //TODO
                if (((Material) data).isRecord()) {
                    return ((Material) data).getId();
                } else {
                    throw new IllegalArgumentException("Data must be a record type!");
                }*/
            case SMOKE:
                builder.option(ParticleOptions.DIRECTION, DirectionConverter.of(((BlockFace) data)));
                break;
            case VILLAGER_PLANT_GROW:
                builder.option(ParticleOptions.QUANTITY, (Integer) data);
                break;
            case STEP_SOUND:
                builder.option(ParticleOptions.ITEM_STACK_SNAPSHOT, MaterialConverter.asItem(((Material)data)).getTemplate());
                break;
            default :
                break;
        }
    }

    public static void addIntegerOptions(ParticleEffect.Builder builder,Effect effect, int data) {
        switch (effect) {
            case POTION_BREAK:
                builder.option(ParticleOptions.POTION_EFFECT_TYPE, (org.spongepowered.api.effect.potion.PotionEffectType) PotionType.REGISTRY.getObjectById(data).getEffects().get(0).getPotion());
                break;
            /*case RECORD_PLAY: //TODO
                if (((Material) data).isRecord()) {
                    return ((Material) data).getId();
                } else {
                    throw new IllegalArgumentException("Data must be a record type!");
                }*/
            case SMOKE:
                Direction direction;
                switch (data) {
                    case 0:
                        direction = Direction.SOUTHEAST;
                        break;
                    case 1:
                        direction = Direction.SOUTH;
                        break;
                    case 2:
                        direction = Direction.SOUTHWEST;
                        break;
                    case 3:
                        direction = Direction.EAST;
                        break;
                    case 5:
                        direction = Direction.WEST;
                        break;
                    case 6:
                        direction = Direction.NORTHEAST;
                        break;
                    case 7:
                        direction = Direction.NORTH;
                        break;
                    case 8:
                        direction = Direction.NORTHWEST;
                        break;
                    default: // this maps to 4
                        direction = Direction.NONE;
                        break;
                }
                builder.option(ParticleOptions.DIRECTION, direction);
                break;
            case VILLAGER_PLANT_GROW:
                builder.option(ParticleOptions.QUANTITY, data);
                break;
            case STEP_SOUND:
                builder.option(ParticleOptions.ITEM_STACK_SNAPSHOT, MaterialConverter.asItem(Material.getMaterial(data)).getTemplate());
                break;
            default:
                break;
        }
    }

}
