package com.cebbys.slabref;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;

public class Slabref implements ModInitializer, ClientModInitializer {

	public static final String MODID;

	@Override
	public void onInitialize() {
		SlabrefServer.initContent();
		SlabrefServer.initData();
		SlabrefServer.initEvents();
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void onInitializeClient() {
		SlabrefClient.initResources();
		SlabrefClient.initProviders();
	}

	static {
		MODID = "slabref";
	}
}
