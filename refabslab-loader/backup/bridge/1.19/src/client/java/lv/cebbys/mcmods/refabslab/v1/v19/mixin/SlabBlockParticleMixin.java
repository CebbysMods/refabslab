package lv.cebbys.mcmods.refabslab.v1.v19.mixin;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Special thanks Discord Fabric: Haven King for idea
 *
 * @author CebbyS
 */

@Mixin(ParticleEngine.class)
public abstract class SlabBlockParticleMixin {

    @Shadow
    protected ClientLevel level;

    @Final
    @Shadow
    private RandomSource random;

    @Shadow
    public abstract void add(Particle p);

    @Shadow
    public abstract void destroy(BlockPos pos, BlockState state);

    @Inject(method = "destroy", at = @At("HEAD"), cancellable = true)
    public void destroy(BlockPos pos, BlockState state, CallbackInfo ci) {
//        var component = ChunkLocator.getComponent(level, pos);
//        if (component == null) {
//            return;
//        }
//        var combination = component.getSlabBlockStateCombination(pos);
//        var base = combination.getValue();
//        var extend = combination.getKey();
//        this.destroy(pos, base);
//        this.destroy(pos, extend);
//        ci.cancel();
    }

    @Inject(method = "crack", at = @At("HEAD"), cancellable = true)
    public void crack(BlockPos pos, Direction dir, CallbackInfo ci) {
//        var component = ChunkLocator.getComponent(level, pos);
//        if (component == null) {
//            return;
//        }
//        var combination = component.getSlabBlockStateCombination(pos);
//        var top = combination.getKey();
//        var bottom = combination.getValue();
//
//        final double c1 = 0.10000000149011612D;
//        final double c2 = c1 * 2.0D;
//
//        int i = pos.getX();
//        int j = pos.getY();
//        int k = pos.getZ();
//        AABB box = Shapes.block().bounds();
//        double x = (double) i + this.random.nextDouble() * (box.maxX - box.minX - c2) + c1 + box.minX;
//        double y = (double) j + this.random.nextDouble() * (box.maxY - box.minY - c2) + c1 + box.minY;
//        double z = (double) k + this.random.nextDouble() * (box.maxZ - box.minZ - c2) + c1 + box.minZ;
//
//        y = dir == Direction.DOWN ? j + box.minY - c1 : dir == Direction.UP ? j + box.maxY + c1 : y;
//        z = dir == Direction.NORTH ? k + box.minZ - c1 : dir == Direction.SOUTH ? k + box.maxZ + c1 : z;
//        x = dir == Direction.WEST ? i + box.minX - c1 : dir == Direction.EAST ? i + box.maxX + c1 : x;
//
//        BlockState slabState;
//        if (y > j + 0.5D) {
//            slabState = top;
//        } else {
//            slabState = bottom;
//        }
//        double v = 0;
//        this.add((new TerrainParticle(this.level, x, y, z, v, v, v, slabState, pos)).setPower(0.2F).scale(0.6F));
//        ci.cancel();
    }
}
