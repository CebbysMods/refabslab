package lv.cebbys.mcmods.refabslab.fabric.compat;

import lv.cebbys.mcmods.modularity.fabric.Wrap;
import lv.cebbys.mcmods.refabslab.compat.KleeSlabsCompat;
import net.blay09.mods.kleeslabs.converter.HorizontalSlabConverter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;

/**
 * KleeSlabs converter for Refabslab double slabs, referenced from
 * {@code data/refabslab/kleeslabs_compat/double_slab.json}. KleeSlabs only loads this class when it
 * is installed, so KleeSlabs stays an optional dependency.
 */
public final class KleeSlabsConverter implements HorizontalSlabConverter {

    @Override
    public boolean isDoubleSlab(BlockState state) {
        return KleeSlabsCompat.isDoubleSlab(Wrap.state(state));
    }

    @Override
    public BlockState getSingleSlab(BlockState state, Level level, BlockPos pos, Player player, SlabType slabType) {
        var half = lv.cebbys.mcmods.modularity.api.block.property.SlabType.valueOf(slabType.name());
        return Wrap.state(KleeSlabsCompat.singleSlab(Wrap.level(level), Wrap.pos(pos), half));
    }
}
