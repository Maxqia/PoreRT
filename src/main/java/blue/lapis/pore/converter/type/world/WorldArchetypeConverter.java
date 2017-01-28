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

package blue.lapis.pore.converter.type.world;

import blue.lapis.pore.converter.type.TypeConverter;

import com.google.common.base.Converter;
import org.bukkit.World;
import org.spongepowered.api.world.WorldArchetype;
import org.spongepowered.api.world.WorldArchetypes;

public class WorldArchetypeConverter {

    private WorldArchetypeConverter() {
    }

    private static final Converter<World.Environment, WorldArchetype> CONVERTER =
            TypeConverter.builder(World.Environment.class, WorldArchetype.class)
                    .add(World.Environment.NORMAL, WorldArchetypes.OVERWORLD)
                    .add(World.Environment.NETHER, WorldArchetypes.THE_NETHER)
                    .add(World.Environment.THE_END, WorldArchetypes.THE_END)
                    .build();

    public static WorldArchetype of(World.Environment worldType) {
        return CONVERTER.convert(worldType);
    }

    public static World.Environment of(WorldArchetype dimension) {
        return CONVERTER.reverse().convert(dimension);
    }
}
