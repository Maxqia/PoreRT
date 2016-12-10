package blue.lapis.pore.impl.entity;

import blue.lapis.pore.converter.wrapper.WrapperConverter;

import org.bukkit.entity.EntityType;
import org.spongepowered.api.entity.living.animal.PolarBear;

public class PorePolarBear extends PoreAnimals implements org.bukkit.entity.PolarBear {

    public static PorePolarBear of(PolarBear handle) {
        return WrapperConverter.of(PorePolarBear.class, handle);
    }

    protected PorePolarBear(PolarBear handle) {
        super(handle);
    }

    @Override
    public PolarBear getHandle() {
        return (PolarBear) super.getHandle();
    }

    @Override
    public EntityType getType() {
        return EntityType.POLAR_BEAR;
    }

}
