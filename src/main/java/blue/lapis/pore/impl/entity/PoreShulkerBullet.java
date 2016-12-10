package blue.lapis.pore.impl.entity;

import blue.lapis.pore.converter.wrapper.WrapperConverter;

import org.apache.commons.lang.NotImplementedException;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.spongepowered.api.entity.ShulkerBullet;
import org.spongepowered.api.entity.projectile.Projectile;

public class PoreShulkerBullet extends PoreProjectile implements org.bukkit.entity.ShulkerBullet {

    public static PoreShulkerBullet of(ShulkerBullet handle) {
        return WrapperConverter.of(PoreShulkerBullet.class, handle);
    }

    protected PoreShulkerBullet(ShulkerBullet handle) {
        super((Projectile)handle);
    }

    /*@Override
    public ShulkerBullet getHandle() {
        return (ShulkerBullet) super.getHandle();
    }*/

    @Override
    public EntityType getType() {
        return EntityType.SHULKER_BULLET;
    }

    @Override
    public Entity getTarget() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public void setTarget(Entity target) {
        throw new NotImplementedException("TODO");
    }

}
