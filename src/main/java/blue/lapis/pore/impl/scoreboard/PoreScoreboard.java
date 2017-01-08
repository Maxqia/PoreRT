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

package blue.lapis.pore.impl.scoreboard;

import static com.google.common.base.Preconditions.checkArgument;

import blue.lapis.pore.Pore;
import blue.lapis.pore.converter.type.scoreboard.DisplaySlotConverter;
import blue.lapis.pore.converter.wrapper.WrapperConverter;
import blue.lapis.pore.util.PoreText;
import blue.lapis.pore.util.PoreWrapper;

import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Team;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.scoreboard.critieria.Criterion;
import org.spongepowered.api.scoreboard.objective.Objective;
import org.spongepowered.api.text.Text;

import java.util.Optional;
import java.util.Set;

public class PoreScoreboard extends PoreWrapper<Scoreboard> implements org.bukkit.scoreboard.Scoreboard {

    public static PoreScoreboard of(Scoreboard handle) {
        return WrapperConverter.of(PoreScoreboard.class, handle);
    }

    protected PoreScoreboard(Scoreboard handle) {
        super(handle);
    }

    @Override
    public org.bukkit.scoreboard.Objective registerNewObjective(String name, String criteria)
            throws IllegalArgumentException {
        checkArgument(name != null, "Name must not be null");
        checkArgument(criteria != null, "Criteria must not be null");
        Objective.Builder builder = Objective.builder();
        //noinspection ConstantConditions
        builder.name(name);
        builder.displayName(PoreText.convert(name)); // TODO remove when Sponge bug is fixed

        Optional<Criterion> criterion = Pore.getGame().getRegistry().getType(Criterion.class, criteria);
        checkArgument(criterion.isPresent(), "Invalid Criteria");
        builder.criterion(criterion.get());


        Objective finalObjective = builder.build();
        getHandle().addObjective(finalObjective); // throws IllegalArgumentException for duplicate name
        return PoreObjective.of(finalObjective);
    }

    @Override
    public org.bukkit.scoreboard.Objective getObjective(String name) throws IllegalArgumentException {
        checkArgument(name != null, "Name must not be null");
        return PoreObjective.of(getHandle().getObjective(name).orElse(null));
    }

    @Override
    public Set<org.bukkit.scoreboard.Objective> getObjectivesByCriteria(String criteria)
            throws IllegalArgumentException {
        checkArgument(criteria != null, "Criterion must not be null");
        Optional<Criterion> c = Pore.getGame().getRegistry().getType(Criterion.class, criteria);
        checkArgument(c.isPresent(), "Invalid criterion");
        return Sets.newHashSet(Collections2.transform(getHandle().getObjectivesByCriteria(c.get()),
                PoreObjective::of
        ));
    }

    @Override
    public Set<org.bukkit.scoreboard.Objective> getObjectives() {
        return Sets.newHashSet(Collections2.transform(getHandle().getObjectives(), PoreObjective::of));
    }

    @Override
    public org.bukkit.scoreboard.Objective getObjective(DisplaySlot slot) throws IllegalArgumentException {
        checkArgument(slot != null, "Display slot must not be null");
        return PoreObjective.of(getHandle().getObjective(DisplaySlotConverter.of(slot)).orElse(null));
    }

    @Override
    public Set<Score> getScores(OfflinePlayer player) throws IllegalArgumentException {
        checkArgument(player != null, "Offline player must not be null");
        return getScores(player.getName());
    }

    @Override
    public Set<Score> getScores(String entry) throws IllegalArgumentException {
        checkArgument(entry != null, "Entry must not be null");
        return Sets.newHashSet(Collections2.transform(
                getHandle().getScores(PoreText.convert(entry)),
                PoreScore::of
        ));
    }

    @Override
    public void resetScores(OfflinePlayer player) throws IllegalArgumentException {
        checkArgument(player != null, "Offline player must not be null");
        resetScores(player.getName());
    }

    @Override
    public void resetScores(String entry) throws IllegalArgumentException {
        checkArgument(entry != null, "Entry must not be null");
        getHandle().removeScores(PoreText.convert(entry));
    }

    @Override
    public Team getPlayerTeam(OfflinePlayer player) throws IllegalArgumentException {
        checkArgument(player != null, "Offline player must not be null");
        return PoreTeam.of(getHandle().getMemberTeam(Text.of(player.getName())).orElse(null));
    }

    @Override
    public Team getEntryTeam(String entry) throws IllegalArgumentException {
        for (org.spongepowered.api.scoreboard.Team team : getHandle().getTeams()) {
            for (Text text : team.getMembers()) {
                if (PoreText.convert(text).equals(entry)) {
                    return PoreTeam.of(team);
                }
            }
        }
        return null;
    }

    @Override
    public Team getTeam(String teamName) throws IllegalArgumentException {
        checkArgument(teamName != null, "Team name must not be null");
        return PoreTeam.of(getHandle().getTeam(teamName).orElse(null));
    }

    @Override
    public Set<Team> getTeams() {
        return Sets.newHashSet(Collections2.transform(getHandle().getTeams(),
                PoreTeam::of
        ));
    }

    @Override
    public Team registerNewTeam(String name) throws IllegalArgumentException {
        checkArgument(name != null, "Team name must not be null");
        org.spongepowered.api.scoreboard.Team.Builder builder = org.spongepowered.api.scoreboard.Team.builder();
        builder.name(name);
        return PoreTeam.of(builder.build());
    }

    @Override
    @SuppressWarnings("deprecation")
    public Set<OfflinePlayer> getPlayers() {
        return Sets.newHashSet(Collections2.transform(getEntries(),
                Bukkit::getOfflinePlayer
        ));
    }

    @Override
    public Set<String> getEntries() { // dunno what entries are ...
        throw new NotImplementedException("TODO");
    }

    @Override
    public void clearSlot(DisplaySlot slot) throws IllegalArgumentException {
        getHandle().clearSlot(DisplaySlotConverter.of(slot));
    }

}
