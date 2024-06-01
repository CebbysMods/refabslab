package lv.cebbys.mcmods.refabslab.loader.fabric;

import lombok.extern.slf4j.Slf4j;
import lv.cebbys.mcmods.mvl.MinecraftVersionUtility;
import lv.cebbys.mcmods.refabslab.core.RefabslabCommon;
import lv.cebbys.mcmods.refabslab.loader.RefabslabBridgeLoader;
import net.fabricmc.api.ModInitializer;

@Slf4j
public class RefabslabFabricCommon implements ModInitializer {
    @Override
    public void onInitialize() {
        try {
            var initializer = new RefabslabCommon();
//            initializer.onInitializeCommon();
            var minecraftVersion = MinecraftVersionUtility.getVersion();
            RefabslabBridgeLoader.initializeContext(minecraftVersion);
        } catch (Throwable t) {
            log.error("Failed to initialize RefabslabCommon", t);
            throw new IllegalStateException("Failure initializing RefabslabCommon", t);
        }
    }
}
