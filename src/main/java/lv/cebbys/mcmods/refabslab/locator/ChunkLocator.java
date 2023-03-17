package lv.cebbys.mcmods.refabslab.locator;

import lv.cebbys.mcmods.refabslab.content.RefabslabComponents;
import lv.cebbys.mcmods.refabslab.content.component.DoubleSlabComponent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public abstract class ChunkLocator {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChunkLocator.class);
    private static final Map<Class<?>, ChunkLocator> LOCATORS;
    private static final ChunkLocator DEFAULT;

    public static void register(Class<?> clazz, ChunkLocator locator) {
        LOGGER.info("Registering new ChunkAccess locator with id '{}' and instance '{}'", clazz, locator);
        if (LOCATORS.containsKey(clazz)) {
            LOGGER.error("Cannot register a Chunk object locator for classname '{}' as locator already exists", clazz);
            return;
        }
        LOCATORS.put(clazz, locator);
    }

    public static ChunkLocator get(Object object) {
        if (object == null) {
            return DEFAULT;
        }
        return get(object.getClass());
    }

    public static ChunkLocator get(Class<?> clazz) {
        return LOCATORS.keySet().stream()
                .filter((var key) -> key == clazz || key.isAssignableFrom(clazz))
                .map(LOCATORS::get).findFirst().orElse(DEFAULT);
    }

    @Nullable
    public static ChunkAccess getChunk(Object container, BlockPos pos) {
        return get(container).getChunkFromContainer(container, pos);
    }

    @Nullable
    public static DoubleSlabComponent getComponent(Object container, BlockPos pos) {
        var chunk = getChunk(container, pos);
        if (chunk == null) {
            return null;
        }
        return RefabslabComponents.DOUBLE_SLAB_QUEUE.getNullable(chunk);
    }

    @Nullable
    public abstract ChunkAccess getChunkFromContainer(Object chunkContainer, BlockPos pos);

    static {
        LOCATORS = new HashMap<>();
        DEFAULT = new ChunkLocator() {
            @Override
            public ChunkAccess getChunkFromContainer(Object chunkContainer, BlockPos pos) {
                var classname = "null";
                if (chunkContainer != null) {
                    classname = chunkContainer.getClass().getName();
                }
                LOGGER.error(
                        "Failed to transform {} to DoubleSlabComponent. {}", classname,
                        "If you see this exception, please notify the developer of Refabslab mod"
                );
                return null;
            }
        };
    }
}
