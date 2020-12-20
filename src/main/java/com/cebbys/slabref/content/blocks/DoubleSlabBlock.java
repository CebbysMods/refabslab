package com.cebbys.slabref.content.blocks;

import com.cebbys.celib.registrators.BlockRegistry;
import com.cebbys.slabref.Slabref;
import com.cebbys.slabref.content.blocks.properties.IdentifierProperty;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.util.Identifier;

public final class DoubleSlabBlock extends Block {

	public static final IdentifierProperty TOP;
	public static final IdentifierProperty BOTTOM;
	public static final Identifier DEFAULT;

	public DoubleSlabBlock(String blockName, Settings settings) {
		super(settings);
		BlockState state = this.getDefaultState().with(TOP, DEFAULT).with(BOTTOM, DEFAULT);
		this.setDefaultState(state);
		BlockRegistry.registerBlock(this, Slabref.MODID, blockName);
	}

	@Override
	protected void appendProperties(Builder<Block, BlockState> builder) {
		builder.add(TOP, BOTTOM);
	}

	static {
		TOP = IdentifierProperty.of("top");
		BOTTOM = IdentifierProperty.of("bottom");
		DEFAULT = new Identifier("oak_slab");
	}
}
