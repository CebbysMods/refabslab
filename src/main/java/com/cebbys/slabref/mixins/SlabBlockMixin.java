package com.cebbys.slabref.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.cebbys.slabref.content.SlabrefSlabBlocks;
import com.cebbys.slabref.content.blocks.DoubleSlabBlock;
import com.cebbys.slabref.registries.SlabRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

@Mixin(SlabBlock.class)
public abstract class SlabBlockMixin extends Block {

	public SlabBlockMixin(Settings settings) {
		super(settings);
	}

	@Inject(method = "getPlacementState", at = @At("HEAD"), cancellable = true)
	public void getPlacementState(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cr) {
		BlockPos pos = ctx.getBlockPos();
		World world = ctx.getWorld();
		BlockState state = world.getBlockState(pos);
		Block block0 = state.getBlock();
		Block block1 = ((BlockItem) ctx.getStack().getItem()).getBlock();
		if(SlabRegistry.contains(block0, block1) && block0 != block1) {
			Identifier id0 = Registry.BLOCK.getId(block0);
			Identifier id1 = Registry.BLOCK.getId(block1);
			BlockState doubleState = null;
			SlabType type0 = state.get(SlabBlock.TYPE);
			if (type0 == SlabType.TOP) {
				doubleState = SlabrefSlabBlocks.DOUBLE_SLAB.getDefaultState();
				doubleState = doubleState.with(DoubleSlabBlock.BASE, id1);
				doubleState = doubleState.with(DoubleSlabBlock.EXTEND, id0);
			} else if (type0 == SlabType.BOTTOM) {
				doubleState = SlabrefSlabBlocks.DOUBLE_SLAB.getDefaultState();
				doubleState = doubleState.with(DoubleSlabBlock.BASE, id0);
				doubleState = doubleState.with(DoubleSlabBlock.EXTEND, id1);
			}
			if (doubleState != null) {
				cr.cancel();
				cr.setReturnValue(doubleState);
			}

		}
	}

	@Inject(method = "canReplace", at = @At("HEAD"), cancellable = true)
	public void canReplace(BlockState state, ItemPlacementContext ctx, CallbackInfoReturnable<Boolean> cr) {
		SlabType type = state.get(SlabBlock.TYPE);
		if (type != SlabType.DOUBLE) {
			ItemStack itemStack = ctx.getStack();
			if (itemStack.getItem() instanceof BlockItem) {
				Block block = ((BlockItem) itemStack.getItem()).getBlock();
				if (block != state.getBlock() && SlabRegistry.contains(state.getBlock(), block)) {
					Direction placementFace = ctx.getSide();

					Direction s = ctx.getSide();
					Vec3d hit = ctx.getHitPos();
					BlockPos pos = ctx.getBlockPos();
					cr.setReturnValue(pos.getX() == hit.getX() && s == Direction.EAST
							|| pos.getX() + 1 == hit.getX() && s == Direction.WEST
							|| pos.getY() == hit.getY() && s == Direction.UP
							|| pos.getY() + 1 == hit.getY() && s == Direction.DOWN
							|| pos.getZ() == hit.getZ() && s == Direction.SOUTH
							|| pos.getZ() + 1 == hit.getZ() && s == Direction.NORTH
							|| type == SlabType.TOP && placementFace == Direction.DOWN
							|| type == SlabType.BOTTOM && placementFace == Direction.UP);
					cr.cancel();
				}
			}
		}
	}
}
