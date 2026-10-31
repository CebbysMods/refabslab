package lv.cebbys.mcmods.refabslab.client;

import lv.cebbys.mcmods.modularity.api.block.BlockStateRef;
import lv.cebbys.mcmods.modularity.api.client.HitParticles;
import lv.cebbys.mcmods.modularity.api.world.BlockView;
import lv.cebbys.mcmods.modularity.api.world.Direction;
import lv.cebbys.mcmods.modularity.api.world.Pos;
import lv.cebbys.mcmods.modularity.api.world.Vec3;
import lv.cebbys.mcmods.refabslab.RefabslabMod;
import lv.cebbys.mcmods.refabslab.content.DoubleSlab;

/** Mining particles use the texture of the half that is being hit. */
public final class DoubleSlabParticles implements HitParticles {

    @Override
    public BlockStateRef particleState(BlockView view, Pos pos, BlockStateRef state, Direction face, Vec3 particle) {
        DoubleSlab slab = DoubleSlab.orDefault(view.data(pos, RefabslabMod.DOUBLE_SLAB_DATA));
        return particle.y() > pos.y() + 0.5D ? slab.topState() : slab.bottomState();
    }
}
