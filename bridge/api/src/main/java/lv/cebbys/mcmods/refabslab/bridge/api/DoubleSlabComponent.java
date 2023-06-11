package lv.cebbys.mcmods.refabslab.bridge.api;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public interface DoubleSlabComponent {
    void pushSlabCombination(BlockPos pos, ResourceLocation topId, ResourceLocation bottomId);
}
