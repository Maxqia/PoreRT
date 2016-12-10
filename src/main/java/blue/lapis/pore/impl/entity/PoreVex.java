package blue.lapis.pore.impl.entity;

import blue.lapis.pore.converter.wrapper.WrapperConverter;

import org.bukkit.entity.EntityType;
import org.spongepowered.api.entity.living.monster.Vex;

public class PoreVex extends PoreMonster implements org.bukkit.entity.Vex {

    public static PoreVex of(Vex handle) {
        return WrapperConverter.of(PoreVex.class, handle);
    }

    protected PoreVex(Vex handle) {
        super(handle);
    }

    @Override
    public Vex getHandle() {
        return (Vex) super.getHandle();
    }

    @Override
    public EntityType getType() {
        return EntityType.VEX;
    }

}
