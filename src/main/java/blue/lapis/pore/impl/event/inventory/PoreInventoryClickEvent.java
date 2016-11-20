/*
 * Pore(RT)
 * Copyright (c) 2014-2016, Lapis <https://github.com/LapisBlue>
 * Copyright (c) 2014-2016, Contributors
 *
 * The MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package blue.lapis.pore.impl.event.inventory;

import static com.google.common.base.Preconditions.checkNotNull;

import blue.lapis.pore.converter.type.attribute.EventResultConverter;
import blue.lapis.pore.event.PoreEvent;
import blue.lapis.pore.event.RegisterEvent;
import blue.lapis.pore.event.Source;
import blue.lapis.pore.impl.entity.PoreHumanEntity;
import blue.lapis.pore.impl.inventory.PoreInventory;
import blue.lapis.pore.impl.inventory.PoreInventoryView;

import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.spongepowered.api.entity.living.Humanoid;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;
import org.spongepowered.common.item.inventory.adapter.impl.slots.SlotAdapter;

import java.util.List;

@RegisterEvent
public final class PoreInventoryClickEvent extends InventoryClickEvent implements PoreEvent<ClickInventoryEvent> {

    private final ClickInventoryEvent handle;
    private final Humanoid player;

    public PoreInventoryClickEvent(ClickInventoryEvent handle, @Source Humanoid player) {
        super(null, null, -1, null, null);
        this.handle = checkNotNull(handle, "handle");
        this.player = checkNotNull(player, "player");
    }

    public ClickInventoryEvent getHandle() {
        return this.handle;
    }

    @Override
    public Inventory getInventory() {
        return PoreInventory.of(this.getHandle().getTargetInventory());
    }

    @Override
    public List<HumanEntity> getViewers() {
        return this.getInventory().getViewers();
    }

    @Override
    public InventoryView getView() {
        return PoreInventoryView.builder()
                .setPlayer(getWhoClicked())
                .setTopInventory(getInventory())
                .setBottomInventory(getWhoClicked().getInventory())
                .build();
    }

    @Override
    public HumanEntity getWhoClicked() {
        return PoreHumanEntity.of(player);
    }

    @Override
    public void setResult(Event.Result newResult) {
        getHandle().setCancelled(EventResultConverter.of(newResult));
    }

    @Override
    public Event.Result getResult() {
        return EventResultConverter.of(getHandle().isCancelled());
    }

    @Override
    public InventoryType.SlotType getSlotType() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public ItemStack getCursor() {
        return super.getCursor();
    }


    @Override
    @SuppressWarnings("deprecation")
    public void setCursor(ItemStack stack) {
        super.setCursor(stack);
    }

    @Override
    public ItemStack getCurrentItem() {
        return super.getCurrentItem();
    }

    @Override
    public void setCurrentItem(ItemStack stack) {
        super.setCurrentItem(stack);
    }

    @Override
    public boolean isRightClick() {
        return getHandle().getClass().isAssignableFrom(ClickInventoryEvent.Secondary.class);
    }

    @Override
    public boolean isLeftClick() {
        return getHandle().getClass().isAssignableFrom(ClickInventoryEvent.Primary.class);
    }

    @Override
    public boolean isShiftClick() {
        return getHandle().getClass().isAssignableFrom(ClickInventoryEvent.Shift.class);
    }

    @Override
    public int getSlot() {
        return getRawSlot();
    }

    @Override
    public int getRawSlot() {
        return ((SlotAdapter) getHandle().getTransactions().get(0).getSlot()).slotNumber;
    }

    @Override
    public int getHotbarButton() {
        return ((ClickInventoryEvent.NumberPress) getHandle()).getNumber();
    } //TODO is this correct?

    @Override
    public InventoryAction getAction() {
        if (this.isShiftClick()) {
            return InventoryAction.MOVE_TO_OTHER_INVENTORY;
        }
        return InventoryAction.UNKNOWN;
    } //TODO implement

    @Override
    public ClickType getClick() {
        if (getHandle().getClass().isAssignableFrom(ClickInventoryEvent.Shift.Primary.class)) {
            return ClickType.SHIFT_LEFT;
        }

        if (getHandle().getClass().isAssignableFrom(ClickInventoryEvent.Shift.Secondary.class)) {
            return ClickType.SHIFT_RIGHT;
        }

        if (getHandle().getClass().isAssignableFrom(ClickInventoryEvent.Primary.class)) {
            return ClickType.LEFT;
        }

        if (getHandle().getClass().isAssignableFrom(ClickInventoryEvent.Middle.class)) {
            return ClickType.MIDDLE;
        }

        if (getHandle().getClass().isAssignableFrom(ClickInventoryEvent.Secondary.class)) {
            return ClickType.RIGHT;
        }

        if (getHandle().getClass().isAssignableFrom(ClickInventoryEvent.Creative.class)) {
            return ClickType.CREATIVE;
        }

        if (getHandle().getClass().isAssignableFrom(ClickInventoryEvent.NumberPress.class)) {
            return ClickType.NUMBER_KEY;
        }

        if (getHandle().getClass().isAssignableFrom(ClickInventoryEvent.Drop.Full.class)) {
            return ClickType.CONTROL_DROP;
        }

        if (getHandle().getClass().isAssignableFrom(ClickInventoryEvent.Drop.class)) {
            return ClickType.DROP;
        }

        /*if (getHandle().getClass().isAssignableFrom(ClickInventoryEvent.class)) {
            return ClickType.WINDOW_BORDER_LEFT; //TODO not available in Sponge
        }

        if (getHandle().getClass().isAssignableFrom(ClickInventoryEvent.class)) {
            return ClickType.WINDOW_BORDER_RIGHT;
        }

        if (getHandle().getClass().isAssignableFrom(ClickInventoryEvent.class)) {
            return ClickType.DOUBLE_CLICK;
        }*/

        return ClickType.UNKNOWN;
    }

    @Override
    public boolean isCancelled() {
        return handle.isCancelled();
    }

    @Override
    public void setCancelled(boolean cancelled) {
        handle.setCancelled(cancelled);
    }

    @Override
    public String toString() {
        return toStringHelper().toString();
    }
}
