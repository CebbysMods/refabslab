package com.cebbys.slabref.content;

import com.cebbys.slabref.content.blocks.DoubleSlabBlock;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;

public class SlabrefSlabBlocks {
	public static final DoubleSlabBlock DOUBLE_SLAB;

	static {
		DOUBLE_SLAB = new DoubleSlabBlock("double_slab", FabricBlockSettings.of(Material.STONE));
	}
}
