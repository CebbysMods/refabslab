package lv.cebbys.mcmods.refabslab.locator;

import lv.cebbys.mcmods.refabslab.exception.ChunkLocatorException;
import net.minecraft.client.renderer.chunk.RenderChunkRegion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.Arrays;

public class RenderChunkRegionChunkLocator extends LevelReaderChunkLocator {
    private final Field levelField;

    public RenderChunkRegionChunkLocator() {
        var clazz = RenderChunkRegion.class;
        levelField = Arrays.stream(clazz.getDeclaredFields())
                .filter((var field) -> Level.class.isAssignableFrom(field.getType()))
                .findFirst().orElse(null);
        if (levelField == null) {
            throw new ChunkLocatorException(clazz, "Failed to locate Level field");
        }
        levelField.setAccessible(true);
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
