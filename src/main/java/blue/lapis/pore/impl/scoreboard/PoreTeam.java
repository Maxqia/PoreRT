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
import blue.lapis.pore.converter.type.scoreboard.NameTagVisibilityConverter;
import blue.lapis.pore.converter.type.scoreboard.OptionStatusConverter;
import blue.lapis.pore.converter.wrapper.WrapperConverter;
import blue.lapis.pore.util.PoreText;
import blue.lapis.pore.util.PoreWrapper;

import com.google.common.base.Preconditions;
import com.google.common.collect.Collections2;
import com.google.common.collect.Sets;

import org.bukkit.OfflinePlayer;
import org.bukkit.scoreboard.NameTagVisibility;
import org.bukkit.scoreboard.Scoreboard;
import org.spongepowered.api.scoreboard.Team;
import org.spongepowered.api.text.Text;

import java.util.Set;

@SuppressWarnings("deprecation")
public class PoreTeam extends PoreWrapper<Team> implements org.bukkit.scoreboard.Team {

    private static final int MAX_NAME_LENGTH = 32;

    public static PoreTeam of(Team handle) {
        return WrapperConverter.of(PoreTeam.class, handle);
    }

    protected PoreTeam(Team handle) {
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
        checkArgument(displayName.length() < MAX_NAME_LENGTH, // kind of unnecessary because Sponge does this for us (ik, it breaks my head too)
                "Display name must not be longer than " + MAX_NAME_LENGTH + " characters");
        getHandle().setDisplayName(PoreText.convert(displayName));
    }

    @Override
    public String getPrefix() throws IllegalStateException {
        checkState();
        return PoreText.convert(getHandle().getPrefix());
    }

    @Override
    public void setPrefix(String prefix) throws IllegalStateException, IllegalArgumentException {
        checkState();
        checkArgument(prefix != null, "Prefix must not be null");
        getHandle().setPrefix(PoreText.convert(prefix));
    }

    @Override
    public String getSuffix() throws IllegalStateException {
        checkState();
        return PoreText.convert(getHandle().getSuffix());
    }

    @Override
    public void setSuffix(String suffix) throws IllegalStateException, IllegalArgumentException {
        checkState();
        checkArgument(suffix != null, "Suffix must not be null");
        getHandle().setSuffix(PoreText.convert(suffix));
    }

    @Override
    public boolean allowFriendlyFire() throws IllegalStateException {
        checkState();
        return getHandle().allowFriendlyFire();
    }

    @Override
    public void setAllowFriendlyFire(boolean enabled) throws IllegalStateException {
        checkState();
        getHandle().setAllowFriendlyFire(enabled);
    }

    @Override
    public boolean canSeeFriendlyInvisibles() throws IllegalStateException {
        checkState();
        return getHandle().canSeeFriendlyInvisibles();
    }

    @Override
    public void setCanSeeFriendlyInvisibles(boolean enabled) throws IllegalStateException {
        checkState();
        getHandle().setCanSeeFriendlyInvisibles(enabled);
    }

    @Override
    public NameTagVisibility getNameTagVisibility() throws IllegalArgumentException {
        checkState(); // this is technically against documentation but the documentation is stupid for this method
        return NameTagVisibilityConverter.of(getHandle().getNameTagVisibility());
    }

    @Override
    public void setNameTagVisibility( NameTagVisibility visibility) throws IllegalArgumentException {
        checkState(); // same for this
        checkArgument(visibility != null, "Visibility cannot be null");
        getHandle().setNameTagVisibility(NameTagVisibilityConverter.of(visibility));
    }

    @Override
    public Set<OfflinePlayer> getPlayers() throws IllegalStateException {
        checkState();
        return Sets.newHashSet(Collections2.transform(getHandle().getMembers(),
                user -> Pore.getServer().getOfflinePlayer(PoreText.convert(user))
        ));
    }

    @Override
    public Set<String> getEntries() throws IllegalStateException {
        checkState();
        return Sets.newHashSet(Collections2.transform(getHandle().getMembers(), PoreText::convert));
    }

    @Override
    public int getSize() throws IllegalStateException {
        checkState();
        return getHandle().getMembers().size();
    }

    @Override
    public Scoreboard getScoreboard() {
        return getHandle().getScoreboard().isPresent() ? PoreScoreboard.of(getHandle().getScoreboard().get()) : null;
    }

    @Override
    public void addPlayer(OfflinePlayer player) throws IllegalStateException, IllegalArgumentException {
        checkArgument(player != null, "Player cannot be null");
        addEntry(player.getName());
    }

    @Override
    public void addEntry(String entry) throws IllegalStateException, IllegalArgumentException {
        checkArgument(entry != null, "Entry cannot be null");
        checkState();
        getHandle().addMember(PoreText.convert(entry));
    }

    @Override
    public boolean removePlayer(OfflinePlayer player) throws IllegalStateException, IllegalArgumentException {
        checkArgument(player != null, "Player cannot be null");
        //noinspection ConstantConditions
        return removeEntry(player.getName());
    }

    @Override
    public boolean removeEntry(String entry) throws IllegalStateException, IllegalArgumentException {
        checkState();
        checkArgument(entry != null, "Entry cannot be null");
        for (Text user : getHandle().getMembers()) {
            if (PoreText.convert(user).equals(entry)) {
                return getHandle().removeMember(user);
            }
        }
        return false;
    }

    @Override
    public void unregister() throws IllegalStateException {
        checkState();
        getHandle().unregister();
    }

    @Override
    public boolean hasPlayer(OfflinePlayer player) throws IllegalArgumentException, IllegalStateException {
        checkArgument(player != null, "Offline player cannot be null");
        //noinspection ConstantConditions
        return hasEntry(player.getName());
    }

    @Override
    public boolean hasEntry(String entry) throws IllegalArgumentException, IllegalStateException {
        checkState();
        checkArgument(entry != null, "Entry cannot be null");
        for (Text user : getHandle().getMembers()) {
            if (PoreText.convert(user).equals(entry)) {
                return true;
            }
        }
        return false;
    }

    private void checkState() throws IllegalStateException {
        Preconditions.checkState(!getHandle().getScoreboard().isPresent(), "Team has been unregistered");
    }

    @Override
    public OptionStatus getOption(Option option) throws IllegalStateException {
        switch(option) {
            case COLLISION_RULE:
                return OptionStatusConverter.of(getHandle().getCollisionRule());
            case DEATH_MESSAGE_VISIBILITY:
                return OptionStatusConverter.of(getHandle().getDeathMessageVisibility());
            case NAME_TAG_VISIBILITY:
                return OptionStatusConverter.of(getHandle().getNameTagVisibility());
            default:
                throw new IllegalStateException("Unknown Option");

        }
    }

    @Override
    public void setOption(Option option, OptionStatus status) throws IllegalStateException {
        switch(option) {
            case COLLISION_RULE:
                getHandle().setCollisionRule(OptionStatusConverter.ofCollision(status));
                break;
            case DEATH_MESSAGE_VISIBILITY:
                getHandle().setDeathMessageVisibility(OptionStatusConverter.ofVisibility(status));
                break;
            case NAME_TAG_VISIBILITY:
                getHandle().setNameTagVisibility(OptionStatusConverter.ofVisibility(status));
                break;
            default:
                throw new IllegalStateException("Unknown Option");

        }
    }

}
