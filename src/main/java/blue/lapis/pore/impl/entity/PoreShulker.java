package blue.lapis.pore.impl.entity;

import blue.lapis.pore.converter.wrapper.WrapperConverter;

import org.bukkit.entity.EntityType;
import org.spongepowered.api.entity.living.golem.Shulker;

public class PoreShulker extends PoreGolem implements org.bukkit.entity.Shulker {

    public static PoreShulker of(Shulker handle) {
        return WrapperConverter.of(PoreShulker.class, handle);
    }

    protected PoreShulker(Shulker handle) {
        super(handle);
    }

    @Override
    public Shulker getHandle() {
        return (Shulker) super.getHandle();
    }

    @Override
    public EntityType getType() {
        return EntityType.SHULKER;
    }

}
