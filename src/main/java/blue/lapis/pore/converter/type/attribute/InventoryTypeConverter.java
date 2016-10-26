package blue.lapis.pore.converter.type.attribute;

import org.apache.commons.lang3.NotImplementedException;
import org.bukkit.event.inventory.InventoryType;
import org.spongepowered.api.item.inventory.InventoryArchetype;
import org.spongepowered.api.item.inventory.InventoryArchetypes;

public class InventoryTypeConverter {
    private InventoryTypeConverter() {
    }

    public static InventoryArchetype of(InventoryType type) {
        switch (type) {
            case CHEST:
            case ENDER_CHEST:
                return InventoryArchetypes.CHEST;
            case DISPENSER:
            case DROPPER:
                return InventoryArchetypes.DISPENSER;
            case FURNACE:
                return InventoryArchetypes.FURNACE;
            case WORKBENCH:
                return InventoryArchetypes.WORKBENCH;
            case CRAFTING:
                return InventoryArchetypes.CRAFTING;
            case ENCHANTING:
                return InventoryArchetypes.ENCHANTING_TABLE;
            case BREWING:
                return InventoryArchetypes.BREWING_STAND;
            case PLAYER:
                return InventoryArchetypes.PLAYER;
            case CREATIVE:
                throw new NotImplementedException("Creative inventory not available!");
            case MERCHANT:
                return InventoryArchetypes.VILLAGER;
            case ANVIL:
                return InventoryArchetypes.ANVIL;
            case BEACON:
                return InventoryArchetypes.BEACON;
            case HOPPER:
                return InventoryArchetypes.HOPPER;
            default:
                throw new NotImplementedException(type.name());
        }
    }
}
