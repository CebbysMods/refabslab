package lv.cebbys.mcmods.refabslab.content.block;

import lv.cebbys.mcmods.refabslab.content.component.DoubleSlabComponent;
import lv.cebbys.mcmods.refabslab.utility.TransformUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.mob.PiglinBrain;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

abstract class AbstractDoubleSlabBlock extends Block {

    protected AbstractDoubleSlabBlock(Settings settings) {
        super(settings);
    }

    protected abstract float getBlockHardness(BlockState top, BlockState bottom, BlockView view, BlockPos pos);

    protected abstract float getMiningSpeed(BlockState top, BlockState bottom, PlayerEntity player);

    protected abstract float getHarvestSpeed(BlockState top, BlockState bottom, PlayerEntity player);

    protected abstract boolean canPlayerHarvest(BlockState top, BlockState bottom, PlayerEntity player);

    @SuppressWarnings({"deprecation"})
    @Override
    public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView view, BlockPos pos) {
        DoubleSlabComponent component;
        if((component = TransformUtils.toDoubleSlabComponent(view, pos)) == null) return 0.0F;
        BlockState top = component.getTopSlabState(pos);
        BlockState bottom = component.getBottomSlabState(pos);
        float hardness = getBlockHardness(top, bottom, view, pos);
        float speed = getMiningSpeed(top, bottom, player);
        float harvest = getHarvestSpeed(top, bottom, player);
        return hardness == -1 ? 0.0F : speed / hardness / harvest;
    }

    @Override
    public void onBreak(World view, BlockPos pos, BlockState state, PlayerEntity player) {
        DoubleSlabComponent component;
        if((component = TransformUtils.toDoubleSlabComponent(view, pos)) != null) {
            BlockState top = component.getTopSlabState(pos);
            BlockState bottom = component.getBottomSlabState(pos);
            if (canPlayerHarvest(top, bottom, player) && isSurvival(player)) {
                double x = pos.getX() + 0.5D;
                double y = pos.getY() + 0.5D;
                double z = pos.getZ() + 0.5D;
                view.spawnEntity(new ItemEntity(view, x, y, z, new ItemStack(top.getBlock())));
                view.spawnEntity(new ItemEntity(view, x, y, z, new ItemStack(bottom.getBlock())));
            }
            this.spawnBreakParticles(view, player, pos, top);
            this.spawnBreakParticles(view, player, pos, bottom);
            if (top.isIn(BlockTags.GUARDED_BY_PIGLINS) || bottom.isIn(BlockTags.GUARDED_BY_PIGLINS)) {
                PiglinBrain.onGuardedBlockInteracted(player, false);
            }
        }
        view.emitGameEvent(player, GameEvent.BLOCK_DESTROY, pos);
    }

    private boolean isSurvival(PlayerEntity player) {
        return !player.isCreative() && !player.isSpectator();
    }
}
