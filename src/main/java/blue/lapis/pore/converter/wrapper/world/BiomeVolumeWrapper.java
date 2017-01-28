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

package blue.lapis.pore.converter.wrapper.world;

import blue.lapis.pore.converter.type.world.BiomeConverter;
import blue.lapis.pore.converter.wrapper.WrapperConverter;
import blue.lapis.pore.util.PoreWrapper;

import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;
import org.spongepowered.api.world.extent.MutableBiomeVolume;
import org.spongepowered.api.world.extent.MutableBlockVolume;

public class BiomeVolumeWrapper extends PoreWrapper<MutableBiomeVolume> implements ChunkGenerator.BiomeGrid {

    public static BiomeVolumeWrapper of(MutableBlockVolume handle) {
        return WrapperConverter.of(BiomeVolumeWrapper.class, handle);
    }

    protected BiomeVolumeWrapper(MutableBiomeVolume handle) {
        super(handle);
    }

    @Override
    public Biome getBiome(int x, int z) {
        return BiomeConverter.of(getHandle().getBiome(x, 0, z));
    }

    @Override
    public void setBiome(int x, int z, Biome bio) {
        getHandle().setBiome(x, 0, z, BiomeConverter.of(bio));
    }

}
