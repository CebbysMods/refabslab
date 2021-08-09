package lv.cebbys.mcmods.refabslab;

import lv.cebbys.mcmods.refabslab.content.models.providers.DoubleSlabModelProvider;
import lv.cebbys.mcmods.refabslab.content.models.providers.DoubleWallModelProvider;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;

public class RefabslabClient {

	protected static void initResources() {

	}
	
	protected static void initProviders() {
		ModelLoadingRegistry.INSTANCE.registerResourceProvider(rm -> new DoubleSlabModelProvider());
		ModelLoadingRegistry.INSTANCE.registerResourceProvider(rm -> new DoubleWallModelProvider());
	}
}
