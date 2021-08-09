package lv.cebbys.mcmods.refabslab.content.blocks;

import lv.cebbys.mcmods.refabslab.content.entities.DoubleSlabEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Function;

public class DoubleSlabBlock extends CombinedBlock<DoubleSlabEntity> {

    public DoubleSlabBlock(Function<FabricBlockSettings, FabricBlockSettings> settings) {
        super(settings.apply(FabricBlockSettings.of(Material.STONE)));
    }

    @Override
    public DoubleSlabEntity createEntity(BlockPos pos, BlockState state) {
        return new DoubleSlabEntity(pos, state);
    }

    @Override
    public float getBlockHardness(DoubleSlabEntity blockEntity) {
        BlockState base = blockEntity.getBaseSlab().getDefaultState();
        BlockState extend = blockEntity.getExtendSlab().getDefaultState();
        World world = blockEntity.getWorld();
        BlockPos pos = blockEntity.getPos();
        return Math.max(base.getHardness(world, pos), extend.getHardness(world, pos));
    }

    @Override
    public float getMiningSpeed(PlayerEntity player, DoubleSlabEntity blockEntity) {
        BlockState base = blockEntity.getBaseSlab().getDefaultState();
        BlockState extend = blockEntity.getExtendSlab().getDefaultState();
        return Math.max(player.getBlockBreakingSpeed(base), player.getBlockBreakingSpeed(extend));
    }

    @Override
    public float getHarvestSpeed(PlayerEntity player, DoubleSlabEntity blockEntity) {
        BlockState base = blockEntity.getBaseSlab().getDefaultState();
        BlockState extend = blockEntity.getExtendSlab().getDefaultState();
        float harvest = 100;
        harvest -= player.canHarvest(base) ? 35 : 0;
        harvest -= player.canHarvest(extend) ? 35 : 0;
        return harvest;
    }

    @Override
    public boolean canPlayerHarvest(PlayerEntity player, BlockState state) {
        return player.canHarvest(state);
    }
}
