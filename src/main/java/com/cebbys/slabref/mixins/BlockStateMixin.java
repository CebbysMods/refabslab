package com.cebbys.slabref.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.cebbys.slabref.content.blocks.DoubleSlabBlock;
import com.cebbys.slabref.mixins.accessors.BlockSettingsAccessor;
import com.cebbys.slabref.mixins.accessors.SettingsAccessor;
import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AbstractBlock.Settings;
import net.minecraft.state.State;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class BlockStateMixin extends State<Block, BlockState> {

	protected BlockStateMixin(Block owner, ImmutableMap<Property<?>, Comparable<?>> entries,
			MapCodec<BlockState> codec) {
		super(owner, entries, codec);
	}

	@Shadow public abstract Block getBlock();
	@Shadow protected abstract BlockState asBlockState();
	
	@Inject(method = "getHardness", at = @At("HEAD"), cancellable = true)
	public void getHardness(BlockView world, BlockPos pos, CallbackInfoReturnable<Float> cir) {
		BlockState state = world.getBlockState(pos);
		if(state.getBlock() instanceof DoubleSlabBlock) {
			Block bt = Registry.BLOCK.get(state.get(DoubleSlabBlock.EXTEND));
			Block bb = Registry.BLOCK.get(state.get(DoubleSlabBlock.BASE));
			Settings st = ((BlockSettingsAccessor) bt).getBlockSettings();
			Settings sb = ((BlockSettingsAccessor) bb).getBlockSettings();
			float hardness = Math.max(((SettingsAccessor) st).getHardness(), ((SettingsAccessor) sb).getHardness());
			cir.setReturnValue(hardness);
			cir.cancel();
		}
	}

	@Inject(method = "isToolRequired", at = @At("HEAD"), cancellable = true)
    public void isToolRequired(CallbackInfoReturnable<Boolean> cir) {
       if(this.getBlock() instanceof DoubleSlabBlock) {
    	   BlockState state = this.asBlockState();
    	   BlockState bt = Registry.BLOCK.get(state.get(DoubleSlabBlock.EXTEND)).getDefaultState();
    	   BlockState bb = Registry.BLOCK.get(state.get(DoubleSlabBlock.BASE)).getDefaultState();
    	   cir.setReturnValue(bt.isToolRequired() || bb.isToolRequired());
    	   cir.cancel();
       }
    }
	

	@Inject(method = "getLuminance", at = @At("HEAD"), cancellable = true)
    public void getLuminance(CallbackInfoReturnable<Integer> cir) {
	       if(this.getBlock() instanceof DoubleSlabBlock) {
	    	   BlockState state = this.asBlockState();
	    	   BlockState bt = Registry.BLOCK.get(state.get(DoubleSlabBlock.EXTEND)).getDefaultState();
	    	   BlockState bb = Registry.BLOCK.get(state.get(DoubleSlabBlock.BASE)).getDefaultState();
	    	   int level = (int) Math.max(bt.getLuminance(), bb.getLuminance());
	    	   cir.setReturnValue(level);
	    	   cir.cancel();
	       }
    }
	
	
}
