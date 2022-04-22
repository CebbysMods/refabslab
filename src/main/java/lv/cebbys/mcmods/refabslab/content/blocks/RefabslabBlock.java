package lv.cebbys.mcmods.refabslab.content.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public abstract class RefabslabBlock extends Block {

    public RefabslabBlock(Settings settings) {
        super(settings);
    }

    public abstract float getBlockHardness(BlockView world, BlockPos pos);

    public abstract float getMiningSpeed(PlayerEntity player, BlockState state);

    public abstract float getHarvestSpeed(PlayerEntity player, BlockState state);

    public abstract boolean canPlayerHarvest(PlayerEntity player, BlockState state);

    @SuppressWarnings({"deprecation"})
    @Override
    public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
        float hardness = this.getBlockHardness(world, pos);
        float speed = this.getMiningSpeed(player, state);
        float harvest = this.getHarvestSpeed(player, state);
        return hardness == -1 ? 0.0F : speed / hardness / harvest;
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (this.canPlayerHarvest(player, state) && this.isSurvival(player)) {
            double x = pos.getX() + 0.5D;
            double y = pos.getY() + 0.5D;
            double z = pos.getZ() + 0.5D;
            world.spawnEntity(new ItemEntity(world, x, y, z, new ItemStack(state.getBlock())));
        }
        super.onBreak(world, pos, state, player);
    }

    private boolean isSurvival(PlayerEntity player) {
        return !player.isCreative() && !player.isSpectator();
    }
}
