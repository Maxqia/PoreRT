/*
 * PoreRT - A Bukkit to Sponge Bridge
 *
 * Copyright (c) 2016, Maxqia <https://github.com/Maxqia> AGPLv3
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

package blue.lapis.pore.impl.block;

import blue.lapis.pore.converter.data.block.BlockDataConverter;
import blue.lapis.pore.converter.type.material.MaterialConverter;
import blue.lapis.pore.converter.vector.LocationConverter;
import blue.lapis.pore.converter.wrapper.WrapperConverter;
import blue.lapis.pore.impl.PoreWorld;
import blue.lapis.pore.util.PoreWrapper;

import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.material.MaterialData;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.tileentity.TileEntity;

import java.util.Collection;
import java.util.List;

public class PoreBlockState extends PoreWrapper<BlockSnapshot> implements org.bukkit.block.BlockState {

    private final TileEntity tileEntity;

    public static PoreBlockState of(BlockSnapshot handle) {
        return WrapperConverter.of(handle);
    }

    public static PoreBlockState of(TileEntity handle) {
        return WrapperConverter.of(handle);
    }

    protected PoreBlockState(BlockSnapshot handle) {
        super(handle);
        this.tileEntity = null;
    }

    protected PoreBlockState(TileEntity handle) {
        super(handle.getLocation().createSnapshot());
        this.tileEntity = handle;
    }

    TileEntity getTileEntity() {
        return tileEntity;
    }

    @Override
    public Block getBlock() {
        return PoreBlock.of(getHandle().getLocation().get());
    }

    @Override
    @SuppressWarnings("deprecation") //TODO: Find non-deprecated way to do this
    public MaterialData getData() {
        return new MaterialData(getBlock().getData());
    }

    @Override
    public Material getType() {
        return MaterialConverter.of(getHandle().getExtendedState().getType());
    }

    @Override
    @SuppressWarnings("deprecation")
    public int getTypeId() {
        return getType().getId();
    }

    @Override
    public byte getLightLevel() {
        //noinspection ConstantConditions
        return isPlaced() ? getBlock().getLightLevel() : null;
    }

    @Override
    public World getWorld() {
        return PoreWorld.of(getHandle().getLocation().get().getExtent());
    }

    @Override
    public int getX() {
        //noinspection ConstantConditions
        return isPlaced() ? getBlock().getX() : null;
    }

    @Override
    public int getY() {
        //noinspection ConstantConditions
        return isPlaced() ? getBlock().getY() : null;
    }

    @Override
    public int getZ() {
        //noinspection ConstantConditions
        return isPlaced() ? getBlock().getZ() : null;
    }

    @Override
    public Location getLocation() {
        return LocationConverter.of(getHandle().getLocation().get());
    }

    @Override
    public Location getLocation(Location loc) {
        return LocationConverter.apply(loc, getHandle().getLocation().get());
    }

    @Override
    public Chunk getChunk() {
        return isPlaced() ? getBlock().getChunk() : null;
    }

    @Override
    public void setData(MaterialData data) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public void setType(Material type) {
        //TODO: this isn't right
        if (isPlaced()) {
            getBlock().setType(type);
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean setTypeId(int type) {
        Material mat = Material.getMaterial(type);
        setType(mat);
        return getType() == mat;
    }

    @Override
    public boolean update() {
        return update(false);
    }

    @Override
    public boolean update(boolean force) {
        return update(force, true);
    }

    @Override
    public boolean update(boolean force, boolean applyPhysics) {
        return true; // TODO : actually implement
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public byte getRawData() { //TODO: fix rawtypes warning
        return BlockDataConverter.INSTANCE.getDataValue((Collection) getHandle().getManipulators(),
                getHandle().getExtendedState().getType());
    }

    @Override
    public void setRawData(byte data) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public boolean isPlaced() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public List<MetadataValue> getMetadata(String metadataKey) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public boolean hasMetadata(String metadataKey) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public void removeMetadata(String metadataKey, Plugin owningPlugin) {
        throw new NotImplementedException("TODO");
    }
}
