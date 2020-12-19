package com.cebbys.slabref;

import com.cebbys.slabref.content.SlabrefSlabBlocks;
import com.cebbys.slabref.content.resources.SlabrefDataGenerator;
import com.cebbys.slabref.content.resources.SlabrefPackGenerator;
import com.cebbys.slabref.registries.TextureRegistry;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterials;

public class Slabref implements ModInitializer, ClientModInitializer {

	public static final String MODID;

	@Override
	public void onInitialize() {
		new SlabrefSlabBlocks();
		
		SlabrefDataGenerator.generateData();
	}

	static {
		MODID = "slabref";
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void onInitializeClient() {
		
		TextureRegistry.initializeTexturePairs();
		
		SlabrefPackGenerator.generatePack();
	}
}
