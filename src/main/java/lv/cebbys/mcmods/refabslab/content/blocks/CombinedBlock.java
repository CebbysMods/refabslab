package lv.cebbys.mcmods.refabslab.content.blocks;

import lv.cebbys.mcmods.celib.loggers.CelibLogger;
import lv.cebbys.mcmods.refabslab.content.entities.CombinedBlockEntity;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public abstract class CombinedBlock<E extends CombinedBlockEntity> extends BlockWithEntity {

    protected CombinedBlock(Settings settings) {
        super(settings);
    }

    public abstract E createEntity(BlockPos pos, BlockState state);

    public abstract float getBlockHardness(E blockEntity);

    public abstract float getMiningSpeed(PlayerEntity player, E blockEntity);

    public abstract float getHarvestSpeed(PlayerEntity player, E blockEntity);

    public abstract boolean canPlayerHarvest(PlayerEntity player, BlockState state);

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return this.createEntity(pos, state);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @SuppressWarnings({"deprecation", "unchecked"})
    @Override
    public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity != null) {
            try {
                E entity = (E) blockEntity;
                float hardness = this.getBlockHardness(entity);
                float speed = this.getMiningSpeed(player, entity);
                float harvest = this.getHarvestSpeed(player, entity);
                return hardness == -1 ? 0.0F : speed / hardness / harvest;
            } catch (Exception e) {
                CelibLogger.info(e, "Failed to cast BlockEntity to type");
            }
        }
        return super.calcBlockBreakingDelta(state, player, world, pos);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity != null && this.isSurvival(player)) {
            try {
                E entity = (E) blockEntity;
                BlockState topState = Registry.BLOCK.get(entity.getExtend()).getDefaultState();
                BlockState bottomState = Registry.BLOCK.get(entity.getBase()).getDefaultState();
                double x = pos.getX() + 0.5D;
                double y = pos.getY() + 0.5D;
                double z = pos.getZ() + 0.5D;
                if (this.canPlayerHarvest(player, topState)) {
                    world.spawnEntity(new ItemEntity(world, x, y, z, new ItemStack(topState.getBlock())));
                }
                if (this.canPlayerHarvest(player, bottomState)) {
                    world.spawnEntity(new ItemEntity(world, x, y, z, new ItemStack(bottomState.getBlock())));
                }
            } catch (Exception e) {
                CelibLogger.info(e, "Failed to cast BlockEntity to type");
            }
        }
        super.onBreak(world, pos, state, player);
    }

    private boolean isSurvival(PlayerEntity player) {
        return !player.isCreative() && !player.isSpectator();
    }
}
