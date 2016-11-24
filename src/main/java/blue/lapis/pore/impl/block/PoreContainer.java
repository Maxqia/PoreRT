package blue.lapis.pore.impl.block;

import org.bukkit.block.Lockable;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.data.manipulator.mutable.tileentity.LockableData;

import java.util.Optional;

public class PoreContainer extends PoreBlockState implements Lockable {

    protected PoreContainer(TileEntity handle) {
        super(handle);
    }

    @Override
    public boolean isLocked() {
        Optional<LockableData> lock = getTileEntity().get(LockableData.class);
        if (lock.isPresent()) {
            if (lock.get().lockToken().exists()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getLock() {
        return getTileEntity().get(LockableData.class).get().lockToken().get();
    }

    @Override
    public void setLock(String key) {
        getTileEntity().get(LockableData.class).get().lockToken().set(key);
    }

}
