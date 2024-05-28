package lv.cebbys.mcmods.refabslab.bridge.api;

import net.minecraft.world.level.chunk.ChunkAccess;

public interface DoubleSlabComponentLocator {
    DoubleSlabComponent get(ChunkAccess chunk);
}
