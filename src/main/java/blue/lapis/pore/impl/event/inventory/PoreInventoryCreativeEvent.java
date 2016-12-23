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
import blue.lapis.pore.impl.entity.PorePlayer;
import blue.lapis.pore.impl.inventory.PoreInventory;
import blue.lapis.pore.impl.inventory.PoreInventoryView;

import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;

import java.util.List;

//TODO: this is just a reimplementation of InventoryClickEvent. Should we try to wrap it?
@RegisterEvent
public final class PoreInventoryCreativeEvent extends InventoryCreativeEvent
    implements PoreEvent<ClickInventoryEvent.Creative> {

    private final ClickInventoryEvent.Creative handle;

    public PoreInventoryCreativeEvent(ClickInventoryEvent.Creative handle) {
        super(null, null, -1, null);
        this.handle = checkNotNull(handle, "handle");
    }

    public ClickInventoryEvent.Creative getHandle() {
        return this.handle;
    }

    @Override
    public Inventory getInventory() {
        return PoreInventory.of(this.getHandle().getTargetInventory());
    }

    @Override
    public Inventory getClickedInventory() {
        return getInventory();
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
        return PorePlayer.of(getHandle().getCause()
                .get(NamedCause.OWNER, org.spongepowered.api.entity.living.player.Player.class).orElse(null));
    }

    @Override
    public Event.Result getResult() {
        return EventResultConverter.of(getHandle().isCancelled());
    }

    @Override
    public void setResult(Event.Result newResult) {
        getHandle().setCancelled(EventResultConverter.of(newResult));
    }

    @Override
    public InventoryType.SlotType getSlotType() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public ItemStack getCursor() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public void setCursor(ItemStack stack) {
        this.getView().setCursor(stack);
    }

    @Override
    public ItemStack getCurrentItem() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public boolean isRightClick() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public boolean isLeftClick() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public boolean isShiftClick() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public void setCurrentItem(ItemStack stack) {
        throw new NotImplementedException("TODO");
    }

    @Override
    public int getSlot() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public int getRawSlot() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public int getHotbarButton() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public InventoryAction getAction() {
        throw new NotImplementedException("TODO");
    }

    @Override
    public ClickType getClick() {
        throw new NotImplementedException("TODO");
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
