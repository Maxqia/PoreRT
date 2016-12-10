package blue.lapis.pore.impl.block;

import blue.lapis.pore.converter.type.material.MaterialConverter;
import blue.lapis.pore.converter.wrapper.WrapperConverter;

import org.bukkit.material.MaterialData;
import org.spongepowered.api.block.tileentity.FlowerPot;
import org.spongepowered.api.data.manipulator.mutable.RepresentedItemData;

import java.util.Optional;

public class PoreFlowerPot extends PoreBlockState implements org.bukkit.block.FlowerPot {

    public static PoreFlowerPot of(FlowerPot handle) {
        return WrapperConverter.of(PoreFlowerPot.class, handle);
    }

    protected PoreFlowerPot(FlowerPot handle) {
        super(handle);
    }

    @Override
    FlowerPot getTileEntity() {
        return (FlowerPot) super.getTileEntity();
    }

    @Override
    public MaterialData getContents() {
        Optional<RepresentedItemData> opt = getTileEntity().get(RepresentedItemData.class);
        if (opt.isPresent()) {
            return new MaterialData(MaterialConverter.of(opt.get().item().get().getType()));
        }
        return null;
    }

    @Override
    public void setContents(MaterialData item) {
        Optional<RepresentedItemData> opt = getTileEntity().getOrCreate(RepresentedItemData.class);
        if (opt.isPresent()) {
            opt.get().item().set(MaterialConverter.asItem(item.getItemType()).getTemplate());
        }
    }

}
