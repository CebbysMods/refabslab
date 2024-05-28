package lv.cebbys.mcmods.refabslab.content.block;

import lv.cebbys.mcmods.refabslab.locator.ChunkLocator;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.PushReaction;

abstract class AbstractDoubleSlabBlock extends Block {

    protected AbstractDoubleSlabBlock(Properties settings) {
        super(settings);
    }

    protected abstract float getBlockHardness(BlockState top, BlockState bottom, BlockGetter view, BlockPos pos);

    protected abstract float getMiningSpeed(BlockState top, BlockState bottom, Player player);

    protected abstract float getHarvestSpeed(BlockState top, BlockState bottom, Player player);

    protected abstract boolean canPlayerHarvest(BlockState top, BlockState bottom, Player player);

    @Override
    public PushReaction getPistonPushReaction(BlockState blockState) {
        return PushReaction.BLOCK;
    }

    @SuppressWarnings({"deprecation"})
    @Override
    public float getDestroyProgress(BlockState state, Player player, BlockGetter view, BlockPos pos) {
        var component = ChunkLocator.getComponent(view, pos);
        if (component == null) {
            return 0.0F;
        }
        var combination = component.getSlabBlockStateCombination(pos);
        BlockState top = combination.getKey();
        BlockState bottom = combination.getValue();
        float hardness = getBlockHardness(top, bottom, view, pos);
        float speed = getMiningSpeed(top, bottom, player);
        float harvest = getHarvestSpeed(top, bottom, player);
        return hardness == -1 ? 0.0F : speed / hardness / harvest;
    }

    @Override
    public void playerWillDestroy(Level view, BlockPos pos, BlockState state, Player player) {
        var component = ChunkLocator.getComponent(view, pos);
        if (component != null) {
            var combination = component.getSlabBlockStateCombination(pos);
            BlockState top = combination.getKey();
            BlockState bottom = combination.getValue();
            if (canPlayerHarvest(top, bottom, player) && isSurvival(player)) {
                double x = pos.getX() + 0.5D;
                double y = pos.getY() + 0.5D;
                double z = pos.getZ() + 0.5D;
                view.addFreshEntity(new ItemEntity(view, x, y, z, new ItemStack(top.getBlock())));
                view.addFreshEntity(new ItemEntity(view, x, y, z, new ItemStack(bottom.getBlock())));
            }
            this.spawnDestroyParticles(view, player, pos, top);
            this.spawnDestroyParticles(view, player, pos, bottom);
            if (top.is(BlockTags.GUARDED_BY_PIGLINS) || bottom.is(BlockTags.GUARDED_BY_PIGLINS)) {
                PiglinAi.angerNearbyPiglins(player, false);
            }
        }
        view.gameEvent(player, GameEvent.BLOCK_DESTROY, pos);
    }

    private boolean isSurvival(Player player) {
        return !player.isCreative() && !player.isSpectator();
    }

}
