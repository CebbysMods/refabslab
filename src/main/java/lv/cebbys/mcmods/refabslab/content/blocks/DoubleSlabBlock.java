package lv.cebbys.mcmods.refabslab.content.blocks;

import lv.cebbys.mcmods.refabslab.content.entities.DoubleSlabEntity;
import lv.cebbys.mcmods.celib.loggers.CelibLogger;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.MapColor;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.function.Function;

public class DoubleSlabBlock extends BlockWithEntity {

    public DoubleSlabBlock(Function<FabricBlockSettings, FabricBlockSettings> settings) {
        super(settings.apply(FabricBlockSettings.of(new Material(
                MapColor.GRAY, false, true, false, true, false, false, PistonBehavior.NORMAL
        ))));
    }

    @SuppressWarnings("deprecation")
    @Override
    public float calcBlockBreakingDelta(BlockState state, PlayerEntity player, BlockView world, BlockPos pos) {
        if (world.getBlockEntity(pos) instanceof DoubleSlabEntity entity) {
            BlockState top = Registry.BLOCK.get(entity.getTop()).getDefaultState();
            BlockState bottom = Registry.BLOCK.get(entity.getTop()).getDefaultState();
            if ((top.getHardness(world, pos) == -1) || (bottom.getHardness(world, pos) == -1)) return 0.0F;
            else {
                float hardness = Math.max(top.getHardness(world, pos), bottom.getHardness(world, pos));
                float speed = Math.max(
                        player.getBlockBreakingSpeed(Registry.BLOCK.get(entity.getBottom()).getDefaultState()),
                        player.getBlockBreakingSpeed(Registry.BLOCK.get(entity.getTop()).getDefaultState())
                );
                float harvest = 100;
                harvest -= player.canHarvest(top) ? 35 : 0;
                harvest -= player.canHarvest(bottom) ? 35 : 0;
                return speed / hardness / harvest;
            }
        }
        return super.calcBlockBreakingDelta(state, player, world, pos);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if(world.getBlockEntity(pos) instanceof DoubleSlabEntity entity && !world.isClient()) {
            BlockState topState = Registry.BLOCK.get(entity.getTop()).getDefaultState();
            BlockState bottomState = Registry.BLOCK.get(entity.getBottom()).getDefaultState();
            double x = pos.getX() + 0.5D;
            double y = pos.getY() + 0.5D;
            double z = pos.getZ() + 0.5D;
            if(player.canHarvest(topState)) {
                world.spawnEntity(new ItemEntity(world, x, y, z, new ItemStack(topState.getBlock())));
            }
            if(player.canHarvest(bottomState)) {
                world.spawnEntity(new ItemEntity(world, x, y, z, new ItemStack(bottomState.getBlock())));
            }
        }
        super.onBreak(world, pos, state, player);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new DoubleSlabEntity(pos, state);
    }

}
