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

package blue.lapis.pore.converter.type.scoreboard;

import blue.lapis.pore.converter.type.TypeConverter;

import com.google.common.base.Converter;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Team.OptionStatus;
import org.spongepowered.api.scoreboard.CollisionRule;
import org.spongepowered.api.scoreboard.CollisionRules;
import org.spongepowered.api.scoreboard.Visibilities;
import org.spongepowered.api.scoreboard.Visibility;

public final class OptionStatusConverter {

    private OptionStatusConverter() {
    }

    private static final Converter<Team.OptionStatus, CollisionRule> COLLISION =
            TypeConverter.builder(Team.OptionStatus.class, CollisionRule.class)
                    .add(OptionStatus.ALWAYS, CollisionRules.ALWAYS)
                    .add(OptionStatus.NEVER, CollisionRules.NEVER)
                    .add(OptionStatus.FOR_OTHER_TEAMS, CollisionRules.PUSH_OTHER_TEAMS)
                    .add(OptionStatus.FOR_OWN_TEAM, CollisionRules.PUSH_OWN_TEAM)
                    .build();

    private static final Converter<Team.OptionStatus, Visibility> VISIBILITY =
            TypeConverter.builder(Team.OptionStatus.class, Visibility.class)
                    .add(OptionStatus.ALWAYS, Visibilities.ALWAYS)
                    .add(OptionStatus.NEVER, Visibilities.NEVER)
                    .add(OptionStatus.FOR_OTHER_TEAMS, Visibilities.HIDE_FOR_OTHER_TEAMS)
                    .add(OptionStatus.FOR_OWN_TEAM, Visibilities.HIDE_FOR_OWN_TEAM)
                    .build();

    public static Team.OptionStatus of(Visibility visiblity) {
        return VISIBILITY.reverse().convert(visiblity);
    }

    public static Team.OptionStatus of(CollisionRule collision) {
        return COLLISION.reverse().convert(collision);
    }

    public static Visibility ofVisibility(Team.OptionStatus option) {
        return VISIBILITY.convert(option);
    }

    public static CollisionRule ofCollision(Team.OptionStatus option) {
        return COLLISION.convert(option);
    }
}
