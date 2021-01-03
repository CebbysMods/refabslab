package com.cebbys.slabref.mixins;

import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.cebbys.slabref.content.blocks.DoubleSlabBlock;
import com.cebbys.slabref.content.entities.DoubleSlabEntity;

import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.particle.BlockDustParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;

/**
 * 
 * Special thanks Discord Fabric: Haven King for idea
 * 
 * @author CebbyS
 *
 */

@Mixin(ParticleManager.class)
public abstract class SlabBlockParticleMixin {

	@Shadow private Random random;
	
	@Shadow
	public abstract void addParticle(Particle particle);

	@Shadow
	protected ClientWorld world;

	@Inject(method = "addBlockBreakParticles", at = @At("HEAD"), cancellable = true)
	public void addBlockBreakParticles(BlockPos pos, BlockState state, CallbackInfo ci) {
		if (state.getBlock() instanceof DoubleSlabBlock) {
			DoubleSlabEntity entity = (DoubleSlabEntity) this.world.getBlockEntity(pos);
			BlockState slabT = Registry.BLOCK.get(entity.getExtend()).getDefaultState();
			BlockState slabB = Registry.BLOCK.get(entity.getBase()).getDefaultState();
			VoxelShape voxelShape = state.getOutlineShape(this.world, pos);
			voxelShape.forEachBox((dx, e, f, g, h, i) -> {
				double j = Math.min(1.0D, g - dx);
				double k = Math.min(1.0D, h - e);
				double l = Math.min(1.0D, i - f);
				int m = Math.max(2, MathHelper.ceil(j / 0.25D));
				int n = Math.max(2, MathHelper.ceil(k / 0.25D));
				int o = Math.max(2, MathHelper.ceil(l / 0.25D));

				for (int p = 0; p < m; ++p) {
					for (int q = 0; q < n; ++q) {
						for (int r = 0; r < o; ++r) {
							double s = ((double) p + 0.5D) / (double) m;
							double t = ((double) q + 0.5D) / (double) n;
							double u = ((double) r + 0.5D) / (double) o;
							double v = s * j + dx;
							double w = t * k + e;
							double x = u * l + f;
							if (q > 1) {
								this.addParticle((new BlockDustParticle(this.world, (double) pos.getX() + v,
										(double) pos.getY() + w, (double) pos.getZ() + x, s - 0.5D, t - 0.5D, u - 0.5D,
										slabT)).setBlockPos(pos));
							} else {
								this.addParticle((new BlockDustParticle(this.world, (double) pos.getX() + v,
										(double) pos.getY() + w, (double) pos.getZ() + x, s - 0.5D, t - 0.5D, u - 0.5D,
										slabB)).setBlockPos(pos));
							}
						}
					}
				}

			});
			ci.cancel();
		}
	}
	
	@Inject(method = "addBlockBreakingParticles", at = @At("HEAD"), cancellable = true)
	public void addBlockBreakingParticles(BlockPos pos, Direction direction, CallbackInfo ci) {
		BlockState state = this.world.getBlockState(pos);
		if (state.getBlock() instanceof DoubleSlabBlock) {
			if (state.getRenderType() != BlockRenderType.INVISIBLE) {
				int i = pos.getX();
				int j = pos.getY();
				int k = pos.getZ();
				Box box = state.getOutlineShape(this.world, pos).getBoundingBox();
				double d = (double) i
						+ this.random.nextDouble() * (box.maxX - box.minX - 0.20000000298023224D)
						+ 0.10000000149011612D + box.minX;
				double e = (double) j
						+ this.random.nextDouble() * (box.maxY - box.minY - 0.20000000298023224D)
						+ 0.10000000149011612D + box.minY;
				double g = (double) k
						+ this.random.nextDouble() * (box.maxZ - box.minZ - 0.20000000298023224D)
						+ 0.10000000149011612D + box.minZ;
				if (direction == Direction.DOWN) {
					e = (double) j + box.minY - 0.10000000149011612D;
				}

				if (direction == Direction.UP) {
					e = (double) j + box.maxY + 0.10000000149011612D;
				}

				if (direction == Direction.NORTH) {
					g = (double) k + box.minZ - 0.10000000149011612D;
				}

				if (direction == Direction.SOUTH) {
					g = (double) k + box.maxZ + 0.10000000149011612D;
				}

				if (direction == Direction.WEST) {
					d = (double) i + box.minX - 0.10000000149011612D;
				}

				if (direction == Direction.EAST) {
					d = (double) i + box.maxX + 0.10000000149011612D;
				}
				DoubleSlabEntity entity = (DoubleSlabEntity) this.world.getBlockEntity(pos);
				BlockState slabT = Registry.BLOCK.get(entity.getExtend()).getDefaultState();
				BlockState slabB = Registry.BLOCK.get(entity.getBase()).getDefaultState();
				if(this.random.nextInt(2) == 1) {
					this.addParticle((new BlockDustParticle(this.world, d, e, g, 0.0D, 0.0D, 0.0D, slabT))
							.setBlockPos(pos).move(0.2F).scale(0.6F));
				} else {
					this.addParticle((new BlockDustParticle(this.world, d, e, g, 0.0D, 0.0D, 0.0D, slabB))
							.setBlockPos(pos).move(0.2F).scale(0.6F));
				}
			}
			ci.cancel();
		}
	}
}
