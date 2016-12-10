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

package blue.lapis.pore.impl.block;

import static blue.lapis.pore.PoreTests.PACKAGE;

import blue.lapis.pore.PoreTests;
import blue.lapis.pore.test.IgnoreResult;

import com.google.common.collect.Sets;
import com.google.common.reflect.ClassPath;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.Set;

public class PoreBlockTest {

    static final String BUKKIT_PACKAGE = "org.bukkit.block";
    static final String PORE_PACKAGE = PACKAGE + "impl.block";

    @Rule
    public IgnoreResult ignoreResult = new IgnoreResult();

    @Test
    public void findUnimplementedBlocks() throws IOException {
        Set<Class<?>> blocks = Sets.newLinkedHashSet();

        for (ClassPath.ClassInfo info : PoreTests.getClassPath().getTopLevelClassesRecursive(BUKKIT_PACKAGE)) {
            try {
                Class<?> block = info.load();
                if (Block.class.isAssignableFrom(block) || BlockState.class.isAssignableFrom(block)) {
                    blocks.add(block);
                }
            } catch (Throwable e) {
                PoreTests.getLogger().warn("Failed to load {}", info, e);
            }
        }

        for (ClassPath.ClassInfo info : PoreTests.getClassPath().getTopLevelClassesRecursive(PORE_PACKAGE)) {
            Class<?> type;
            try {
                type = info.load();
                if (!(Block.class.isAssignableFrom(type) || BlockState.class.isAssignableFrom(type))) {
                    continue;
                }
            } catch (Throwable e) {
                PoreTests.getLogger().warn("Failed to load {}", info, e);
                continue;
            }

            try {
                blocks.remove(type.getInterfaces()[0]);
            } catch (Exception e) {
                // interface not found?
            }
        }

        if (!blocks.isEmpty()) {
            for (Class<?> block : blocks) {
                PoreTests.getLogger().warn("Block \"{}\" is missing", block.getSimpleName());
            }
        }
    }

}
