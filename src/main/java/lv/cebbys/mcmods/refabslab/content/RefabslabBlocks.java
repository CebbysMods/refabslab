package lv.cebbys.mcmods.refabslab.content;

import lv.cebbys.mcmods.celib.utilities.CelibRegistryTypes;
import lv.cebbys.mcmods.refabslab.content.block.DoubleSlabBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import static lv.cebbys.mcmods.refabslab.Refabslab.REGISTRY;

public class RefabslabBlocks {
    public static final Block DOUBLE_SLAB;

    static {
        DOUBLE_SLAB = REGISTRY.registerBlock("double_slab_block", new DoubleSlabBlock());
    }
}
