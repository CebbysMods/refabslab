package com.cebbys.slabref.content.blocks;

import com.cebbys.celib.registrators.BlockRegistry;
import com.cebbys.slabref.Slabref;
import com.cebbys.slabref.content.blocks.properties.IdentifierProperty;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.util.Identifier;

public final class DoubleSlabBlock extends Block {

	public static final IdentifierProperty BASE;
	public static final IdentifierProperty EXTEND;
	public static final Identifier DEFAULT;

	public DoubleSlabBlock(String blockName, Settings settings) {
		super(settings);
		this.setDefaultState(this.getDefaultState().with(BASE, DEFAULT).with(EXTEND, DEFAULT));
		BlockRegistry.registerBlock(this, Slabref.MODID, blockName);
	}

	@Override
	protected void appendProperties(Builder<Block, BlockState> builder) {
		builder.add(BASE, EXTEND);
	}
	
	static {
		BASE = IdentifierProperty.of("base");
		EXTEND = IdentifierProperty.of("extend");
		DEFAULT = new Identifier("oak_slab");
	}
}
