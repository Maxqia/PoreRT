package blue.lapis.pore.impl.entity;

import blue.lapis.pore.converter.wrapper.WrapperConverter;

import org.spongepowered.api.entity.projectile.explosive.DragonFireball;

public class PoreDragonFireball extends PoreFireball implements org.bukkit.entity.DragonFireball {

    public static PoreDragonFireball of(DragonFireball handle) {
        return WrapperConverter.of(PoreDragonFireball.class, handle);
    }

    protected PoreDragonFireball(DragonFireball handle) {
        super(handle);
    }

}
