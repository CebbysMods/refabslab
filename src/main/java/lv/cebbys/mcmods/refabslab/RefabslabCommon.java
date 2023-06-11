package lv.cebbys.mcmods.refabslab;

import lv.cebbys.mcmods.celib.mod.utility.CelibRegistrator;
import lv.cebbys.mcmods.refabslab.compatibility.minegate.MinegateCompatibility;
import lv.cebbys.mcmods.refabslab.content.RefabslabBlocks;
import lv.cebbys.mcmods.refabslab.locator.ChunkLocator;
import lv.cebbys.mcmods.refabslab.locator.LevelReaderChunkLocator;
import lv.cebbys.mcmods.refabslab.resource.ResourceEntrypoint;
import net.fabricmc.api.ModInitializer;
import net.minecraft.world.level.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RefabslabCommon implements ModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RefabslabCommon.class);
    public static final String MODID;
    public static final CelibRegistrator REGISTRY;

    @Override
    public void onInitialize() {
        if (RefabslabMixinPlugin.hasInitializationFailure()) {
            var failure = RefabslabMixinPlugin.getInitializationFailure();
            LOGGER.error("Stopping the 'refabslab' mod initialization because of mixin failure", failure);
            throw new RuntimeException("Failed to initialize refabslab mod", failure);
        }

        LOGGER.info("Initializing Refabslab common side");
        new RefabslabBlocks();
        ResourceEntrypoint.registerData();

        MinegateCompatibility.loadCommon();
    }

    static {
        MODID = "refabslab";
        REGISTRY = new CelibRegistrator(MODID);

        ChunkLocator.register(Level.class, new LevelReaderChunkLocator());
    }
}
