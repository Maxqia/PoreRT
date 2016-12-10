package blue.lapis.pore.impl.entity;

import blue.lapis.pore.converter.wrapper.WrapperConverter;

import org.apache.commons.lang.NotImplementedException;
import org.bukkit.entity.EntityType;
import org.spongepowered.api.entity.living.monster.Evoker;

public class PoreEvoker extends PoreMonster implements org.bukkit.entity.Evoker {

    public static PoreEvoker of(Evoker handle) {
        return WrapperConverter.of(PoreEvoker.class, handle);
    }

    protected PoreEvoker(Evoker handle) {
        super(handle);
    }

    @Override
    public Evoker getHandle() {
        return (Evoker) super.getHandle();
    }

    @Override
    public EntityType getType() {
        return EntityType.EVOKER;
    }

    @Override
    public Spell getCurrentSpell() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public void setCurrentSpell(Spell spell) {
        throw new NotImplementedException("TODO");
    }

}
