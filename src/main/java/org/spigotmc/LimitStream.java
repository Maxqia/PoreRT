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

import net.minecraft.nbt.NBTSizeTracker;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class LimitStream extends FilterInputStream {

    private final NBTSizeTracker limit;

    public LimitStream(InputStream is, NBTSizeTracker limit) {
        super( is );
        this.limit = limit;
    }

    @Override
    public int read() throws IOException {
        limit.read( 8 );
        return super.read();
    }

    @Override
    public int read(byte[] b) throws IOException {
        limit.read( b.length * 8 );
        return super.read( b );
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        limit.read( len * 8 );
        return super.read( b, off, len );
    }
}
