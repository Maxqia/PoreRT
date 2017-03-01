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

package blue.lapis.pore.converter.type.statistic;

import blue.lapis.pore.converter.type.TypeConverter;

import com.google.common.base.Converter;
import org.bukkit.Statistic;
import org.spongepowered.api.statistic.Statistics;

public final class StatisticConverter {

    private StatisticConverter() {
    }

    public static final Converter<Statistic, org.spongepowered.api.statistic.Statistic> STD_CONVERTER =
            TypeConverter.builder(Statistic.class, org.spongepowered.api.statistic.Statistic.class)
                    .add(Statistic.ANIMALS_BRED, Statistics.ANIMALS_BRED)
                    .add(Statistic.ARMOR_CLEANED, Statistics.ARMOR_CLEANED)
                    .add(Statistic.BANNER_CLEANED, Statistics.BANNER_CLEANED)
                    .add(Statistic.BEACON_INTERACTION, Statistics.BEACON_INTERACTION)
                    .add(Statistic.BOAT_ONE_CM, Statistics.BOAT_ONE_CM)
                    .add(Statistic.BREWINGSTAND_INTERACTION, Statistics.BREWINGSTAND_INTERACTION)
                    .add(Statistic.CAKE_SLICES_EATEN, Statistics.CAKE_SLICES_EATEN)
                    .add(Statistic.CAULDRON_FILLED, Statistics.CAULDRON_FILLED)
                    .add(Statistic.CAULDRON_USED, Statistics.CAULDRON_USED)
                    .add(Statistic.CHEST_OPENED, Statistics.CHEST_OPENED)
                    .add(Statistic.CLIMB_ONE_CM, Statistics.CLIMB_ONE_CM)
                    .add(Statistic.CRAFTING_TABLE_INTERACTION, Statistics.CRAFTING_TABLE_INTERACTION)
                    .add(Statistic.CROUCH_ONE_CM, Statistics.CROUCH_ONE_CM)
                    .add(Statistic.DAMAGE_DEALT, Statistics.DAMAGE_DEALT)
                    .add(Statistic.DAMAGE_TAKEN, Statistics.DAMAGE_TAKEN)
                    .add(Statistic.DEATHS, Statistics.DEATHS)
                    .add(Statistic.DISPENSER_INSPECTED, Statistics.DISPENSER_INSPECTED)
                    .add(Statistic.DIVE_ONE_CM, Statistics.DIVE_ONE_CM)
                    .add(Statistic.DROP, Statistics.DROP)
                    .add(Statistic.DROPPER_INSPECTED, Statistics.DROPPER_INSPECTED)
                    .add(Statistic.ENDERCHEST_OPENED, Statistics.ENDERCHEST_OPENED)
                    .add(Statistic.FALL_ONE_CM, Statistics.FALL_ONE_CM)
                    .add(Statistic.FISH_CAUGHT, Statistics.FISH_CAUGHT)
                    .add(Statistic.FLOWER_POTTED, Statistics.FLOWER_POTTED)
                    //.add(Statistic.FLY_ONE_CM, Statistics.FLY_ONE_CM) //TODO why was this removed?
                    .add(Statistic.FURNACE_INTERACTION, Statistics.FURNACE_INTERACTION)
                    .add(Statistic.HOPPER_INSPECTED, Statistics.HOPPER_INSPECTED)
                    .add(Statistic.HORSE_ONE_CM, Statistics.HORSE_ONE_CM)
                    .add(Statistic.ITEM_ENCHANTED, Statistics.ITEM_ENCHANTED)
                    .add(Statistic.JUMP, Statistics.JUMP)
                    //.add(Statistic.JUNK_FISHED, Statistics.JUNK_FISHED) //Removed in 1.11.1
                    .add(Statistic.LEAVE_GAME, Statistics.LEAVE_GAME)
                    .add(Statistic.MINECART_ONE_CM, Statistics.MINECART_ONE_CM)
                    .add(Statistic.MOB_KILLS, Statistics.MOB_KILLS)
                    .add(Statistic.NOTEBLOCK_PLAYED, Statistics.NOTEBLOCK_PLAYED)
                    .add(Statistic.NOTEBLOCK_TUNED, Statistics.NOTEBLOCK_TUNED)
                    .add(Statistic.PIG_ONE_CM, Statistics.PIG_ONE_CM)
                    .add(Statistic.PLAYER_KILLS, Statistics.PLAYER_KILLS)
                    .add(Statistic.PLAY_ONE_TICK, Statistics.TIME_PLAYED)
                    .add(Statistic.RECORD_PLAYED, Statistics.RECORD_PLAYED)
                    .add(Statistic.SPRINT_ONE_CM, Statistics.SPRINT_ONE_CM)
                    .add(Statistic.SWIM_ONE_CM, Statistics.SWIM_ONE_CM)
                    .add(Statistic.TALKED_TO_VILLAGER, Statistics.TALKED_TO_VILLAGER)
                    .add(Statistic.TIME_SINCE_DEATH, Statistics.TIME_SINCE_DEATH)
                    .add(Statistic.TRADED_WITH_VILLAGER, Statistics.TRADED_WITH_VILLAGER)
                    .add(Statistic.TRAPPED_CHEST_TRIGGERED, Statistics.TRAPPED_CHEST_TRIGGERED)
                    //.add(Statistic.TREASURE_FISHED, Statistics.TREASURE_FISHED) //Removed in 1.11.1
                    .add(Statistic.WALK_ONE_CM, Statistics.WALK_ONE_CM)
                    .build();

    /*public static final Converter<Statistic, org.spongepowered.api.statistic.StatisticGroup> GROUP_CONVERTER =
            TypeConverter.builder(Statistic.class, org.spongepowered.api.statistic.StatisticGroup.class)
                    .add(Statistic.BREAK_ITEM, StatisticGroups.BREAK_ITEM)
                    .add(Statistic.CRAFT_ITEM, StatisticGroups.CRAFT_ITEM)
                    .add(Statistic.ENTITY_KILLED_BY, StatisticGroups.KILLED_BY_ENTITY)
                    .add(Statistic.KILL_ENTITY, StatisticGroups.HAS_KILLED_ENTITY)
                    .add(Statistic.MINE_BLOCK, StatisticGroups.MINE_BLOCK)
                    .add(Statistic.USE_ITEM, StatisticGroups.USE_ITEM)
                    .build();*/


    public static org.spongepowered.api.statistic.Statistic of(Statistic statistic) {
        return STD_CONVERTER.convert(statistic);
    }

    /*public static org.spongepowered.api.statistic.StatisticGroup asGroupStat(Statistic statistic) {
        return GROUP_CONVERTER.convert(statistic);
    }*/

    public static Statistic of(org.spongepowered.api.statistic.Statistic statistic) {
        return STD_CONVERTER.reverse().convert(statistic);
    }

    /*public static Statistic of(org.spongepowered.api.statistic.StatisticGroup statistic) {
        return GROUP_CONVERTER.reverse().convert(statistic);
    }*/
}
