package lv.cebbys.mcmods.refabslab.bridge.constant;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class RefabslabConstants {
    public static final String MODID = "refabslab";
    public static final ResourceLocation DOUBLE_SLAB_BLOCK_KEY = new ResourceLocation(MODID, "double_slab_block");
    public static IntegerProperty LIGHT_LEVEL = IntegerProperty.create("light_level", 0, 15);
}
