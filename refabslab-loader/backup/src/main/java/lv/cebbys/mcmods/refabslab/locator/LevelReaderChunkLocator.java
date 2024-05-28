package lv.cebbys.mcmods.refabslab.locator;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LevelReaderChunkLocator extends ChunkLocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(LevelReaderChunkLocator.class);

    protected @Nullable LevelReader getLevelReader(Object chunkContainer) {
        if (chunkContainer instanceof LevelReader) {
            return (LevelReader) chunkContainer;
        }
        return null;
    }

    @Override
    public @Nullable ChunkAccess getChunkFromContainer(Object chunkContainer, BlockPos pos) {
        var level = getLevelReader(chunkContainer);
        if (level == null) {
            LOGGER.error("Failed to load Level object from '{}', returning ChunkAccess as 'null'", chunkContainer);
            return null;
        }
        return level.getChunk(pos);
    }
}
