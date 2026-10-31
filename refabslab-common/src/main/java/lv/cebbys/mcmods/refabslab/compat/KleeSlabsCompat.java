package lv.cebbys.mcmods.refabslab.compat;

import lv.cebbys.mcmods.modularity.api.block.BlockStateRef;
import lv.cebbys.mcmods.modularity.api.block.property.SlabType;
import lv.cebbys.mcmods.modularity.api.world.BlockView;
import lv.cebbys.mcmods.modularity.api.world.Pos;
import lv.cebbys.mcmods.refabslab.RefabslabMod;
import lv.cebbys.mcmods.refabslab.content.DoubleSlab;

/**
 * Loader independent part of the KleeSlabs integration. KleeSlabs breaks only the half of a double
 * slab the player looks at: it asks which single slab is the broken half (dropped) and which is the
 * remaining half (left in the world). For Refabslab double slabs both come from the block data.
 *
 * <p>The loader specific adapter implementing KleeSlabs' converter interface lives in each platform
 * module and only delegates here.
 */
public final class KleeSlabsCompat {
    private KleeSlabsCompat() {
    }

    public static boolean isDoubleSlab(BlockStateRef state) {
        return state.is(RefabslabMod.doubleSlab().get());
    }

    /** The single slab state for {@code half} of the double slab at {@code pos}. */
    public static BlockStateRef singleSlab(BlockView view, Pos pos, SlabType half) {
        return DoubleSlab.orDefault(view.data(pos, RefabslabMod.DOUBLE_SLAB_DATA)).half(half);
    }
}
