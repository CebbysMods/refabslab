package lv.cebbys.mcmods.refabslab.content;

import lv.cebbys.mcmods.refabslab.content.block.DoubleSlabBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import static lv.cebbys.mcmods.refabslab.RefabslabCommon.REGISTRY;

public class RefabslabBlocks {
    public static final Block DOUBLE_SLAB;

    static {
        DOUBLE_SLAB = REGISTRY.registerBlock("double_slab_block", new DoubleSlabBlock());
    }
}
