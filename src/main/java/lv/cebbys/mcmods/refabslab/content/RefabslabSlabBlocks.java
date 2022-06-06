package lv.cebbys.mcmods.refabslab.content;

import lv.cebbys.mcmods.refabslab.content.blocks.DoubleSlabBlock;
import net.minecraft.block.Block;

import static lv.cebbys.mcmods.refabslab.Refabslab.REGISTRY;

public class RefabslabSlabBlocks {

    public static final Block DOUBLE_SLAB;
    public static final Block HAND_DOUBLE_SLAB;

    static {
        DOUBLE_SLAB = REGISTRY.registerBlock("double_slab_block", new DoubleSlabBlock(s -> s));
        HAND_DOUBLE_SLAB = REGISTRY.registerBlock("hand_double_slab_block", new DoubleSlabBlock(s -> s));
    }
}
