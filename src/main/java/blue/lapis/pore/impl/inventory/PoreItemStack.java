package blue.lapis.pore.impl.inventory;

import blue.lapis.pore.converter.type.material.MaterialConverter;

import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.property.item.UseLimitProperty;
import org.spongepowered.api.item.inventory.ItemStack;

public class PoreItemStack extends org.bukkit.inventory.ItemStack {

    ItemStack handle;

    public PoreItemStack(ItemStack handle) {
        super(MaterialConverter.of(handle.getItem()), handle.getQuantity());
        this.handle = handle;
    }

    public ItemStack getHandle() {
        return handle;
    }

    @Override
    public short getDurability() {
        int maxdur = getHandle().getProperty(UseLimitProperty.class).orElse(null).getValue();
        int dur = getHandle().get(Keys.ITEM_DURABILITY).orElse(0);
        return (short) (maxdur - dur);
    }

    @Override
    public void setDurability(short durability) {
        int maxdur = getHandle().getProperty(UseLimitProperty.class).get().getValue();
        getHandle().offer(Keys.ITEM_DURABILITY, maxdur - durability);
        return;
    }
}
