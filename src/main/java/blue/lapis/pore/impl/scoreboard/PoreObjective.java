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

import blue.lapis.pore.converter.type.scoreboard.DisplaySlotConverter;
import blue.lapis.pore.converter.wrapper.WrapperConverter;
import blue.lapis.pore.util.PoreText;
import blue.lapis.pore.util.PoreWrapper;

import com.google.common.base.Preconditions;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ServerScoreboard;
import org.bukkit.OfflinePlayer;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Score;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.scoreboard.critieria.Criteria;
import org.spongepowered.api.scoreboard.objective.Objective;
import org.spongepowered.common.registry.type.scoreboard.DisplaySlotRegistryModule;
import org.spongepowered.common.scoreboard.SpongeObjective;

public class PoreObjective extends PoreWrapper<Objective> implements org.bukkit.scoreboard.Objective {

    public static PoreObjective of(Objective handle) {
        return WrapperConverter.of(PoreObjective.class, handle);
    }


    protected PoreObjective(Objective handle) {
        super(handle);
    }

    @Override
    public String getName() throws IllegalStateException {
        checkState();
        return getHandle().getName();
    }

    @Override
    public String getDisplayName() throws IllegalStateException {
        checkState();
        return PoreText.convert(getHandle().getDisplayName());
    }

    @Override
    public void setDisplayName(String displayName) throws IllegalStateException, IllegalArgumentException {
        checkState();
        checkArgument(displayName != null, "Display name must not be null");
        getHandle().setDisplayName(PoreText.convert(displayName));
    }

    @Override
    public String getCriteria() throws IllegalStateException {
        checkState();
        return getHandle().getCriterion().getName();
    }

    @Override
    public boolean isModifiable() throws IllegalStateException {
        checkState();
        // TODO: I might try to get a method into SpongeAPI, but this will do for now
        return getHandle().getCriterion() == Criteria.HEALTH;
    }

    @Override
    public org.bukkit.scoreboard.Scoreboard getScoreboard() {
        //TODO: eh, this might be screwy because Bukkit doesn't account for multiple parents
        return PoreScoreboard.of(
                (org.spongepowered.api.scoreboard.Scoreboard) getHandle().getScoreboards().toArray()[0]
        );
    }

    @Override
    public void unregister() throws IllegalStateException {
        checkState();
        for (org.spongepowered.api.scoreboard.Scoreboard sb : getHandle().getScoreboards()) {
            sb.removeObjective(getHandle());
        }
    }

    @Override
    public DisplaySlot getDisplaySlot() throws IllegalStateException {
        // I genuinely have no idea why this isn't implemented in SpongeAPI
        checkState();
        ServerScoreboard board = (ServerScoreboard) ((PoreScoreboard)this.getScoreboard()).getHandle();
        int slotIndex = board.getObjectiveDisplaySlotCount(((SpongeObjective) getHandle()).getObjectiveFor(board));
        return DisplaySlotConverter.of(DisplaySlotRegistryModule.getInstance().getForIndex(slotIndex).get());
    }

    @Override
    public void setDisplaySlot(DisplaySlot slot) throws IllegalStateException {
        // same goes for this one
        checkState();
        Scoreboard board = ((PoreScoreboard)this.getScoreboard()).getHandle();
        ServerScoreboard mcBoard = (ServerScoreboard) board;
        ScoreObjective objective = ((SpongeObjective) getHandle()).getObjectiveFor(mcBoard);

        int slotIndex = mcBoard.getObjectiveDisplaySlotCount(objective);
        mcBoard.setObjectiveInDisplaySlot(slotIndex, null);
        board.updateDisplaySlot(getHandle(), DisplaySlotConverter.of(slot));
    }

    @Override
    public Score getScore(OfflinePlayer player) throws IllegalArgumentException, IllegalStateException {
        checkState();
        checkArgument(player != null, "Offline player cannot be null");
        //noinspection ConstantConditions
        return getScore(player.getName());
    }

    @Override
    public Score getScore(String entry) throws IllegalArgumentException, IllegalStateException {
        checkState();
        checkArgument(entry != null, "Entry cannot be null");
        //noinspection ConstantConditions
        return PoreScore.of(getHandle().getOrCreateScore(PoreText.convert(entry)));
    }

    private void checkState() throws IllegalStateException {
        Preconditions.checkState(!getHandle().getScoreboards().isEmpty(), "Objective has been unregistered");
    }

}
