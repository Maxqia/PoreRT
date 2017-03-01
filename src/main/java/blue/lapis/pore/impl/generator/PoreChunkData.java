/*
 * PoreRT - A Bukkit to Sponge Bridge
 *
 * Copyright (c) 2016-2017, Maxqia <https://github.com/Maxqia> AGPLv3
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

package blue.lapis.pore.impl.generator;

import blue.lapis.pore.Pore;
import blue.lapis.pore.converter.type.material.MaterialConverter;
import blue.lapis.pore.converter.wrapper.WrapperConverter;
import blue.lapis.pore.util.PoreWrapper;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.ChunkData;
import org.bukkit.material.MaterialData;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.world.extent.MutableBlockVolume;

public class PoreChunkData extends PoreWrapper<MutableBlockVolume> implements ChunkData {

    public static PoreChunkData of(MutableBlockVolume handle) {
        return WrapperConverter.of(PoreChunkData.class, handle);
    }

    protected PoreChunkData(MutableBlockVolume handle) {
        super(handle);
    }

    @Override
    public int getMaxHeight() {
        return getHandle().getBlockMax().getY();
    }

    @Override
    @Deprecated
    public void setBlock(int x, int y, int z, int blockId) {
        this.setBlock(x, y, z, Material.getMaterial(blockId));
    }

    @Override
    @Deprecated
    public void setBlock(int x, int y, int z, int blockId, byte data) {
        this.setBlock(x, y, z, new MaterialData(blockId, data));
    }

    @Override
    public void setBlock(int x, int y, int z, Material material) {
        this.setBlock(x, y, z, new MaterialData(material));
    }

    @Override
    public void setBlock(int x, int y, int z, MaterialData material) { //TODO data
        getHandle().setBlock(x, y, z, BlockState.builder()
                .blockType(MaterialConverter.asBlock(material.getItemType())).build(),
                Cause.of(NamedCause.of(NamedCause.SOURCE, Pore.getPlugin())));
    }

    @Override
    @Deprecated
    public void setRegion(int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, int blockId) {
        this.setRegion(xMin, yMin, zMin, xMax, yMax, zMax, Material.getMaterial(blockId));
    }

    @Override
    @Deprecated
    public void setRegion(int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, int blockId, int data) {
        this.setRegion(xMin, yMin, zMin, xMax, yMax, zMax, new MaterialData(blockId, (byte) data));
    }

    @Override
    public void setRegion(int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, Material material) {
        this.setRegion(xMin, yMin, zMin, xMax, yMax, zMax, new MaterialData(material));
    }

    @Override
    public void setRegion(int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, MaterialData material) { //TODO data
        BlockState state = BlockState.builder().blockType(MaterialConverter.asBlock(material.getItemType())).build();
        Cause cause = Cause.of(NamedCause.of(NamedCause.SOURCE, Pore.getPlugin()));
        for (int x = xMin; x <= xMax; x++)
            for (int y = yMin; y <= yMax; y++)
                for (int z = zMin; z <= zMax; z++) {
                   getHandle().setBlock(x, y, z, state, cause);
        }
    }

    @Override
    public Material getType(int x, int y, int z) {
        return MaterialConverter.of(getHandle().getBlock(x, y, z).getType());
    }

    @Override
    public MaterialData getTypeAndData(int x, int y, int z) { // TODO data
        return new MaterialData(getType(x,y,z));
    }

    @Override
    @Deprecated
    public int getTypeId(int x, int y, int z) {
        return MaterialConverter.of(getHandle().getBlock(x, y, z).getType()).getId();
    }

    @Override
    public byte getData(int x, int y, int z) { // TODO data
        return 0;
    }

}
