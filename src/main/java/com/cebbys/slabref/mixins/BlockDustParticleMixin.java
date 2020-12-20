package com.cebbys.slabref.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.cebbys.slabref.content.blocks.DoubleSlabBlock;

import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.BlockDustParticle;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.registry.Registry;

@Mixin(BlockDustParticle.class)
public abstract class BlockDustParticleMixin extends SpriteBillboardParticle {
	
	@Inject(at = @At("TAIL"), method = "<init>(Lnet/minecraft/client/world/ClientWorld;DDDDDDLnet/minecraft/block/BlockState;)V")
	private void init(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, BlockState blockState, CallbackInfo info) {
		if(blockState.getBlock() instanceof DoubleSlabBlock) {
			BlockState top = Registry.BLOCK.get(blockState.get(DoubleSlabBlock.TOP)).getDefaultState();
			this.setSprite(MinecraftClient.getInstance().getBlockRenderManager().getModels().getSprite(top));
		}
	}

	protected BlockDustParticleMixin(ClientWorld clientWorld, double d, double e, double f, double g, double h,
			double i) {
		super(clientWorld, d, e, f, g, h, i);
	}
}
