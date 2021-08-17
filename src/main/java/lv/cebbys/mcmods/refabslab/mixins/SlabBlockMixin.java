package lv.cebbys.mcmods.refabslab.mixins;

import lv.cebbys.mcmods.celib.loggers.CelibLogger;
import lv.cebbys.mcmods.refabslab.content.RefabslabBlocks;
import lv.cebbys.mcmods.refabslab.events.RefabslabEventsClient;
import lv.cebbys.mcmods.refabslab.utilities.SlabUtilities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SlabBlock.class)
public abstract class SlabBlockMixin extends Block {

    public SlabBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "getPlacementState", at = @At("HEAD"), cancellable = true)
    public void getPlacementState(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cr) {
        try {
            BlockPos pos = ctx.getBlockPos();
            World world = ctx.getWorld();
            BlockState placed = world.getBlockState(pos);
            if (placed.getBlock() instanceof SlabBlock) {
                ItemStack placeable = ctx.getStack();
                BlockState inventory = ((BlockItem) placeable.getItem()).getBlock().getDefaultState();
                if (this.isValidDoubleSlab(placed, inventory)) {
                    BlockState bottom;
                    BlockState top;

                    if (placed.get(SlabBlock.TYPE).equals(SlabType.BOTTOM)) {
                        bottom = placed;
                        top = inventory;
                    } else {
                        bottom = inventory;
                        top = placed;
                    }

                    BlockState doubleState = RefabslabBlocks.DOUBLE_SLAB.getDefaultState();
                    if (world.canPlace(doubleState, pos, ShapeContext.absent())) {
                        Identifier bottomId = Registry.BLOCK.getId(bottom.getBlock());
                        Identifier topId = Registry.BLOCK.getId(top.getBlock());
                        if (world.isClient()) {
                            RefabslabEventsClient.Execute.createDoubleSlabEvent(pos, bottomId, topId);
                        }
                        cr.setReturnValue(placed);
                        cr.cancel();
                    }
                }
            }
        } catch (Exception e) {
            CelibLogger.error(e, "Failed to place double slab block");
        }
    }

    private boolean isValidDoubleSlab(BlockState placed, BlockState inventory) {
        return !placed.get(SlabBlock.TYPE).equals(SlabType.DOUBLE)
                && !placed.getBlock().equals(inventory.getBlock())
                && SlabUtilities.Blocks.contains(placed.getBlock(), inventory.getBlock());
    }

    @Inject(method = "canReplace", at = @At("HEAD"), cancellable = true)
    public void canReplace(BlockState state, ItemPlacementContext ctx, CallbackInfoReturnable<Boolean> cr) {
        SlabType type = state.get(SlabBlock.TYPE);
        BlockPos pos = ctx.getBlockPos();
        World world = ctx.getWorld();
        BlockState placed = world.getBlockState(pos);
        if (placed.getBlock() instanceof SlabBlock) {
            ItemStack placeable = ctx.getStack();
            BlockState inventory = ((BlockItem) placeable.getItem()).getBlock().getDefaultState();
            if (this.isValidDoubleSlab(placed, inventory)) {
                BlockState doubleState = RefabslabBlocks.DOUBLE_SLAB.getDefaultState();
                if (world.canPlace(doubleState, pos, ShapeContext.absent())) {
                    Direction placementFace = ctx.getSide();
                    Direction s = ctx.getSide();
                    Vec3d hit = ctx.getHitPos();
                    cr.setReturnValue(pos.getX() == hit.getX() && s == Direction.EAST
                            || pos.getX() + 1 == hit.getX() && s == Direction.WEST
                            || pos.getY() == hit.getY() && s == Direction.UP
                            || pos.getY() + 1 == hit.getY() && s == Direction.DOWN
                            || pos.getZ() == hit.getZ() && s == Direction.SOUTH
                            || pos.getZ() + 1 == hit.getZ() && s == Direction.NORTH
                            || type == SlabType.TOP && placementFace == Direction.DOWN
                            || type == SlabType.BOTTOM && placementFace == Direction.UP);
                    cr.cancel();
                }
            }
        }
    }
}
