package com.cebbys.slabref.content.blocks;

import com.cebbys.celib.registrators.BlockRegistry;
import com.cebbys.slabref.Slabref;
import com.cebbys.slabref.content.entities.DoubleSlabEntity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.world.BlockView;

public final class DoubleSlabBlock extends Block implements BlockEntityProvider {

	public DoubleSlabBlock(String blockName, Settings settings) {
		super(settings);
		BlockRegistry.registerBlock(this, Slabref.MODID, blockName);
	}

	@Override
	public BlockEntity createBlockEntity(BlockView world) {
		return new DoubleSlabEntity();
	}
}
