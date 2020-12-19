package com.cebbys.slabref.content.blocks;

import com.cebbys.celib.registrators.BlockRegistry;
import com.cebbys.slabref.Slabref;
import com.cebbys.slabref.content.blocks.properties.IdentifierProperty;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

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

	@Override
	public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
		if (!world.isClient()) {
		}
	}

	public void dropSlabs(WorldAccess world, BlockPos pos, BlockState state) {
		Item itemT = Registry.BLOCK.get(state.get(DoubleSlabBlock.TOP)).asItem();
		Item itemB = Registry.BLOCK.get(state.get(DoubleSlabBlock.BOTTOM)).asItem();

		ItemStack stackT = new ItemStack(itemT);
		ItemStack stackB = new ItemStack(itemB);

		double d = (double) (world.getRandom().nextFloat() * 0.5F) + 0.25D;
		double e = (double) (world.getRandom().nextFloat() * 0.5F) + 0.25D;
		double g = (double) (world.getRandom().nextFloat() * 0.5F) + 0.25D;
		ItemEntity itemEntityT = new ItemEntity((World) world, (double) pos.getX() + d, (double) pos.getY() + e,
				(double) pos.getZ() + g, stackT);
		itemEntityT.setToDefaultPickupDelay();
		world.spawnEntity(itemEntityT);

		d = (double) (world.getRandom().nextFloat() * 0.5F) + 0.25D;
		e = (double) (world.getRandom().nextFloat() * 0.5F) + 0.25D;
		g = (double) (world.getRandom().nextFloat() * 0.5F) + 0.25D;
		ItemEntity itemEntityB = new ItemEntity((World) world, (double) pos.getX() + d, (double) pos.getY() + e,
				(double) pos.getZ() + g, stackB);
		itemEntityB.setToDefaultPickupDelay();
		world.spawnEntity(itemEntityB);
	}

	static {
		TOP = IdentifierProperty.of("top");
		BOTTOM = IdentifierProperty.of("bottom");
		DEFAULT = new Identifier("oak_slab");
	}
}
