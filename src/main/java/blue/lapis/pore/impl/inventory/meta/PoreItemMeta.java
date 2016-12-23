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

package blue.lapis.pore.impl.inventory.meta;

import blue.lapis.pore.impl.enchantments.PoreEnchantment;
import blue.lapis.pore.util.PoreText;
import blue.lapis.pore.util.PoreWrapper;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentWrapper;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;
import org.spongepowered.api.data.manipulator.mutable.DisplayNameData;
import org.spongepowered.api.data.manipulator.mutable.item.EnchantmentData;
import org.spongepowered.api.data.manipulator.mutable.item.HideData;
import org.spongepowered.api.data.manipulator.mutable.item.LoreData;
import org.spongepowered.api.data.meta.ItemEnchantment;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class PoreItemMeta extends PoreWrapper<ItemStack> implements ItemMeta {

    public PoreItemMeta(ItemStack holder) {
        super(holder);
    }

    @Override
    public boolean hasDisplayName() {
        Optional<DisplayNameData> displayName = getHandle().get(DisplayNameData.class);
        if (displayName.isPresent()) {
            if (displayName.get().displayName().exists()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getDisplayName() {
        Optional<DisplayNameData> displayName = getHandle().get(DisplayNameData.class);
        if (displayName.isPresent()) {
            return PoreText.convert(displayName.get().displayName().get());
        }
        return null;
    }

    @Override
    public void setDisplayName(String name) {
        Optional<DisplayNameData> displayName = getHandle().getOrCreate(DisplayNameData.class);
        if (displayName.isPresent()) {
            getHandle().offer(displayName.get().displayName().set(PoreText.convert(name)));
        }

    }

    @Override
    public boolean hasLore() {
        Optional<LoreData> lore = getHandle().get(LoreData.class);
        if (lore.isPresent()) {
            if (lore.get().lore().exists()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> getLore() {
        List<String> lores = new ArrayList<String>();
        Optional<LoreData> lore = getHandle().get(LoreData.class);
        if (lore.isPresent()) {
            for (Text text : lore.get().lore()) {
                lores.add(PoreText.convert(text));
            }
        }
        return lores;
    }

    @Override
    public void setLore(List<String> lore) {
        Optional<LoreData> loreData = getHandle().get(LoreData.class);
        if (loreData.isPresent()) {
            List<Text> text = new ArrayList<Text>();
            for (String string : lore) {
                text.add(PoreText.convert(string));
            }
            loreData.get().setElements(text);
        }
    }

    @Override
    public boolean hasEnchants() {
        Optional<EnchantmentData> enchants = getHandle().get(EnchantmentData.class);
        return enchants.isPresent() && enchants.get().asList().size() > 0 ;
    }

    @Override
    public boolean hasEnchant(Enchantment ench) {
        Optional<EnchantmentData> enchants = getHandle().get(EnchantmentData.class);
        org.spongepowered.api.item.Enchantment target = getEnchant(ench);
        if (enchants.isPresent()) {
            for (ItemEnchantment itmEnch : enchants.get().asList()) {
                if (itmEnch.getEnchantment().equals(target)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int getEnchantLevel(Enchantment ench) {
        Optional<EnchantmentData> enchants = getHandle().get(EnchantmentData.class);
        org.spongepowered.api.item.Enchantment target = getEnchant(ench);
        if (enchants.isPresent()) {
            for (ItemEnchantment itmEnch : enchants.get().asList()) {
                if (itmEnch.getEnchantment().equals(target)) {
                    return itmEnch.getLevel();
                }
            }
        }
        return 0;
    }

    @Override
    public Map<Enchantment, Integer> getEnchants() {
        Optional<EnchantmentData> enchants = getHandle().get(EnchantmentData.class);
        Map<Enchantment, Integer> map = new HashMap<Enchantment, Integer>();
        if (enchants.isPresent()) {
            for (ItemEnchantment itmEnch : enchants.get().asList()) {
                map.put(Enchantment.getByName(itmEnch.getEnchantment().getName()), itmEnch.getLevel());
            }
        }
        return ImmutableMap.copyOf(map);
    }

    @Override
    public boolean addEnchant(Enchantment ench, int level, boolean ignoreLevelRestriction) {
        Optional<EnchantmentData> enchants = getHandle().getOrCreate(EnchantmentData.class);
        if (enchants.isPresent()) {
            if (level > ench.getMaxLevel() && !ignoreLevelRestriction) {
                level = ench.getMaxLevel();
            }

            org.spongepowered.api.item.Enchantment copy = getEnchant(ench);
            getHandle().offer(enchants.get().addElement(new ItemEnchantment(copy, level)));
            return true;
        }
        return false;
    }

    @Override
    public boolean removeEnchant(Enchantment ench) {
        Optional<EnchantmentData> enchants = getHandle().get(EnchantmentData.class);
        org.spongepowered.api.item.Enchantment target = getEnchant(ench);
        if (enchants.isPresent()) {
            for (ItemEnchantment itmEnch : enchants.get().asList()) {
                if (itmEnch.getEnchantment().equals(target)) {
                    getHandle().offer(enchants.get().remove(itmEnch));
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean hasConflictingEnchant(Enchantment ench) {
        Optional<EnchantmentData> enchants = getHandle().get(EnchantmentData.class);
        org.spongepowered.api.item.Enchantment target = getEnchant(ench);
        if (enchants.isPresent()) {
        for (ItemEnchantment itmEnch : enchants.get().asList()) {
            if (!itmEnch.getEnchantment().isCompatibleWith(target)) {
                return true;
            }
        }
        }
        return false;
    }

    private static org.spongepowered.api.item.Enchantment getEnchant(Enchantment ench) {
        if (ench instanceof EnchantmentWrapper) {
            ench = ((EnchantmentWrapper) ench).getEnchantment();
        }
        return ((PoreEnchantment) ench).getHandle();
    }

    @Override
    public void addItemFlags(ItemFlag... itemFlags) {
        for (ItemFlag flag : itemFlags) {
            switch (flag) {
                case HIDE_ATTRIBUTES:
                    getHandle().getOrCreate(HideData.class).get().hideAttributes().set(true);
                    break;
                case HIDE_DESTROYS:
                    getHandle().getOrCreate(HideData.class).get().hideCanDestroy().set(true);
                    break;
                case HIDE_ENCHANTS:
                    getHandle().getOrCreate(HideData.class).get().hideEnchantments().set(true);
                    break;
                case HIDE_PLACED_ON:
                    getHandle().getOrCreate(HideData.class).get().hideCanPlace().set(true);
                    break;
                case HIDE_POTION_EFFECTS:
                    getHandle().getOrCreate(HideData.class).get().hideMiscellaneous().set(true);
                    break;
                case HIDE_UNBREAKABLE:
                    getHandle().getOrCreate(HideData.class).get().hideUnbreakable().set(true);
                    break;
                default:
                    throw new NotImplementedException("TODO");
            }
        }
    }

    @Override
    public void removeItemFlags(ItemFlag... itemFlags) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public Set<ItemFlag> getItemFlags() {
        //return ImmutableSet.of();
        throw new NotImplementedException("TODO");
    }

    @Override
    public boolean hasItemFlag(ItemFlag flag) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public ItemMeta clone() {
        return new PoreItemMeta(getHandle().copy());
    }

    @Override
    public Map<String, Object> serialize() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public boolean isUnbreakable() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public void setUnbreakable(boolean unbreakable) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public Spigot spigot() {
        throw new NotImplementedException("TODO");
    }
}
