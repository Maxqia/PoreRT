/*
 * PoreRT - A Bukkit to Sponge Bridge
 *
 * Copyright (c) 2016, Maxqia <https://github.com/Maxqia> AGPLv3
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

package blue.lapis.pore.mixin.network;

import blue.lapis.pore.impl.entity.PorePlayer;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayerAbilities;
import net.minecraft.network.play.client.CPacketEntityAction.Action;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerToggleFlightEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At.Shift;

@Mixin(NetHandlerPlayServer.class)
public abstract class MixinNetHandlerPlayServer {

    private static final String CHECK_THREAD = "Lnet/minecraft/network/PacketThreadUtil;checkThreadAndEnqueue(Lnet/minecraft/network/Packet;Lnet/minecraft/network/INetHandler;Lnet/minecraft/util/IThreadListener;)V";
    @Shadow public EntityPlayerMP playerEntity;

    @Inject(method = "processEntityAction", cancellable = true, at = @At(value = "INVOKE", target = CHECK_THREAD, shift = Shift.AFTER))
    public void onEntityActionRecieved(CPacketEntityAction packet, CallbackInfo callback) { // TODO remove when sponge has a event that can replace this
        switch (packet.getAction()) {
            case START_SNEAKING :
            case STOP_SNEAKING :
                PlayerToggleSneakEvent toggleSneak = new PlayerToggleSneakEvent(PorePlayer.of((Player)playerEntity), packet.getAction().equals(Action.START_SNEAKING));
                Bukkit.getPluginManager().callEvent(toggleSneak);
                if (toggleSneak.isCancelled()) callback.cancel();
                break;
            case START_SPRINTING:
            case STOP_SPRINTING:
                PlayerToggleSprintEvent toggleSprint = new PlayerToggleSprintEvent(PorePlayer.of((Player)playerEntity), packet.getAction().equals(Action.START_SPRINTING));
                Bukkit.getPluginManager().callEvent(toggleSprint);
                if (toggleSprint.isCancelled()) callback.cancel();
                break;
            default:
                break;
        }
    }

    @Inject(method = "processPlayerAbilities", cancellable = true, at = @At(value = "INVOKE", target = CHECK_THREAD, shift = Shift.AFTER))
    public void onAbilitiesRecieved(CPacketPlayerAbilities packetIn, CallbackInfo callback) {
        if (this.playerEntity.capabilities.allowFlying && packetIn.isFlying() != this.playerEntity.capabilities.isFlying) {
            PlayerToggleFlightEvent event = new PlayerToggleFlightEvent(PorePlayer.of((Player)playerEntity), packetIn.isFlying());
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                playerEntity.sendPlayerAbilities();
                callback.cancel();
            }
        }
    }
}
