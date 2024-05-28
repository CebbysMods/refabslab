package lv.cebbys.mcmods.refabslab.compatibility.sodium;

import lv.cebbys.mcmods.refabslab.RefabslabCommon;
import lv.cebbys.mcmods.refabslab.locator.ChunkLocator;
import lv.cebbys.mcmods.refabslab.exception.CompatibilityException;
import lv.cebbys.mcmods.refabslab.utility.TransformUtils;
import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

import static lv.cebbys.mcmods.refabslab.constant.RefabslabConstants.WORLD_SLICE_CLASS_NAME;

public class SodiumCompatibility {
    private static final Logger LOGGER = LoggerFactory.getLogger(RefabslabCommon.class);
    public static @Nullable Field WORLD_FIELD;

    public static void load() {
        FabricLoader loader = FabricLoader.getInstance();
        if (!loader.isModLoaded("sodium")) {
            return;
        }
        if (!loader.isModLoaded("indium")) {
            var message = "Refabslab mod requires Indium mod if Sodium mod is loaded. Include Indium mod to make Refabslab mod work";
            LOGGER.error(message);
            throw new CompatibilityException(message);
        }
        Class<?> worldSlice = loadWorldSliceClass();
        ChunkLocator.register(worldSlice, new WorldSliceChunkLocator(worldSlice));
    }

    private static Class<?> loadWorldSliceClass() {
        try {
            return TransformUtils.class.getClassLoader().loadClass(WORLD_SLICE_CLASS_NAME);
        } catch (Exception e) {
            throw new CompatibilityException(
                    "Failed to obtain access to Sodium " + WORLD_SLICE_CLASS_NAME + " class"
            );
        }
    }
}
