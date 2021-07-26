package com.cebbys.slabref;

import com.cebbys.slabref.content.models.DoubleSlabModelProvider;
import com.cebbys.slabref.content.resources.SlabrefPackGenerator;

import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;

public class SlabrefClient {

	protected static void initResources() {
		SlabrefPackGenerator.generatePack();
	}
	
	protected static void initProviders() {
		ModelLoadingRegistry.INSTANCE.registerResourceProvider(rm -> new DoubleSlabModelProvider());
	}
}
