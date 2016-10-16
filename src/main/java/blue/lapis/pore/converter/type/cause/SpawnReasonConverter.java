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
