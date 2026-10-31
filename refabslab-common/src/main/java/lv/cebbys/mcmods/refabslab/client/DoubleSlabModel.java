package lv.cebbys.mcmods.refabslab.client;

import lv.cebbys.mcmods.modularity.api.block.BlockStateRef;
import lv.cebbys.mcmods.modularity.api.client.DynamicModel;
import lv.cebbys.mcmods.modularity.api.client.ModelOutput;
import lv.cebbys.mcmods.modularity.api.core.Id;
import lv.cebbys.mcmods.modularity.api.world.BlockView;
import lv.cebbys.mcmods.modularity.api.world.Pos;
import lv.cebbys.mcmods.refabslab.RefabslabMod;
import lv.cebbys.mcmods.refabslab.content.DoubleSlab;

/** Renders a double slab as its bottom slab plus its top slab, using their own models. */
public final class DoubleSlabModel implements DynamicModel {

    @Override
    public void emit(BlockView view, Pos pos, BlockStateRef state, ModelOutput out) {
        DoubleSlab slab = DoubleSlab.orDefault(view.data(pos, RefabslabMod.DOUBLE_SLAB_DATA));
        out.blockState(slab.bottomState());
        out.blockState(slab.topState());
    }

    @Override
    public Id particleTexture() {
        return Id.minecraft("block/cobblestone");
    }
}
