package lv.cebbys.mcmods.refabslab.loader.fabric;

import lombok.extern.slf4j.Slf4j;
import lv.cebbys.mcmods.mvl.MinecraftVersionUtility;
import lv.cebbys.mcmods.refabslab.core.RefabslabClient;
import lv.cebbys.mcmods.refabslab.loader.RefabslabBridgeLoader;
import net.fabricmc.api.ClientModInitializer;

@Slf4j
public class RefabslabFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        /*
        1. Load refabslab plugins
        2. Identify minecraft version
        3. Initialize refabslab context from plugins using minecraft version
        3.1. This means -> Different versions may implement different plugins
             in case specific plugin does not have specific component in that version
             fall back to earlier minecraft version and load component from there
         */
        var minecraftVersion = MinecraftVersionUtility.getVersion();
        RefabslabBridgeLoader.initializeContext(minecraftVersion);

        try {
            var initializer = new RefabslabClient();
            initializer.onInitializeClient();
        } catch (Throwable t) {
            log.error("Failed to initialize RefabslabClient", t);
            throw new IllegalStateException("Failure initializing RefabslabClient", t);
        }
    }
}
