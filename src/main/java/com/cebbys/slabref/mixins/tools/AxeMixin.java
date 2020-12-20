package com.cebbys.slabref.mixins.tools;

import java.util.Set;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.cebbys.slabref.content.blocks.DoubleSlabBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.registry.Registry;

@Mixin(AxeItem.class)
public abstract class AxeMixin extends MiningToolItem {

	@Inject(method = "getMiningSpeedMultiplier", at = @At("HEAD"), cancellable = true)
	public void getMiningSpeedMultiplier(ItemStack stack, BlockState state, CallbackInfoReturnable<Float> cir) {
		if(state.getBlock() instanceof DoubleSlabBlock) {
			BlockState t = Registry.BLOCK.get(state.get(DoubleSlabBlock.TOP)).getDefaultState();
			BlockState b = Registry.BLOCK.get(state.get(DoubleSlabBlock.BOTTOM)).getDefaultState();
			cir.setReturnValue((this.getMiningSpeedMultiplier(stack, t) + this.getMiningSpeedMultiplier(stack, b)) / 2.0F);
			cir.cancel();
		}
	}

	protected AxeMixin(float attackDamage, float attackSpeed, ToolMaterial material, Set<Block> effectiveBlocks,
			Settings settings) {
		super(attackDamage, attackSpeed, material, effectiveBlocks, settings);
	}
}
