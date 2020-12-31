package com.cebbys.slabref;

import com.cebbys.celib.loggers.CelibLogger;
import com.cebbys.slabref.content.SlabrefSlabBlocks;
import com.cebbys.slabref.content.resources.SlabrefDataGenerator;
import com.cebbys.slabref.content.resources.SlabrefPackGenerator;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;

public class Slabref implements ModInitializer, ClientModInitializer {

	public static final String MODID;

	@Override
	public void onInitialize() {
        CelibLogger.log( MODID, "Loading slabref - Refactored Slabs Mod!" );
		new SlabrefSlabBlocks();
        CelibLogger.log( MODID, "Generating data pack!" );
		SlabrefDataGenerator.generateData();
        CelibLogger.log( MODID, "Data pack generation compleated!" );
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void onInitializeClient() {
		CelibLogger.log( MODID, "Generating resource pack!" );
		SlabrefPackGenerator.generatePack();
		CelibLogger.log( MODID, "Resource pack generation compleated!" );
	}

	static {
		MODID = "slabref";
	}
}
