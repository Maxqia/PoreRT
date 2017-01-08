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

package blue.lapis.pore.impl.command;

import blue.lapis.pore.Pore;
import blue.lapis.pore.converter.wrapper.WrapperConverter;
import blue.lapis.pore.impl.permissions.PorePermissible;
import blue.lapis.pore.util.PoreText;

import org.bukkit.Server;
import org.bukkit.command.CommandSender;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.Text;

public class PoreCommandSender extends PorePermissible implements CommandSender {

    public static CommandSender of(CommandSource source) {
        return WrapperConverter.of(CommandSender.class, source);
    }

    protected PoreCommandSender(CommandSource handle) {
        super(handle);
    }

    @Override
    public CommandSource getHandle() {
        return (CommandSource) super.getHandle();
    }

    @Override
    public String getName() {
        return getHandle().getName();
    }

    @Override
    public Server getServer() {
        return Pore.getServer();
    }

    @Override
    public void sendMessage(String message) {
        getHandle().sendMessage(PoreText.convert(message));
    }

    @Override
    public void sendMessage(String[] messages) {
        Text[] texts = new Text[messages.length];
        for (int i = 0; i < messages.length; i++) {
            texts[i] = PoreText.convert(messages[i]);
        }
        this.getHandle().sendMessages(texts);
    }

}
