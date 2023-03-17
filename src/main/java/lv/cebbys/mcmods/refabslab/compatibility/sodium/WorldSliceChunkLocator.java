package lv.cebbys.mcmods.refabslab.compatibility.sodium;

import lv.cebbys.mcmods.refabslab.exception.ChunkLocatorException;
import lv.cebbys.mcmods.refabslab.locator.LevelReaderChunkLocator;
import net.minecraft.world.level.LevelReader;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

public class WorldSliceChunkLocator extends LevelReaderChunkLocator {
    private final Field levelField;

    public WorldSliceChunkLocator(Class<?> worldSliceClass) {
        try {
            levelField = worldSliceClass.getDeclaredField("world");
            levelField.setAccessible(true);
        } catch (Exception e) {
            throw new ChunkLocatorException(worldSliceClass, e);
        }
    }

    @Override
    protected @Nullable LevelReader getLevelReader(Object chunkContainer) {
        try {
            return (LevelReader) levelField.get(chunkContainer);
        } catch (Exception e) {
            return null;
        }
    }
}
