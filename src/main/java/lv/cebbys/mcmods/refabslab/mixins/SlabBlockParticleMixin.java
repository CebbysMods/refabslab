package lv.cebbys.mcmods.refabslab.mixins;

import lv.cebbys.mcmods.refabslab.content.blocks.DoubleSlabBlock;
import lv.cebbys.mcmods.refabslab.content.entities.DoubleSlabEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.particle.BlockDustParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

/**
 * Special thanks Discord Fabric: Haven King for idea
 *
 * @author CebbyS
 */

@Mixin(ParticleManager.class)
public abstract class SlabBlockParticleMixin {

    @Shadow
    private Random random;
	@Shadow
	protected ClientWorld world;

	@Shadow
	public abstract void addParticle(Particle p);
	@Shadow
	public abstract void addBlockBreakParticles(BlockPos pos, BlockState state);

    @Inject(method = "addBlockBreakParticles", at = @At("HEAD"), cancellable = true)
    public void addBlockBreakParticles(BlockPos pos, BlockState state, CallbackInfo ci) {
        if (state.getBlock() instanceof DoubleSlabBlock &&
				this.world.getBlockEntity(pos) instanceof DoubleSlabEntity entity) {
            BlockState slabTop = Registry.BLOCK.get(entity.getTop()).getDefaultState();
            BlockState slabBottom = Registry.BLOCK.get(entity.getBottom()).getDefaultState();
			this.addBlockBreakParticles(pos, slabTop);
			this.addBlockBreakParticles(pos, slabBottom);
            ci.cancel();
        }
    }


    @Inject(method = "addBlockBreakingParticles", at = @At("HEAD"), cancellable = true)
    public void addBlockBreakingParticles(BlockPos pos, Direction dir, CallbackInfo ci) {

        if (this.world.getBlockState(pos).getBlock() instanceof DoubleSlabBlock &&
                world.getBlockEntity(pos) instanceof DoubleSlabEntity entity) {

            final double c1 = 0.10000000149011612D;
            final double c2 = c1 * 2.0D;
            final BlockState topState = Registry.BLOCK.get(entity.getTop()).getDefaultState();
            final BlockState bottomState = Registry.BLOCK.get(entity.getBottom()).getDefaultState();

            BlockState blockState = this.world.getBlockState(pos);
            if (blockState.getRenderType() != BlockRenderType.INVISIBLE) {
                int i = pos.getX();
                int j = pos.getY();
                int k = pos.getZ();
                Box box = blockState.getOutlineShape(this.world, pos).getBoundingBox();
                double x = (double) i + this.random.nextDouble() * (box.maxX - box.minX - c2) + c1 + box.minX;
                double y = (double) j + this.random.nextDouble() * (box.maxY - box.minY - c2) + c1 + box.minY;
                double z = (double) k + this.random.nextDouble() * (box.maxZ - box.minZ - c2) + c1 + box.minZ;

                y = dir == Direction.DOWN ? j + box.minY - c1 : dir == Direction.UP ? j + box.maxY + c1 : y;
                z = dir == Direction.NORTH ? k + box.minZ - c1 : dir == Direction.SOUTH ? k + box.maxZ + c1 : z;
                x = dir == Direction.WEST ? i + box.minX - c1 : dir == Direction.EAST ? i + box.maxX + c1 : x;

                BlockState slabState;
                if (y > j + 0.5D) {
                    slabState = topState;
                } else {
                    slabState = bottomState;
                }
                double v = 0;
                this.addParticle((new BlockDustParticle(this.world, x, y, z, v, v, v, slabState, pos)).move(0.2F).scale(0.6F));
            }
            ci.cancel();
        }
    }
}
