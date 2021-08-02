package lv.cebbys.mcmods.refabslab.utilities;

import net.minecraft.block.entity.BlockEntity;

public interface BlockStateEntityProvider {
    BlockEntity getBlockEntity();
    void setBlockEntity(BlockEntity entity);
}
