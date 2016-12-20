package blue.lapis.pore.impl.entity;

import blue.lapis.pore.converter.wrapper.WrapperConverter;

import org.bukkit.entity.EntityType;
import org.spongepowered.api.entity.living.monster.Vindicator;

public class PoreVindicator extends PoreMonster implements org.bukkit.entity.Vindicator {

    public static PoreVindicator of(Vindicator handle) {
        return WrapperConverter.of(PoreVindicator.class, handle);
    }

    protected PoreVindicator(Vindicator handle) {
        super(handle);
    }

    @Override
    public Vindicator getHandle() {
        return (Vindicator) super.getHandle();
    }

    @Override
    public EntityType getType() {
        return EntityType.VINDICATOR;
    }
}
