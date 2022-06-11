package lv.cebbys.mcmods.refabslab;

import lv.cebbys.mcmods.refabslab.content.model.provider.DoubleSlabModelProvider;
import lv.cebbys.mcmods.refabslab.resource.ResourceEntrypoint;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;

public class RefabslabClient {

    protected static void initResources() {
        ResourceEntrypoint.registerAssets();
    }

    protected static void initProviders() {
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(rm -> new DoubleSlabModelProvider());
    }
}
