package lv.cebbys.mcmods.refabslab.mixin;

import lv.cebbys.mcmods.refabslab.content.RefabslabBlocks;
import lv.cebbys.mcmods.refabslab.content.RefabslabComponents;
import lv.cebbys.mcmods.refabslab.content.block.DoubleSlabBlock;
import lv.cebbys.mcmods.refabslab.content.component.DoubleSlabComponent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SlabBlock.class)
public abstract class SlabBlockMixin extends Block {

    public SlabBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "getPlacementState(Lnet/minecraft/item/ItemPlacementContext;)Lnet/minecraft/block/BlockState;", at = @At("HEAD"), cancellable = true)
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

                    BlockState doubleState = RefabslabBlocks.DOUBLE_SLAB.getDefaultState()
                            .with(DoubleSlabBlock.LIGHT_LEVEL, getLuminance(top, bottom));
                    if (world.canPlace(doubleState, pos, ShapeContext.absent())) {
                        Identifier bottomId = Registry.BLOCK.getId(bottom.getBlock());
                        Identifier topId = Registry.BLOCK.getId(top.getBlock());
                        DoubleSlabComponent component = RefabslabComponents.DOUBLE_SLAB_QUEUE.get(world.getChunk(pos));
                        component.setSlabIds(pos, topId, bottomId);
                        cr.setReturnValue(doubleState);
                        cr.cancel();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage() + ": " + e.getCause());
        }
    }

    private int getLuminance(@NotNull BlockState a, @NotNull BlockState b) {
        int al = a.getLuminance();
        int bl = b.getLuminance();
        int m = Math.max(al, bl);
        m = Math.min(15, m);
        m = Math.max(0, m);
        return m;
    }

    private boolean isValidDoubleSlab(BlockState placed, BlockState inventory) {
        return inventory.getBlock() instanceof SlabBlock
                && !placed.get(SlabBlock.TYPE).equals(SlabType.DOUBLE)
                && !placed.getBlock().equals(inventory.getBlock());
    }

    @Inject(method = "canReplace(Lnet/minecraft/block/BlockState;Lnet/minecraft/item/ItemPlacementContext;)Z", at = @At("HEAD"), cancellable = true)
    public void canReplace(BlockState state, ItemPlacementContext ctx, CallbackInfoReturnable<Boolean> cr) {
        SlabType type = state.get(SlabBlock.TYPE);
        BlockPos pos = ctx.getBlockPos();
        World world = ctx.getWorld();
        BlockState placed = world.getBlockState(pos);
        if (!(placed.getBlock() instanceof SlabBlock)) return;

        Item item = ctx.getStack().getItem();
        if (!(item instanceof BlockItem)) return;

        BlockState inventory = ((BlockItem) item).getBlock().getDefaultState();
        if (!this.isValidDoubleSlab(placed, inventory)) return;

        BlockState doubleState = RefabslabBlocks.DOUBLE_SLAB.getDefaultState();
        if (!world.canPlace(doubleState, pos, ShapeContext.absent())) return;

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
