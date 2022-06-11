package lv.cebbys.mcmods.refabslab.content.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class DoubleSlabBlock extends AbstractDoubleSlabBlock {
    public static final IntProperty LIGHT_LEVEL;

    public DoubleSlabBlock() {
        super(FabricBlockSettings.of(Material.STONE).luminance(s -> s.get(LIGHT_LEVEL)));
        setDefaultState(getStateManager().getDefaultState().with(LIGHT_LEVEL, 0));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(LIGHT_LEVEL);
    }


    @Override
    protected float getBlockHardness(BlockState top, BlockState bottom, BlockView world, BlockPos pos) {
        return Math.max(top.getHardness(world, pos), bottom.getHardness(world, pos));
    }

    @Override
    protected float getMiningSpeed(BlockState top, BlockState bottom, PlayerEntity player) {
        return Math.max(player.getBlockBreakingSpeed(top), player.getBlockBreakingSpeed(bottom));
    }

    @Override
    protected float getHarvestSpeed(BlockState top, BlockState bottom, PlayerEntity player) {
        float harvest = 100;
        harvest -= player.canHarvest(top) ? 35 : 0;
        harvest -= player.canHarvest(bottom) ? 35 : 0;
        return harvest;
    }

    @Override
    protected boolean canPlayerHarvest(BlockState top, BlockState bottom, PlayerEntity player) {
        return player.canHarvest(top) || player.canHarvest(bottom);
    }

    static {
        LIGHT_LEVEL = IntProperty.of("light_level", 0, 15);
    }
}
