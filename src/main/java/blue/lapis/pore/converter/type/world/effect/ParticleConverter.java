package blue.lapis.pore.converter.type.world.effect;

import blue.lapis.pore.converter.type.TypeConverter;

import com.flowpowered.math.vector.Vector3d;
import com.google.common.base.Converter;
import org.bukkit.Particle;
import org.spongepowered.api.effect.Viewer;
import org.spongepowered.api.effect.particle.ParticleEffect;
import org.spongepowered.api.effect.particle.ParticleType;
import org.spongepowered.api.effect.particle.ParticleTypes;

public final class ParticleConverter {
    private ParticleConverter() {
    }

    private static final Converter<Particle, ParticleType> CONVERTER =
            TypeConverter.builder(Particle.class, ParticleType.class)
                    .add(Particle.EXPLOSION_NORMAL, ParticleTypes.EXPLOSION)
                    .add(Particle.EXPLOSION_LARGE, ParticleTypes.LARGE_EXPLOSION)
                    .add(Particle.EXPLOSION_HUGE, ParticleTypes.HUGE_EXPLOSION)
                    .add(Particle.FIREWORKS_SPARK, ParticleTypes.FIREWORKS_SPARK)
                    .add(Particle.WATER_BUBBLE, ParticleTypes.WATER_BUBBLE)
                    .add(Particle.WATER_SPLASH, ParticleTypes.WATER_SPLASH)
                    .add(Particle.WATER_WAKE, ParticleTypes.WATER_WAKE)
                    .add(Particle.SUSPENDED, ParticleTypes.SUSPENDED)
                    .add(Particle.SUSPENDED_DEPTH, ParticleTypes.SUSPENDED_DEPTH)
                    .add(Particle.CRIT, ParticleTypes.CRITICAL_HIT)
                    .add(Particle.CRIT_MAGIC, ParticleTypes.MAGIC_CRITICAL_HIT)
                    .add(Particle.SMOKE_NORMAL, ParticleTypes.SMOKE)
                    .add(Particle.SMOKE_LARGE, ParticleTypes.LARGE_SMOKE)
                    .add(Particle.SPELL, ParticleTypes.SPELL)
                    .add(Particle.SPELL_INSTANT, ParticleTypes.INSTANT_SPELL)
                    .add(Particle.SPELL_MOB, ParticleTypes.MOB_SPELL)
                    .add(Particle.SPELL_MOB_AMBIENT, ParticleTypes.AMBIENT_MOB_SPELL)
                    .add(Particle.SPELL_WITCH, ParticleTypes.WITCH_SPELL)
                    .add(Particle.DRIP_WATER, ParticleTypes.DRIP_WATER)
                    .add(Particle.DRIP_LAVA, ParticleTypes.DRIP_LAVA)
                    .add(Particle.VILLAGER_ANGRY, ParticleTypes.ANGRY_VILLAGER)
                    .add(Particle.VILLAGER_HAPPY, ParticleTypes.HAPPY_VILLAGER)
                    .add(Particle.TOWN_AURA, ParticleTypes.TOWN_AURA)
                    .add(Particle.NOTE, ParticleTypes.NOTE)
                    .add(Particle.PORTAL, ParticleTypes.PORTAL)
                    .add(Particle.ENCHANTMENT_TABLE, ParticleTypes.ENCHANTING_GLYPHS) // mkay
                    .add(Particle.FLAME, ParticleTypes.FLAME)
                    .add(Particle.LAVA, ParticleTypes.LAVA)
                    .add(Particle.FOOTSTEP, ParticleTypes.FOOTSTEP)
                    .add(Particle.CLOUD, ParticleTypes.CLOUD)
                    .add(Particle.REDSTONE, ParticleTypes.REDSTONE_DUST) //idk
                    .add(Particle.SNOWBALL, ParticleTypes.SNOWBALL)
                    .add(Particle.SNOW_SHOVEL, ParticleTypes.SNOW_SHOVEL)
                    .add(Particle.SLIME, ParticleTypes.SLIME)
                    .add(Particle.HEART, ParticleTypes.HEART)
                    .add(Particle.BARRIER, ParticleTypes.BARRIER)
                    .add(Particle.ITEM_CRACK, ParticleTypes.ITEM_CRACK)
                    .add(Particle.BLOCK_CRACK, ParticleTypes.BLOCK_CRACK)
                    .add(Particle.BLOCK_DUST, ParticleTypes.BLOCK_DUST)
                    .add(Particle.WATER_DROP, ParticleTypes.WATER_DROP)
                    //.add(Particle.ITEM_TAKE, ParticleTypes.) //idk
                    //.add(Particle.MOB_APPEARANCE, ParticleTypes.)
                    .add(Particle.DRAGON_BREATH, ParticleTypes.DRAGON_BREATH)
                    .add(Particle.END_ROD, ParticleTypes.END_ROD)
                    .add(Particle.DAMAGE_INDICATOR, ParticleTypes.DAMAGE_INDICATOR)
                    .add(Particle.SWEEP_ATTACK, ParticleTypes.SWEEP_ATTACK)
                    .add(Particle.FALLING_DUST, ParticleTypes.FALLING_DUST)
                    //.add(Particle.TOTEM, ParticleTypes.)
                    //.add(Particle.SPIT, ParticleTypes.)
                    .build();

    public static ParticleType of(Particle biome) {
        return CONVERTER.convert(biome);
    }

    public static Particle of(ParticleType biomeType) {
        return CONVERTER.reverse().convert(biomeType);
    }

    public static <T> void spawnParticle(Viewer viewer, Particle particle, double x, double y, double z, int count, double offsetX, double offsetY, double offsetZ, double extra, T data) {
        viewer.spawnParticles(ParticleEffect.builder().type(of(particle)).quantity(count).offset(new Vector3d(offsetX, offsetY, offsetZ)).build(), new Vector3d(x,y,z));
    } // IDK about data...
}
