/*
 * PoreRT - A Bukkit to Sponge Bridge
 *
 * Copyright (c) 2016, Maxqia <https://github.com/Maxqia> AGPLv3
 * Copyright (c) Spigot/Craftbukkit Project <https://hub.spigotmc.org/stash/projects/SPIGOT> LGPLv3
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

package org.spigotmc;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.io.File;
import java.util.List;
import java.util.Set;
import gnu.trove.map.hash.TObjectIntHashMap;
import org.bukkit.configuration.file.YamlConfiguration;
import org.spongepowered.common.SpongeImpl;

/**
 * Here for binary compatibility with Spigot.
 * Copied from Spigot, Stripping out the Private things
 * @author Maxqia
 */
public class SpigotConfig
{
    /*========================================================================*/
    public static YamlConfiguration config = new YamlConfiguration();
    /*========================================================================*/

    public static void init(File configFile){
    }

    public static void registerCommands(){
    }

    public static boolean logCommands = false; // true default
    public static int tabComplete = 0;

    public static String whitelistMessage = "You are not whitelisted on this server!";
    public static String unknownCommandMessage = "Unknown command. Type \"/help\" for help.";
    public static String serverFullMessage = "The server is full!";
    public static String outdatedClientMessage = "Outdated client! Please use {0}";
    public static String outdatedServerMessage = "Outdated server! I\'m still on {0}";

    public static int timeoutTime = 60;
    public static boolean restartOnCrash = true;
    public static String restartScript = "./start.sh";
    public static String restartMessage = "Server is restarting";

    public static boolean bungee;
    static {
        bungee = SpongeImpl.getGlobalConfig().getConfig().getBungeeCord().getIpForwarding();
    }

    public static boolean lateBind = false;

    public static boolean disableStatSaving = false;
    public static TObjectIntHashMap<String> forcedStats = new TObjectIntHashMap<String>();
    public static int playerSample = 12;

    public static int playerShuffle = 0;

    public static List<String> spamExclusions = Lists.newArrayList();

    public static boolean silentCommandBlocks = false;

    public static boolean filterCreativeItems = true;

    public static Set<String> replaceCommands = Sets.newHashSet();

    public static int userCacheCap = 1000;

    public static boolean saveUserCacheOnStopOnly = false;
    public static int intCacheLimit = 1024;

    public static double movedWronglyThreshold = 0.0625D;

    public static double movedTooQuicklyMultiplier = 10.0D;

    public static double maxHealth = 2048;
    public static double movementSpeed = 2048;
    public static double attackDamage = 2048;

    public static boolean debug = false;

    public static int itemDirtyTicks = 20;
}
