/*
 * PoreRT - A Bukkit to Sponge Bridge
 *
 * Copyright (c) 2016, Maxqia <https://github.com/Maxqia> AGPLv3
 * Copyright (c) 2014-2016, Lapis <https://github.com/LapisBlue> MIT
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

package blue.lapis.pore.impl.inventory;

import blue.lapis.pore.converter.type.attribute.InventoryTypeConverter;
import blue.lapis.pore.converter.type.material.ItemStackConverter;
import blue.lapis.pore.converter.wrapper.WrapperConverter;
import blue.lapis.pore.util.PoreWrapper;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.NonNullList;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.type.CarriedInventory;
import org.spongepowered.common.item.inventory.custom.CustomContainer;
import org.spongepowered.common.item.inventory.custom.CustomInventory;
import org.spongepowered.common.item.inventory.util.ItemStackUtil;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Optional;

public class PoreInventory extends PoreWrapper<Inventory> implements org.bukkit.inventory.Inventory {

    public static PoreInventory of(Inventory handle) {
        if (handle instanceof ContainerChest) {
            try {
                Field field = handle.getClass().getDeclaredField("field_75155_e");
                field.setAccessible(true);
                return WrapperConverter.of(PoreInventory.class, field.get(handle));
            } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return WrapperConverter.of(PoreInventory.class, handle);
    }

    protected PoreInventory(Inventory handle) {
        super(handle);
    }

    protected IInventory getInventory() {
        //System.out.println(getHandle().getClass().getName());
        Object handle = getHandle();
        while (handle instanceof CustomContainer || handle instanceof CustomInventory) {
            handle = getCustom(handle);
        }

        if (IInventory.class.isAssignableFrom(handle.getClass())) {
            return (IInventory) handle;
        } else {
            throw new RuntimeException("Couldn't get inventory!");
        }
    }

    protected Object getCustom(Object handle) {
        try {
            Field field = handle.getClass().getDeclaredField("inv");
            field.setAccessible(true);
            return field.get(handle);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    protected List<net.minecraft.item.ItemStack> getInternalContents() {
        try {
            //System.out.println(getHandle().getClass());
            Field field = getInventory().getClass().getDeclaredField("field_70482_c");
            field.setAccessible(true);
            return (NonNullList<net.minecraft.item.ItemStack>) field.get(getInventory());
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            throw new RuntimeException("Couldn't get inventory contents!");
        }
    }

    @Override
    public ItemStack[] getStorageContents() {
        return getContents();
    }

    @Override
    public void setStorageContents(ItemStack[] items) throws IllegalArgumentException {
        setContents(items);
    }

    @Override
    public int getSize() {
        return getInventory().getSizeInventory();
    }

    @Override
    public int getMaxStackSize() {
        return this.getHandle().getMaxStackSize();
    }

    @Override
    public void setMaxStackSize(int size) {
        this.getHandle().setMaxStackSize(size);
    }

    @Override
    public String getName() {
        return this.getHandle().getName().get(Locale.ENGLISH);
    }

    @Override
    public ItemStack getItem(int index) {
        return ItemStackConverter.of(ItemStackUtil.fromNative((((IInventory) this.getHandle()).getStackInSlot(index))));
    }

    @Override
    @SuppressWarnings("deprecation")
    public void setItem(int index, ItemStack item) {
        getInventory().setInventorySlotContents(index, ((item == null || item.getTypeId() == 0)
                ? net.minecraft.item.ItemStack.EMPTY : ItemStackUtil.toNative(ItemStackConverter.of(item)).copy()));
    }

    @Override
    public HashMap<Integer, ItemStack> addItem(ItemStack... items) throws IllegalArgumentException {
        HashMap<Integer, ItemStack> remainder = Maps.newHashMap();
        int i = 0;
        for (ItemStack stack : items) {
            if (stack == null) {
                throw new IllegalArgumentException("Cannot add null ItemStack");
            }
            org.spongepowered.api.item.inventory.ItemStack spongeIs = ItemStackConverter.of(stack);
            this.getHandle().offer(spongeIs);
            if (spongeIs.getQuantity() > 0) {
                remainder.put(i, ItemStackConverter.of(spongeIs));
            }
            ++i;
        }
        return remainder;
    }

    @Override
    public HashMap<Integer, ItemStack> removeItem(ItemStack... items) throws IllegalArgumentException {
        HashMap<Integer, ItemStack> notRemoved = Maps.newHashMap();
        int i = 0;
        for (ItemStack stack : items) {
            Inventory query = this.getHandle().query(ItemStackConverter.of(stack));
            if (query.hasChildren()) {
                notRemoved.put(i, stack);
                continue;
            }
            query.clear(); //TODO: verify this affects the parent as well
            ++i;
        }
        return notRemoved;
    }

    @Override
    public ItemStack[] getContents() {
        ItemStack[] items = new ItemStack[getSize()];
        List<net.minecraft.item.ItemStack> mcItems = this.getInternalContents();

        int size = Math.min(items.length, mcItems.size());
        for (int i = 0; i < size; i++) {
            items[i] = (mcItems.get(i) == net.minecraft.item.ItemStack.EMPTY) ? null :
                ItemStackConverter.of(ItemStackUtil.fromNative(mcItems.get(i)));
        }
        return items;
    }

    @Override
    public void setContents(ItemStack[] items) throws IllegalArgumentException {
        if (getSize() < items.length) {
            throw new IllegalArgumentException("Invalid inventory size; expected " + getSize() + " or less");
        }

        for (int i = 0; i < getSize(); i++) {
            if (i >= items.length) {
                setItem(i, null);
            } else {
                setItem(i, items[i]);
            }
        }
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean contains(int materialId) {
        for (ItemStack item : getStorageContents()) {
            if (item != null && item.getTypeId() == materialId) {
                return true;
            }
        }
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean contains(Material material) {
        Validate.notNull(material, "Material cannot be null");
        return contains(material.getId());
    }

    @Override
    public boolean contains(ItemStack item) {
        if (item == null) {
            return false;
        }
        for (ItemStack i : getStorageContents()) {
            if (item.equals(i)) {
                return true;
            }
        }
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean contains(int materialId, int amount) {
        if (amount <= 0) {
            return true;
        }
        for (ItemStack item : getStorageContents()) {
            if (item != null && item.getTypeId() == materialId) {
                if ((amount -= item.getAmount()) <= 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean contains(Material material, int amount) {
        Validate.notNull(material, "Material cannot be null");
        return contains(material.getId(), amount);
    }

    @Override
    public boolean contains(ItemStack item, int amount) {
        if (item == null) {
            return false;
        }
        if (amount <= 0) {
            return true;
        }
        for (ItemStack i : getStorageContents()) {
            if (item.equals(i) && --amount <= 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAtLeast(ItemStack item, int amount) {
        if (item == null) {
            return false;
        }
        if (amount <= 0) {
            return true;
        }
        for (ItemStack i : getStorageContents()) {
            if (item.isSimilar(i) && (amount -= i.getAmount()) <= 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public HashMap<Integer, ItemStack> all(int materialId) {
        HashMap<Integer, ItemStack> slots = new HashMap<Integer, ItemStack>();

        ItemStack[] inventory = getStorageContents();
        for (int i = 0; i < inventory.length; i++) {
            ItemStack item = inventory[i];
            if (item != null && item.getTypeId() == materialId) {
                slots.put(i, item);
            }
        }
        return slots;
    }

    @Override
    @SuppressWarnings("deprecation")
    public HashMap<Integer, ItemStack> all(Material material) {
        Validate.notNull(material, "Material cannot be null");
        return all(material.getId());
    }

    @Override
    public HashMap<Integer, ItemStack> all(ItemStack item) {
        HashMap<Integer, ItemStack> slots = new HashMap<Integer, ItemStack>();
        if (item != null) {
            ItemStack[] inventory = getStorageContents();
            for (int i = 0; i < inventory.length; i++) {
                if (item.equals(inventory[i])) {
                    slots.put(i, inventory[i]);
                }
            }
        }
        return slots;
    }

    @Override
    @SuppressWarnings("deprecation")
    public int first(int materialId) {
        ItemStack[] inventory = getStorageContents();
        for (int i = 0; i < inventory.length; i++) {
            ItemStack item = inventory[i];
            if (item != null && item.getTypeId() == materialId) {
                return i;
            }
        }
        return -1;
    }

    @Override
    @SuppressWarnings("deprecation")
    public int first(Material material) {
        Validate.notNull(material, "Material cannot be null");
        return first(material.getId());
    }

    @Override
    public int first(ItemStack item) {
        return first(item, true);
    }

    private int first(ItemStack item, boolean withAmount) {
        if (item == null) {
            return -1;
        }
        ItemStack[] inventory = getStorageContents();
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] == null) {
                continue;
            }

            if (withAmount ? item.equals(inventory[i]) : item.isSimilar(inventory[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int firstEmpty() {
        ItemStack[] inventory = getStorageContents();
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] == null) {
                return i;
            }
        }
        return -1;
    }


    @Override
    @SuppressWarnings("deprecation")
    public void remove(Material material) throws IllegalArgumentException {
        Validate.notNull(material, "Material cannot be null");
        remove(material.getId());
    }

    @Override
    @SuppressWarnings("deprecation")
    public void remove(int materialId) {
        ItemStack[] items = getStorageContents();
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null && items[i].getTypeId() == materialId) {
                clear(i);
            }
        }
    }

    @Override
    public void remove(ItemStack item) {
        ItemStack[] items = getStorageContents();
        for (int i = 0; i < items.length; i++) {
            if (items[i] != null && items[i].equals(item)) {
                clear(i);
            }
        }
    }

    @Override
    public void clear(int index) {
        setItem(index, null);
    }

    @Override
    public void clear() {
        this.getHandle().clear();
    }

    @Override
    public List<HumanEntity> getViewers() {
        // TODO: We're waiting on SpongeAPI for this one
        return ImmutableList.of();
    }

    @Override
    public String getTitle() {
        return this.getName();
    }

    @Override
    public InventoryType getType() {
        return InventoryTypeConverter.of(getHandle().getArchetype());
    }

    @Override
    @SuppressWarnings("rawtypes") //TODO fix this
    public InventoryHolder getHolder() {
        if (this.getHandle() instanceof CarriedInventory) {
            Optional<?> carrier = ((CarriedInventory) this.getHandle()).getCarrier();
            if (carrier.isPresent()) {
                return WrapperConverter.of(carrier.get());
            }
        }
        return null;
    }

    // I've worked with Bukkit for more than two years and this method is by far
    // one of the worst ideas I've seen in it.
    @Override
    public ListIterator<ItemStack> iterator() {
        return this.iterator(0);
    }

    @Override
    public ListIterator<ItemStack> iterator(int index) {
        return new ItemStackIterator(this, index);
    }

    /**
     * Convenience method for getting the first ItemStack in an inventory that
     * matches the given criterion.
     * @param bound The criterion to pass to
     * {@link org.spongepowered.api.item.inventory.Inventory#query(Object...)}.
     * @return The first match found in this inventory, or {@code null} if
     *         one cannot be discovered.
     */
    protected ItemStack getArbitraryStack(Object bound) {
        Inventory query = this.getHandle().query(bound);
        if (query.capacity() >= 1) {
            Optional<org.spongepowered.api.item.inventory.ItemStack> stack = query.peek();
            if (stack.isPresent()) {
                return ItemStackConverter.of(stack.get());
            }
        }
        return null;
    }

    @Override
    public Location getLocation() {
        // TODO Auto-generated method stub
        throw new NotImplementedException("TODO");
    }
}
