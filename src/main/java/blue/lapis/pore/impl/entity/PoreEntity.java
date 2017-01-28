/*
 * PoreRT - A Bukkit to Sponge Bridge
 *
 * Copyright (c) 2016-2017, Maxqia <https://github.com/Maxqia> AGPLv3
 * Copyright (c) 2014-2016, Lapis <https://github.com/LapisBlue> MIT
 * Copyright (c) Contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * An exception applies to this license, see the LICENSE file in the main directory for more information.
 */

package blue.lapis.pore.impl.entity;

import blue.lapis.pore.Pore;
import blue.lapis.pore.converter.vector.LocationConverter;
import blue.lapis.pore.converter.vector.VectorConverter;
import blue.lapis.pore.converter.wrapper.WrapperConverter;
import blue.lapis.pore.impl.PoreWorld;
import blue.lapis.pore.impl.command.PoreCommandSender;
import blue.lapis.pore.impl.metadata.PoreMetadataStore;
import blue.lapis.pore.util.PoreText;

import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.EntityEffect;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.metadata.MetadataStore;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.DataManipulator;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntitySnapshot;
import org.spongepowered.api.util.AABB;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

//TODO: Determine if metadata methods should be implemented manually
public class PoreEntity extends PoreCommandSender implements org.bukkit.entity.Entity {

    private static final MetadataStore<org.bukkit.entity.Entity> entityMeta =
            new PoreMetadataStore<org.bukkit.entity.Entity>();

    public static PoreEntity of(Entity handle) {
        return WrapperConverter.of(PoreEntity.class, handle);
    }

    public static PoreEntity of(EntitySnapshot snapshot) {
        Optional<UUID> uuid = snapshot.getUniqueId();
        Optional<Entity> entity = snapshot.getTransform().get().getExtent().getEntity(uuid.get());
        if (!entity.isPresent()) {
            return null;
        }
        return PoreEntity.of(entity.get());
    }

    protected PoreEntity(Entity handle) {
        super(handle);
    }

    @Override
    public Entity getHandle() {
        return (Entity) super.getHandle();
    }

    public net.minecraft.entity.Entity getMCHandle() {
        return (net.minecraft.entity.Entity) getHandle();
    }

    protected <T extends DataManipulator<T, ?>> boolean hasData(Class<T> key) {
        return getHandle().get(key).isPresent();
    }

    @Override
    public EntityType getType() {
        return EntityType.UNKNOWN;
    }

    @Override
    public Location getLocation() {
        return LocationConverter.fromTransform(getHandle().getTransform());
    }

    @Override
    public Location getLocation(Location loc) {
        return LocationConverter.apply(loc, getHandle().getTransform());
    }

    @Override
    public Vector getVelocity() {
        return getHandle().get(Keys.VELOCITY).map(VectorConverter::createBukkitVector)
                .orElseGet(() -> new Vector(0, 0, 0));
    }

    @Override
    public void setVelocity(Vector velocity) {
        getHandle().offer(Keys.VELOCITY, VectorConverter.create3d(velocity));
    }

    @Override
    public boolean isOnGround() {
        return getHandle().isOnGround();
    }

    @Override
    public World getWorld() {
        return PoreWorld.of(getHandle().getWorld());
    }

    @Override
    public boolean teleport(Location location) {
        return this.teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
    }

    @Override
    public boolean teleport(Location location, PlayerTeleportEvent.TeleportCause cause) {
        if (this.getPassenger() != null || this.isDead()) {
            return false;
        }
        if (this.eject()) {
            getHandle().setTransform(LocationConverter.toTransform(location));
            // CraftBukkit apparently does not throw an event when this method is called
            return true;
        }
        return false;
    }

    @Override
    public boolean teleport(org.bukkit.entity.Entity destination) {
        return this.teleport(destination.getLocation());
    }

    @Override
    public boolean teleport(org.bukkit.entity.Entity destination, PlayerTeleportEvent.TeleportCause cause) {
        return this.teleport(destination.getLocation(), cause);
    }

    @Override
    public List<org.bukkit.entity.Entity> getNearbyEntities(double x, double y, double z) {
        Entity handle = getHandle();
        AABB box = getHandle().getBoundingBox().get().expand(x, y, z);

        return getHandle().getWorld().getIntersectingEntities(box, entity -> !entity.equals(handle))
            .stream().map(entity -> PoreEntity.of(entity)).collect(Collectors.toList());
    }

    @Override
    public int getEntityId() { // note to self - this is the ID of the entity in the world, and unrelated to
        // its UUID
        return ((net.minecraft.entity.Entity) getHandle()).getEntityId();
    } // No function in Sponge

    @Override
    public int getFireTicks() {
        return getHandle().get(Keys.FIRE_TICKS).get();
    }

    @Override
    public void setFireTicks(int ticks) {
        getHandle().offer(Keys.FIRE_TICKS, ticks);
    }

    @Override
    public int getMaxFireTicks() {
        return getHandle().getValue(Keys.FIRE_TICKS).get().getMaxValue();
    }

    @Override
    public void remove() {
        getHandle().remove();
    }

    @Override
    public boolean isDead() {
        return getHandle().isRemoved();
    }

    @Override
    public boolean isValid() {
        return getHandle().isLoaded();
    }

    @Override
    public Server getServer() {
        return Pore.getServer();
    }

    @Override
    public org.bukkit.entity.Entity getPassenger() {
        Entity passenger = null;
        Optional<List<EntitySnapshot>> passengers = getHandle().get(Keys.PASSENGERS);
        if (passengers.isPresent()) {
            passengers.get().get(0);
        }
        return PoreEntity.of(passenger); // TODO : better sorting?
    }

    @Override
    public boolean setPassenger(final org.bukkit.entity.Entity passenger) {
        if (passenger != null) {
            return getHandle().get(Keys.PASSENGERS).get().add(((PoreEntity) passenger).getHandle().createSnapshot());
        } else {
            return getHandle().remove(Keys.PASSENGERS).isSuccessful();
        }
    }

    @Override
    public boolean isEmpty() {
        return !getHandle().get(Keys.PASSENGERS).isPresent();
    }

    @Override
    public boolean eject() {
        return setPassenger(null);
    }

    @Override
    public float getFallDistance() {
        return getHandle().get(Keys.FALL_DISTANCE).get();
    }

    @Override
    public void setFallDistance(float distance) {
        getHandle().offer(Keys.FALL_DISTANCE, distance);
    }

    @Override
    public void setLastDamageCause(EntityDamageEvent event) {
        //TODO: Sponge counterpart planned for 1.1
    }

    @Override
    public EntityDamageEvent getLastDamageCause() {
        // TODO: Sponge counterpart planned for 1.1
        return null;
    }

    @Override
    public UUID getUniqueId() {
        return getHandle().getUniqueId();
    }

    @Override
    public int getTicksLived() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public void setTicksLived(int value) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public void playEffect(EntityEffect type) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public boolean isInsideVehicle() {
        return getHandle().get(Keys.VEHICLE).isPresent();
    }

    @Override
    public boolean leaveVehicle() {
        return isInsideVehicle() && getHandle().remove(Keys.VEHICLE).isSuccessful();
    }

    @Override
    public org.bukkit.entity.Entity getVehicle() {
        return getHandle().get(Keys.VEHICLE).map(PoreEntity::of).orElse(null);
    }

    @Override
    public String getCustomName() {
        return getHandle().get(Keys.DISPLAY_NAME).map(PoreText::convert).orElse(null);
    }

    @Override
    public void setCustomName(String name) {
        getHandle().offer(Keys.DISPLAY_NAME, PoreText.convert(name));
    }

    @Override
    public boolean isCustomNameVisible() {
        Optional<Boolean> visible = getHandle().get(Keys.CUSTOM_NAME_VISIBLE);
        return visible.isPresent() ? visible.get() : false;
    }

    @Override
    public void setCustomNameVisible(boolean flag) {
        getHandle().offer(Keys.CUSTOM_NAME_VISIBLE, flag);
    }

    @Override
    public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {
        entityMeta.setMetadata(this, metadataKey, newMetadataValue);
    }

    @Override
    public List<MetadataValue> getMetadata(String metadataKey) {
        return entityMeta.getMetadata(this, metadataKey);
    }

    @Override
    public boolean hasMetadata(String metadataKey) {
        return entityMeta.hasMetadata(this, metadataKey);
    }

    @Override
    public void removeMetadata(String metadataKey, Plugin owningPlugin) {
        entityMeta.removeMetadata(this, metadataKey, owningPlugin);
    }

    @Override
    public void setGlowing(boolean flag) {
        getHandle().offer(Keys.GLOWING, flag);
    }

    @Override
    public boolean isGlowing() {
        return getHandle().get(Keys.GLOWING).orElse(false);
    }

    @Override
    public void setInvulnerable(boolean flag) {
        // TODO
        throw new NotImplementedException("TODO");
    }

    @Override
    public boolean isInvulnerable() {
        // TODO
        throw new NotImplementedException("TODO");
    }

    @Override
    public boolean isSilent() {
        return getHandle().get(Keys.IS_SILENT).orElse(false);
    }

    @Override
    public void setSilent(boolean flag) {
        getHandle().offer(Keys.IS_SILENT, flag);
    }

    @Override
    public boolean hasGravity() {
        return getHandle().get(Keys.HAS_GRAVITY).orElse(true);
    }

    @Override
    public void setGravity(boolean gravity) {
        getHandle().offer(Keys.HAS_GRAVITY, gravity);
    }

    @Override
    public int getPortalCooldown() { // TODO this might not be the right key
        return getHandle().get(Keys.COOLDOWN).orElse(0);
    }

    @Override
    public void setPortalCooldown(int cooldown) {
        getHandle().offer(Keys.COOLDOWN, cooldown);
    }

    @Override
    public Set<String> getScoreboardTags() {
        throw new NotImplementedException("Sponge API needs Merge");
    }

    @Override
    public boolean addScoreboardTag(String tag) {
        throw new NotImplementedException("Sponge API needs Merge");
    }

    @Override
    public boolean removeScoreboardTag(String tag) {
        throw new NotImplementedException("Sponge API needs Merge");
    }

    @Override
    public org.bukkit.entity.Entity.Spigot spigot() {
        throw new NotImplementedException("TODO");
    }
}
