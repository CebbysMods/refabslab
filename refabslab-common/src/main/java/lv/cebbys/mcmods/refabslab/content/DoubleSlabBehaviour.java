package lv.cebbys.mcmods.refabslab.content;

import lv.cebbys.mcmods.modularity.api.block.BlockBehaviour;
import lv.cebbys.mcmods.modularity.api.block.BlockContext;
import lv.cebbys.mcmods.modularity.api.block.BlockStateRef;
import lv.cebbys.mcmods.modularity.api.block.BreakContext;
import lv.cebbys.mcmods.modularity.api.block.DropContext;
import lv.cebbys.mcmods.modularity.api.item.ItemStackRef;
import lv.cebbys.mcmods.modularity.api.world.Player;
import lv.cebbys.mcmods.refabslab.RefabslabMod;

import java.util.List;
import java.util.stream.Stream;

/**
 * Mining a double slab behaves like mining its slabs: the harder slab decides the hardness, the
 * better-suited tool decides the speed, and both slabs drop if the tool can harvest at least one.
 */
public final class DoubleSlabBehaviour implements BlockBehaviour {

    @Override
    public float destroyProgress(BlockContext ctx, Player player) {
        DoubleSlab slab = DoubleSlab.orDefault(ctx.data(RefabslabMod.DOUBLE_SLAB_DATA));
        BlockStateRef top = slab.topState();
        BlockStateRef bottom = slab.bottomState();
        float hardness = Math.max(top.destroySpeed(ctx.view(), ctx.pos()), bottom.destroySpeed(ctx.view(), ctx.pos()));
        if (hardness == -1.0F) {
            return 0.0F;
        }
        float speed = Math.max(player.destroySpeed(top), player.destroySpeed(bottom));
        float harvest = 100.0F;
        harvest -= player.hasCorrectToolForDrops(top) ? 35.0F : 0.0F;
        harvest -= player.hasCorrectToolForDrops(bottom) ? 35.0F : 0.0F;
        return speed / hardness / harvest;
    }

    @Override
    public void onPlayerBreak(BreakContext ctx) {
        DoubleSlab slab = DoubleSlab.orDefault(ctx.data(RefabslabMod.DOUBLE_SLAB_DATA));
        ctx.breakEffects(slab.topState());
        ctx.breakEffects(slab.bottomState());
    }

    @Override
    public List<ItemStackRef> drops(DropContext ctx) {
        DoubleSlab slab = DoubleSlab.orDefault(ctx.data(RefabslabMod.DOUBLE_SLAB_DATA));
        BlockStateRef top = slab.topState();
        BlockStateRef bottom = slab.bottomState();
        boolean harvestable = ctx.player()
                .map(player -> player.hasCorrectToolForDrops(top) || player.hasCorrectToolForDrops(bottom))
                .orElse(true);
        if (!harvestable) {
            return List.of();
        }
        return Stream.of(top.block().asStack(1), bottom.block().asStack(1))
                .filter(stack -> !stack.isEmpty())
                .toList();
    }
}
