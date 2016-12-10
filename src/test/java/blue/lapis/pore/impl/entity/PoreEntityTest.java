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

package blue.lapis.pore.impl.entity;

import static blue.lapis.pore.PoreTests.PACKAGE;

import blue.lapis.pore.PoreTests;
import blue.lapis.pore.test.IgnoreResult;

import com.google.common.collect.Sets;
import com.google.common.reflect.ClassPath;
import org.bukkit.entity.Entity;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;
import java.util.Set;

public class PoreEntityTest {

    static final String BUKKIT_PACKAGE = "org.bukkit.entity";
    static final String PORE_PACKAGE = PACKAGE + "impl.entity";

    @Rule
    public IgnoreResult ignoreResult = new IgnoreResult();

    @Test
    public void findUnimplementedEntities() throws IOException {
        Set<Class<?>> entities = Sets.newLinkedHashSet();

        for (ClassPath.ClassInfo info : PoreTests.getClassPath().getTopLevelClassesRecursive(BUKKIT_PACKAGE)) {
            try {
                Class<?> entity = info.load();
                if (Entity.class.isAssignableFrom(entity)) {
                    entities.add(entity);
                }
            } catch (Throwable e) {
                PoreTests.getLogger().warn("Failed to load {}", info, e);
            }
        }

        for (ClassPath.ClassInfo info : PoreTests.getClassPath().getTopLevelClassesRecursive(PORE_PACKAGE)) {
            Class<?> type;
            try {
                type = info.load();
                if (!Entity.class.isAssignableFrom(type)) {
                    continue;
                }
            } catch (Throwable e) {
                PoreTests.getLogger().warn("Failed to load {}", info, e);
                continue;
            }

            try {
                entities.remove(type.getInterfaces()[0]);
            } catch (Exception e) {
                // interface not found?
            }
        }

        if (!entities.isEmpty()) {
            for (Class<?> entity : entities) {
                PoreTests.getLogger().warn("Entity \"{}\" is missing", entity.getSimpleName());
            }
        }
    }

}
