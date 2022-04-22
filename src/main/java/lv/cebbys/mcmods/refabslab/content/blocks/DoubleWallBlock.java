package lv.cebbys.mcmods.refabslab.content.blocks;

import lv.cebbys.mcmods.refabslab.content.entities.DoubleWallEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class DoubleWallBlock extends CombinedBlock<DoubleWallEntity> {

    public static final EnumProperty<Direction.Axis> AXIS;

    static {
        AXIS = EnumProperty.of("axis", Direction.Axis.class, Direction.Axis.X, Direction.Axis.Z);
    }

    public DoubleWallBlock() {
        super(FabricBlockSettings.of(Material.STONE));
        this.setDefaultState(this.getDefaultState().with(AXIS, Direction.Axis.X));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
    }

    @Override
    public DoubleWallEntity createEntity(BlockPos pos, BlockState state) {
        return new DoubleWallEntity(pos, state);
    }

    @Override
    public float getBlockHardness(DoubleWallEntity blockEntity) {
        BlockState base = blockEntity.getBaseWall().getSlabVariant().getDefaultState();
        BlockState extend = blockEntity.getExtendWall().getSlabVariant().getDefaultState();
        World world = blockEntity.getWorld();
        BlockPos pos = blockEntity.getPos();
        return Math.max(base.getHardness(world, pos), extend.getHardness(world, pos));
    }

    @Override
    public float getMiningSpeed(PlayerEntity player, DoubleWallEntity blockEntity) {
        BlockState base = blockEntity.getBaseWall().getSlabVariant().getDefaultState();
        BlockState extend = blockEntity.getExtendWall().getSlabVariant().getDefaultState();
        return Math.max(player.getBlockBreakingSpeed(base), player.getBlockBreakingSpeed(extend));
    }

    @Override
    public float getHarvestSpeed(PlayerEntity player, DoubleWallEntity blockEntity) {
        BlockState base = blockEntity.getBaseWall().getSlabVariant().getDefaultState();
        BlockState extend = blockEntity.getExtendWall().getSlabVariant().getDefaultState();
        float harvest = 100;
        harvest -= player.canHarvest(base) ? 35 : 0;
        harvest -= player.canHarvest(extend) ? 35 : 0;
        return harvest;
    }

    @Override
    public boolean canPlayerHarvest(PlayerEntity player, BlockState state) {
        WallBlock wall = (WallBlock) state.getBlock();
        BlockState slab = wall.getSlabVariant().getDefaultState();
        return player.canHarvest(slab);
    }
}
