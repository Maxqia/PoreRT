package blue.lapis.pore.impl.entity;

import blue.lapis.pore.converter.wrapper.WrapperConverter;

import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.spongepowered.api.entity.AreaEffectCloud;

import java.util.List;

public class PoreAreaEffectCloud extends PoreEntity implements org.bukkit.entity.AreaEffectCloud {

    public static PoreAreaEffectCloud of(AreaEffectCloud handle) {
        return WrapperConverter.of(PoreAreaEffectCloud.class, handle);
    }

    protected PoreAreaEffectCloud(AreaEffectCloud handle) {
        super(handle);
    }

    @Override
    public AreaEffectCloud getHandle() {
        return (AreaEffectCloud) super.getHandle();
    }

    @Override
    public EntityType getType() {
        return EntityType.AREA_EFFECT_CLOUD;
    }

    @Override
    public int getDuration() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public void setDuration(int duration) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public int getWaitTime() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public void setWaitTime(int waitTime) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public int getReapplicationDelay() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public void setReapplicationDelay(int delay) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public int getDurationOnUse() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public void setDurationOnUse(int duration) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public float getRadius() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public void setRadius(float radius) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public float getRadiusOnUse() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public void setRadiusOnUse(float radius) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public float getRadiusPerTick() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public void setRadiusPerTick(float radius) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public Particle getParticle() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public void setParticle(Particle particle) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public void setBasePotionData(PotionData data) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public PotionData getBasePotionData() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public boolean hasCustomEffects() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public List<PotionEffect> getCustomEffects() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public boolean addCustomEffect(PotionEffect effect, boolean overwrite) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public boolean removeCustomEffect(PotionEffectType type) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public boolean hasCustomEffect(PotionEffectType type) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public void clearCustomEffects() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public Color getColor() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public void setColor(Color color) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public ProjectileSource getSource() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public void setSource(ProjectileSource source) {
        throw new NotImplementedException("TODO");
    }

}
