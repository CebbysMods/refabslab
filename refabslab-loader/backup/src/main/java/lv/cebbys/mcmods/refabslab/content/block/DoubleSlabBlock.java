package lv.cebbys.mcmods.refabslab.content.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Material;

import static lv.cebbys.mcmods.refabslab.bridge.constant.RefabslabConstants.LIGHT_LEVEL;

public class DoubleSlabBlock extends AbstractDoubleSlabBlock {

    public DoubleSlabBlock() {
        super(FabricBlockSettings.of(Material.STONE).lightLevel(s -> s.getValue(LIGHT_LEVEL)));
        registerDefaultState(getStateDefinition().any().setValue(LIGHT_LEVEL, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LIGHT_LEVEL);
    }


    @Override
    protected float getBlockHardness(BlockState top, BlockState bottom, BlockGetter world, BlockPos pos) {
        return Math.max(top.getDestroySpeed(world, pos), bottom.getDestroySpeed(world, pos));
    }

    @Override
    protected float getMiningSpeed(BlockState top, BlockState bottom, Player player) {
        return Math.max(player.getDestroySpeed(top), player.getDestroySpeed(bottom));
    }

    @Override
    protected float getHarvestSpeed(BlockState top, BlockState bottom, Player player) {
        float harvest = 100;
        harvest -= player.hasCorrectToolForDrops(top) ? 35 : 0;
        harvest -= player.hasCorrectToolForDrops(bottom) ? 35 : 0;
        return harvest;
    }

    @Override
    protected boolean canPlayerHarvest(BlockState top, BlockState bottom, Player player) {
        return player.hasCorrectToolForDrops(top) || player.hasCorrectToolForDrops(bottom);
    }
}
