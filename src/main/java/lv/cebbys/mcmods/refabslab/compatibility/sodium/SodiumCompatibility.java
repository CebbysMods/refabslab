package lv.cebbys.mcmods.refabslab.compatibility.sodium;

import lv.cebbys.mcmods.refabslab.Refabslab;
import lv.cebbys.mcmods.refabslab.exception.CompatibilityException;
import lv.cebbys.mcmods.refabslab.utility.TransformUtils;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;

import static lv.cebbys.mcmods.refabslab.constant.RefabslabConstants.WORLD_SLICE_CLASS_NAME;

public class SodiumCompatibility {
    private static final Logger LOGGER = LoggerFactory.getLogger(Refabslab.class);
    public static @Nullable Field WORLD_FIELD;

    public static void load() {
        FabricLoader loader = FabricLoader.getInstance();
        if (loader.isModLoaded("sodium") && !loader.isModLoaded("indium")) {
            String issue = "Refabslab mod requires Indium mod if Sodium mod is loaded";
            String action = "Include Indium mod to make Refabslab mod work";
            LOGGER.error(issue);
            LOGGER.error(action);
            throw new CompatibilityException(issue + ". " + action);
        } else if (loader.isModLoaded("sodium") && loader.isModLoaded("indium")) {
            WORLD_FIELD = null;
            Class<?> worldSlice;
            try {
                worldSlice = TransformUtils.class.getClassLoader().loadClass(WORLD_SLICE_CLASS_NAME);
            } catch (Exception e) {
                if (FabricLoader.getInstance().isModLoaded("sodium")) {
                    throw new CompatibilityException(
                            "Failed to obtain access to Sodium " + WORLD_SLICE_CLASS_NAME + " class"
                    );
                }
                return;
            }
            try {
                WORLD_FIELD = worldSlice.getDeclaredField("world");
                WORLD_FIELD.setAccessible(true);
            } catch (Exception e) {
                if (FabricLoader.getInstance().isModLoaded("sodium")) {
                    throw new CompatibilityException(
                            "Failed to obtain access to Sodium " + WORLD_SLICE_CLASS_NAME + " World field"
                    );
                }
            }
        }
    }

    public static boolean isWorldSlice(@Nullable Object object) {
        if (object == null) return false;
        return WORLD_SLICE_CLASS_NAME.equals(object.getClass().getName());
    }

    public static @Nullable World toWorld(@Nullable Object slice) {
        if (!isWorldSlice(slice) || WORLD_FIELD == null) return null;
        try {
            return (World) WORLD_FIELD.get(slice);
        } catch (Exception e) {
            LOGGER.error("Failed to extract World field from Sodium " + WORLD_SLICE_CLASS_NAME);
            return null;
        }
    }
}
