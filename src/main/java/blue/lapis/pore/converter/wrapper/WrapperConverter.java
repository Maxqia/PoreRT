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

package blue.lapis.pore.converter.wrapper;

import blue.lapis.pore.impl.PoreChunk;
import blue.lapis.pore.impl.PoreOfflinePlayer;
import blue.lapis.pore.impl.PoreWorld;
import blue.lapis.pore.impl.block.PoreBanner;
import blue.lapis.pore.impl.block.PoreBeacon;
import blue.lapis.pore.impl.block.PoreBlock;
import blue.lapis.pore.impl.block.PoreBlockState;
import blue.lapis.pore.impl.block.PoreBrewingStand;
import blue.lapis.pore.impl.block.PoreChest;
import blue.lapis.pore.impl.block.PoreCommandBlock;
import blue.lapis.pore.impl.block.PoreCreatureSpawner;
import blue.lapis.pore.impl.block.PoreDispenser;
import blue.lapis.pore.impl.block.PoreDropper;
import blue.lapis.pore.impl.block.PoreFlowerPot;
import blue.lapis.pore.impl.block.PoreFurnace;
import blue.lapis.pore.impl.block.PoreHopper;
import blue.lapis.pore.impl.block.PoreJukebox;
import blue.lapis.pore.impl.block.PoreNoteBlock;
import blue.lapis.pore.impl.block.PoreSign;
import blue.lapis.pore.impl.block.PoreSkull;
import blue.lapis.pore.impl.command.PoreBlockCommandSender;
import blue.lapis.pore.impl.command.PoreCommandSender;
import blue.lapis.pore.impl.command.PoreConsoleCommandSender;
import blue.lapis.pore.impl.entity.PoreAbstractHorse;
import blue.lapis.pore.impl.entity.PoreAgeable;
import blue.lapis.pore.impl.entity.PoreAmbient;
import blue.lapis.pore.impl.entity.PoreAnimalTamer;
import blue.lapis.pore.impl.entity.PoreAnimals;
import blue.lapis.pore.impl.entity.PoreAreaEffectCloud;
import blue.lapis.pore.impl.entity.PoreArmorStand;
import blue.lapis.pore.impl.entity.PoreArrow;
import blue.lapis.pore.impl.entity.PoreBat;
import blue.lapis.pore.impl.entity.PoreBlaze;
import blue.lapis.pore.impl.entity.PoreBoat;
import blue.lapis.pore.impl.entity.PoreCaveSpider;
import blue.lapis.pore.impl.entity.PoreChicken;
import blue.lapis.pore.impl.entity.PoreComplexEntityPart;
import blue.lapis.pore.impl.entity.PoreComplexLivingEntity;
import blue.lapis.pore.impl.entity.PoreCow;
import blue.lapis.pore.impl.entity.PoreCreature;
import blue.lapis.pore.impl.entity.PoreCreeper;
import blue.lapis.pore.impl.entity.PoreDonkey;
import blue.lapis.pore.impl.entity.PoreDragonFireball;
import blue.lapis.pore.impl.entity.PoreEgg;
import blue.lapis.pore.impl.entity.PoreEnderCrystal;
import blue.lapis.pore.impl.entity.PoreEnderDragon;
import blue.lapis.pore.impl.entity.PoreEnderDragonPart;
import blue.lapis.pore.impl.entity.PoreEnderPearl;
import blue.lapis.pore.impl.entity.PoreEnderSignal;
import blue.lapis.pore.impl.entity.PoreEnderman;
import blue.lapis.pore.impl.entity.PoreEndermite;
import blue.lapis.pore.impl.entity.PoreEntity;
import blue.lapis.pore.impl.entity.PoreExperienceOrb;
import blue.lapis.pore.impl.entity.PoreFallingBlock;
import blue.lapis.pore.impl.entity.PoreFireball;
import blue.lapis.pore.impl.entity.PoreFirework;
import blue.lapis.pore.impl.entity.PoreFishHook;
import blue.lapis.pore.impl.entity.PoreFlying;
import blue.lapis.pore.impl.entity.PoreGhast;
import blue.lapis.pore.impl.entity.PoreGiant;
import blue.lapis.pore.impl.entity.PoreGolem;
import blue.lapis.pore.impl.entity.PoreGuardian;
import blue.lapis.pore.impl.entity.PoreHanging;
import blue.lapis.pore.impl.entity.PoreHorse;
import blue.lapis.pore.impl.entity.PoreHumanEntity;
import blue.lapis.pore.impl.entity.PoreHusk;
import blue.lapis.pore.impl.entity.PoreIronGolem;
import blue.lapis.pore.impl.entity.PoreItem;
import blue.lapis.pore.impl.entity.PoreItemFrame;
import blue.lapis.pore.impl.entity.PoreLargeFireball;
import blue.lapis.pore.impl.entity.PoreLeashHitch;
import blue.lapis.pore.impl.entity.PoreLightningStrike;
import blue.lapis.pore.impl.entity.PoreLivingEntity;
import blue.lapis.pore.impl.entity.PoreLlama;
import blue.lapis.pore.impl.entity.PoreLlamaSpit;
import blue.lapis.pore.impl.entity.PoreMagmaCube;
import blue.lapis.pore.impl.entity.PoreMonster;
import blue.lapis.pore.impl.entity.PoreMule;
import blue.lapis.pore.impl.entity.PoreMushroomCow;
import blue.lapis.pore.impl.entity.PoreOcelot;
import blue.lapis.pore.impl.entity.PorePainting;
import blue.lapis.pore.impl.entity.PorePig;
import blue.lapis.pore.impl.entity.PorePigZombie;
import blue.lapis.pore.impl.entity.PorePlayer;
import blue.lapis.pore.impl.entity.PoreProjectile;
import blue.lapis.pore.impl.entity.PoreRabbit;
import blue.lapis.pore.impl.entity.PoreSheep;
import blue.lapis.pore.impl.entity.PoreShulker;
import blue.lapis.pore.impl.entity.PoreShulkerBullet;
import blue.lapis.pore.impl.entity.PoreSilverfish;
import blue.lapis.pore.impl.entity.PoreSkeleton;
import blue.lapis.pore.impl.entity.PoreSkeletonHorse;
import blue.lapis.pore.impl.entity.PoreSlime;
import blue.lapis.pore.impl.entity.PoreSmallFireball;
import blue.lapis.pore.impl.entity.PoreSnowball;
import blue.lapis.pore.impl.entity.PoreSnowman;
import blue.lapis.pore.impl.entity.PoreSpider;
import blue.lapis.pore.impl.entity.PoreSquid;
import blue.lapis.pore.impl.entity.PoreStray;
import blue.lapis.pore.impl.entity.PoreTNTPrimed;
import blue.lapis.pore.impl.entity.PoreThrownExpBottle;
import blue.lapis.pore.impl.entity.PoreThrownPotion;
import blue.lapis.pore.impl.entity.PoreVillager;
import blue.lapis.pore.impl.entity.PoreWaterMob;
import blue.lapis.pore.impl.entity.PoreWeather;
import blue.lapis.pore.impl.entity.PoreWitch;
import blue.lapis.pore.impl.entity.PoreWither;
import blue.lapis.pore.impl.entity.PoreWitherSkeleton;
import blue.lapis.pore.impl.entity.PoreWitherSkull;
import blue.lapis.pore.impl.entity.PoreWolf;
import blue.lapis.pore.impl.entity.PoreZombie;
import blue.lapis.pore.impl.entity.PoreZombieHorse;
import blue.lapis.pore.impl.entity.PoreZombieVillager;
import blue.lapis.pore.impl.entity.minecart.PoreCommandMinecart;
import blue.lapis.pore.impl.entity.minecart.PoreExplosiveMinecart;
import blue.lapis.pore.impl.entity.minecart.PoreHopperMinecart;
import blue.lapis.pore.impl.entity.minecart.PoreMinecart;
import blue.lapis.pore.impl.entity.minecart.PorePoweredMinecart;
import blue.lapis.pore.impl.entity.minecart.PoreRideableMinecart;
import blue.lapis.pore.impl.entity.minecart.PoreSpawnerMinecart;
import blue.lapis.pore.impl.entity.minecart.PoreStorageMinecart;
import blue.lapis.pore.impl.inventory.PoreInventory;
import blue.lapis.pore.impl.inventory.PoreInventoryHolder;
import blue.lapis.pore.impl.inventory.PorePlayerInventory;
import blue.lapis.pore.impl.scoreboard.PoreScoreboard;
import blue.lapis.pore.impl.scoreboard.PoreScoreboardManager;
import blue.lapis.pore.impl.scoreboard.PoreTeam;
import blue.lapis.pore.impl.util.PoreCachedServerIcon;
import blue.lapis.pore.util.PoreWrapper;

import com.google.common.base.Function;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.tileentity.Banner;
import org.spongepowered.api.block.tileentity.CommandBlock;
import org.spongepowered.api.block.tileentity.FlowerPot;
import org.spongepowered.api.block.tileentity.Jukebox;
import org.spongepowered.api.block.tileentity.MobSpawner;
import org.spongepowered.api.block.tileentity.Note;
import org.spongepowered.api.block.tileentity.Sign;
import org.spongepowered.api.block.tileentity.Skull;
import org.spongepowered.api.block.tileentity.carrier.Beacon;
import org.spongepowered.api.block.tileentity.carrier.BrewingStand;
import org.spongepowered.api.block.tileentity.carrier.Chest;
import org.spongepowered.api.block.tileentity.carrier.Dispenser;
import org.spongepowered.api.block.tileentity.carrier.Dropper;
import org.spongepowered.api.block.tileentity.carrier.Furnace;
import org.spongepowered.api.block.tileentity.carrier.Hopper;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.source.CommandBlockSource;
import org.spongepowered.api.command.source.ConsoleSource;
import org.spongepowered.api.entity.AreaEffectCloud;
import org.spongepowered.api.entity.EnderCrystal;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.ExperienceOrb;
import org.spongepowered.api.entity.FallingBlock;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.entity.ShulkerBullet;
import org.spongepowered.api.entity.Tamer;
import org.spongepowered.api.entity.explosive.PrimedTNT;
import org.spongepowered.api.entity.hanging.Hanging;
import org.spongepowered.api.entity.hanging.ItemFrame;
import org.spongepowered.api.entity.hanging.LeashHitch;
import org.spongepowered.api.entity.hanging.Painting;
import org.spongepowered.api.entity.living.Aerial;
import org.spongepowered.api.entity.living.Ageable;
import org.spongepowered.api.entity.living.Agent;
import org.spongepowered.api.entity.living.Ambient;
import org.spongepowered.api.entity.living.Aquatic;
import org.spongepowered.api.entity.living.ArmorStand;
import org.spongepowered.api.entity.living.Bat;
import org.spongepowered.api.entity.living.Humanoid;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.Squid;
import org.spongepowered.api.entity.living.Villager;
import org.spongepowered.api.entity.living.animal.Animal;
import org.spongepowered.api.entity.living.animal.Chicken;
import org.spongepowered.api.entity.living.animal.Cow;
import org.spongepowered.api.entity.living.animal.Donkey;
import org.spongepowered.api.entity.living.animal.Horse;
import org.spongepowered.api.entity.living.animal.Llama;
import org.spongepowered.api.entity.living.animal.Mooshroom;
import org.spongepowered.api.entity.living.animal.Mule;
import org.spongepowered.api.entity.living.animal.Ocelot;
import org.spongepowered.api.entity.living.animal.Pig;
import org.spongepowered.api.entity.living.animal.Rabbit;
import org.spongepowered.api.entity.living.animal.RideableHorse;
import org.spongepowered.api.entity.living.animal.Sheep;
import org.spongepowered.api.entity.living.animal.SkeletonHorse;
import org.spongepowered.api.entity.living.animal.Wolf;
import org.spongepowered.api.entity.living.animal.ZombieHorse;
import org.spongepowered.api.entity.living.complex.ComplexLiving;
import org.spongepowered.api.entity.living.complex.ComplexLivingPart;
import org.spongepowered.api.entity.living.complex.EnderDragon;
import org.spongepowered.api.entity.living.complex.EnderDragonPart;
import org.spongepowered.api.entity.living.golem.Golem;
import org.spongepowered.api.entity.living.golem.IronGolem;
import org.spongepowered.api.entity.living.golem.Shulker;
import org.spongepowered.api.entity.living.golem.SnowGolem;
import org.spongepowered.api.entity.living.monster.Blaze;
import org.spongepowered.api.entity.living.monster.CaveSpider;
import org.spongepowered.api.entity.living.monster.Creeper;
import org.spongepowered.api.entity.living.monster.Enderman;
import org.spongepowered.api.entity.living.monster.Endermite;
import org.spongepowered.api.entity.living.monster.Ghast;
import org.spongepowered.api.entity.living.monster.Giant;
import org.spongepowered.api.entity.living.monster.Guardian;
import org.spongepowered.api.entity.living.monster.Husk;
import org.spongepowered.api.entity.living.monster.MagmaCube;
import org.spongepowered.api.entity.living.monster.Monster;
import org.spongepowered.api.entity.living.monster.Silverfish;
import org.spongepowered.api.entity.living.monster.Skeleton;
import org.spongepowered.api.entity.living.monster.Slime;
import org.spongepowered.api.entity.living.monster.Spider;
import org.spongepowered.api.entity.living.monster.Stray;
import org.spongepowered.api.entity.living.monster.Witch;
import org.spongepowered.api.entity.living.monster.Wither;
import org.spongepowered.api.entity.living.monster.WitherSkeleton;
import org.spongepowered.api.entity.living.monster.Zombie;
import org.spongepowered.api.entity.living.monster.ZombiePigman;
import org.spongepowered.api.entity.living.monster.ZombieVillager;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.entity.projectile.Egg;
import org.spongepowered.api.entity.projectile.EnderPearl;
import org.spongepowered.api.entity.projectile.EyeOfEnder;
import org.spongepowered.api.entity.projectile.Firework;
import org.spongepowered.api.entity.projectile.FishHook;
import org.spongepowered.api.entity.projectile.LlamaSpit;
import org.spongepowered.api.entity.projectile.Projectile;
import org.spongepowered.api.entity.projectile.Snowball;
import org.spongepowered.api.entity.projectile.ThrownExpBottle;
import org.spongepowered.api.entity.projectile.ThrownPotion;
import org.spongepowered.api.entity.projectile.arrow.Arrow;
import org.spongepowered.api.entity.projectile.explosive.DragonFireball;
import org.spongepowered.api.entity.projectile.explosive.WitherSkull;
import org.spongepowered.api.entity.projectile.explosive.fireball.Fireball;
import org.spongepowered.api.entity.projectile.explosive.fireball.LargeFireball;
import org.spongepowered.api.entity.projectile.explosive.fireball.SmallFireball;
import org.spongepowered.api.entity.vehicle.Boat;
import org.spongepowered.api.entity.vehicle.minecart.ChestMinecart;
import org.spongepowered.api.entity.vehicle.minecart.CommandBlockMinecart;
import org.spongepowered.api.entity.vehicle.minecart.FurnaceMinecart;
import org.spongepowered.api.entity.vehicle.minecart.HopperMinecart;
import org.spongepowered.api.entity.vehicle.minecart.Minecart;
import org.spongepowered.api.entity.vehicle.minecart.MobSpawnerMinecart;
import org.spongepowered.api.entity.vehicle.minecart.RideableMinecart;
import org.spongepowered.api.entity.vehicle.minecart.TNTMinecart;
import org.spongepowered.api.entity.weather.Lightning;
import org.spongepowered.api.entity.weather.WeatherEffect;
import org.spongepowered.api.item.inventory.Carrier;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.entity.PlayerInventory;
import org.spongepowered.api.network.status.Favicon;
import org.spongepowered.api.scoreboard.Scoreboard;
import org.spongepowered.api.scoreboard.Team;
import org.spongepowered.api.world.Chunk;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public final class WrapperConverter {

    private WrapperConverter() {
    }

    @SuppressWarnings("rawtypes")
    // Note : This evaluates in order
    static final CachedWrapperConverter<PoreWrapper> converter = CachedWrapperConverter.builder(PoreWrapper.class)
            // @formatter:off
            .register(Favicon.class, PoreCachedServerIcon.class)

            // Entities
            .register(Entity.class, PoreEntity.class)
                .register(AreaEffectCloud.class, PoreAreaEffectCloud.class)
                .register(Living.class, PoreLivingEntity.class)
                    .register(Ambient.class, PoreAmbient.class)
                        .register(Bat.class, PoreBat.class)
                    .register(Humanoid.class, PoreHumanEntity.class)
                        .register(Player.class, PorePlayer.class)
                    .register(ComplexLiving.class, PoreComplexLivingEntity.class)
                        .register(EnderDragon.class, PoreEnderDragon.class)

                    // Special registration needed here because it conflicts with PoreFlying
                    .register(Blaze.class, PoreBlaze.class)
                    .register(Wither.class, PoreWither.class)

                    .register(Aerial.class, PoreFlying.class)
                        .register(Ghast.class, PoreGhast.class)

                    .register(Slime.class, PoreSlime.class)
                        .register(MagmaCube.class, PoreMagmaCube.class)

                    .register(Agent.class, PoreCreature.class)
                        .register(Monster.class, PoreMonster.class)
                            .register(Creeper.class, PoreCreeper.class)
                            .register(Enderman.class, PoreEnderman.class)
                            .register(Endermite.class, PoreEndermite.class)
                            .register(Giant.class, PoreGiant.class)
                            .register(Guardian.class, PoreGuardian.class)
                            .register(Silverfish.class, PoreSilverfish.class)
                            .register(Skeleton.class, PoreSkeleton.class)
                                .register(WitherSkeleton.class, PoreWitherSkeleton.class)
                                .register(Stray.class, PoreStray.class)

                            .register(Spider.class, PoreSpider.class)
                                .register(CaveSpider.class, PoreCaveSpider.class)
                            .register(Witch.class, PoreWitch.class)

                            .register(Zombie.class, PoreZombie.class)
                                .register(ZombiePigman.class, PorePigZombie.class)
                                .register(Husk.class, PoreHusk.class)
                                .register(ZombieVillager.class, PoreZombieVillager.class)

                        .register(Aquatic.class, PoreWaterMob.class)
                            .register(Squid.class, PoreSquid.class)
                        .register(Ageable.class, PoreAgeable.class)
                            .register(Animal.class, PoreAnimals.class)
                                .register(Chicken.class, PoreChicken.class)
                                .register(Cow.class, PoreCow.class)
                                    .register(Mooshroom.class, PoreMushroomCow.class)
                                .register(Pig.class, PorePig.class)
                                .register(Rabbit.class, PoreRabbit.class)
                                .register(Sheep.class, PoreSheep.class)

                                //.register(Tameable.class, PoreTameable.class) TODO
                                    .register(Horse.class, PoreAbstractHorse.class)
                                        .register(RideableHorse.class, PoreHorse.class)
                                        //.register(sponge, PoreChestedHorse.class)
                                        .register(Donkey.class, PoreDonkey.class)
                                        .register(Llama.class, PoreLlama.class)
                                        .register(Mule.class, PoreMule.class)
                                        .register(SkeletonHorse.class, PoreSkeletonHorse.class)
                                        .register(ZombieHorse.class, PoreZombieHorse.class)
                                    .register(Ocelot.class, PoreOcelot.class)
                                    .register(Wolf.class, PoreWolf.class)

                            .register(Villager.class, PoreVillager.class)
                        .register(Golem.class, PoreGolem.class)
                            .register(IronGolem.class, PoreIronGolem.class)
                            .register(SnowGolem.class, PoreSnowman.class)
                            .register(Shulker.class, PoreShulker.class)
                    .register(ArmorStand.class, PoreArmorStand.class)
                .register(ComplexLivingPart.class, PoreComplexEntityPart.class)
                    .register(EnderDragonPart.class, PoreEnderDragonPart.class)
                .register(EnderCrystal.class, PoreEnderCrystal.class)
                .register(EyeOfEnder.class, PoreEnderSignal.class)
                .register(ExperienceOrb.class, PoreExperienceOrb.class)
                .register(FallingBlock.class, PoreFallingBlock.class)
                .register(Firework.class, PoreFirework.class)
                .register(Hanging.class, PoreHanging.class)
                    .register(ItemFrame.class, PoreItemFrame.class)
                    .register(LeashHitch.class, PoreLeashHitch.class)
                    .register(Painting.class, PorePainting.class)
                .register(Item.class, PoreItem.class)
                .register(Lightning.class, PoreLightningStrike.class)
                .register(Projectile.class, PoreProjectile.class)
                    .register(Arrow.class, PoreArrow.class)
                    .register(Egg.class, PoreEgg.class)
                    .register(EnderPearl.class, PoreEnderPearl.class)
                    .register(Fireball.class, PoreFireball.class)
                        .register(LargeFireball.class, PoreLargeFireball.class)
                        .register(SmallFireball.class, PoreSmallFireball.class)
                        .register(WitherSkull.class, PoreWitherSkull.class)
                        .register(DragonFireball.class, PoreDragonFireball.class)
                    .register(FishHook.class, PoreFishHook.class)
                    .register(Snowball.class, PoreSnowball.class)
                    .register(ThrownExpBottle.class, PoreThrownExpBottle.class)
                    .register(ThrownPotion.class, PoreThrownPotion.class)
                    .register(LlamaSpit.class, PoreLlamaSpit.class)
                    .register(ShulkerBullet.class, PoreShulkerBullet.class)

                .register(PrimedTNT.class, PoreTNTPrimed.class)
                .register(WeatherEffect.class, PoreWeather.class)
                .register(Minecart.class, PoreMinecart.class)
                    .register(ChestMinecart.class, PoreStorageMinecart.class)
                    .register(CommandBlockMinecart.class, PoreCommandMinecart.class)
                    .register(FurnaceMinecart.class, PorePoweredMinecart.class)
                    .register(HopperMinecart.class, PoreHopperMinecart.class)
                    .register(MobSpawnerMinecart.class, PoreSpawnerMinecart.class)
                    .register(RideableMinecart.class, PoreRideableMinecart.class)
                    .register(TNTMinecart.class, PoreExplosiveMinecart.class)
                .register(Boat.class, PoreBoat.class)

            // Inventory classes
            .register(PlayerInventory.class, PorePlayerInventory.class)
                .register(Inventory.class, PoreInventory.class)

            //.register(TileEntity.class, PoreTileEntity.class)
                .register(Banner.class, PoreBanner.class)
                .register(Beacon.class, PoreBeacon.class)
                .register(BrewingStand.class, PoreBrewingStand.class)
                .register(Chest.class, PoreChest.class)
                .register(CommandBlock.class, PoreCommandBlock.class)
                .register(MobSpawner.class, PoreCreatureSpawner.class)
                .register(Dispenser.class, PoreDispenser.class)
                .register(Dropper.class, PoreDropper.class)
                .register(Furnace.class, PoreFurnace.class)
                .register(Hopper.class, PoreHopper.class)
                .register(Jukebox.class, PoreJukebox.class)
                .register(Note.class, PoreNoteBlock.class)
                .register(Sign.class, PoreSign.class)
                .register(Skull.class, PoreSkull.class)
                .register(FlowerPot.class, PoreFlowerPot.class)
                .register(BlockSnapshot.class, PoreBlockState.class)

            .register(Chunk.class, PoreChunk.class)
            .register(User.class, PoreOfflinePlayer.class)
            .register(World.class, PoreWorld.class)

            .register(Tamer.class, PoreAnimalTamer.class)
            .register(CommandSource.class, PoreCommandSender.class)
                .register(CommandBlockSource.class, PoreBlockCommandSender.class)
                .register(ConsoleSource.class, PoreConsoleCommandSender.class)

            .register(Scoreboard.Builder.class, PoreScoreboardManager.class)
            .register(Scoreboard.class, PoreScoreboard.class)
            .register(Team.class, PoreTeam.class)

            .register(Carrier.class, PoreInventoryHolder.class)

            // Well, more or less...
            .register(Location.class, PoreBlock.class)

            // @formatter:on

            .build();

    public static <P extends PoreWrapper<?>> P of(Object handle) {
        return converter.get(handle);
    }

    public static <P extends PoreWrapper<?>> P of(Class<P> type, Object handle) {
        return converter.get(type, handle);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" }) //TODO fix this
    public static <S, P extends PoreWrapper<?>> Function<S, P> getConverter() {
        return (Function) converter;
    }
}
