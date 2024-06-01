package lv.cebbys.mcmods.refabslab.v1.v19.mixin;

import lv.cebbys.mcmods.refabslab.bridge.api.RefabslabRegistry;
import lv.cebbys.mcmods.refabslab.bridge.constant.RefabslabConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Supplier;

@Mixin(SlabBlock.class)
public abstract class SlabBlockMixin extends Block {
    private Supplier<BlockState> doubleSlabSupplier;

    public SlabBlockMixin(Properties settings) {
        super(settings);
    }

    @Inject(method = "getStateForPlacement", at = @At("HEAD"), cancellable = true)
    public void getStateForPlacement(BlockPlaceContext ctx, CallbackInfoReturnable<BlockState> cr) {
        try {
            BlockPos pos = ctx.getClickedPos();
            Level world = ctx.getLevel();
            BlockState placed = world.getBlockState(pos);
            if (placed.getBlock() instanceof SlabBlock) {
                ItemStack placeable = ctx.getItemInHand();
                BlockState inventory = ((BlockItem) placeable.getItem()).getBlock().defaultBlockState();
                if (this.isValidDoubleSlab(placed, inventory)) {
                    BlockState bottom;
                    BlockState top;

                    if (placed.getValue(SlabBlock.TYPE).equals(SlabType.BOTTOM)) {
                        bottom = placed;
                        top = inventory;
                    } else {
                        bottom = inventory;
                        top = placed;
                    }

                    BlockState doubleState = getDoubleSlabState()
                            .setValue(RefabslabConstants.LIGHT_LEVEL, getLuminance(top, bottom));
                    if (world.isUnobstructed(doubleState, pos, CollisionContext.empty())) {
                        ResourceLocation bottomId = Registry.BLOCK.getKey(bottom.getBlock());
                        ResourceLocation topId = Registry.BLOCK.getKey(top.getBlock());
                        var locator = RefabslabRegistry.getDoubleSlabComponentLocator();
                        var component = locator.get(world.getChunk(pos));
                        component.pushSlabCombination(pos, topId, bottomId);
                        cr.setReturnValue(doubleState);
                        cr.cancel();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage() + ": " + e.getCause());
        }
    }

    @Inject(method = "canBeReplaced", at = @At("HEAD"), cancellable = true)
    public void canBeReplaced(BlockState state, BlockPlaceContext ctx, CallbackInfoReturnable<Boolean> cr) {
        SlabType type = state.getValue(SlabBlock.TYPE);
        BlockPos pos = ctx.getClickedPos();
        Level world = ctx.getLevel();
        BlockState placed = world.getBlockState(pos);
        if (!(placed.getBlock() instanceof SlabBlock)) return;

        Item item = ctx.getItemInHand().getItem();
        if (!(item instanceof BlockItem)) return;

        BlockState inventory = ((BlockItem) item).getBlock().defaultBlockState();
        if (!this.isValidDoubleSlab(placed, inventory)) return;

        BlockState doubleState = getDoubleSlabState();
        if (!world.isUnobstructed(doubleState, pos, CollisionContext.empty())) return;

        Direction placementFace = ctx.getClickedFace();
        Direction s = ctx.getClickedFace();
        Vec3 hit = ctx.getClickLocation();
        cr.setReturnValue(pos.getX() == hit.x() && s == Direction.EAST
                || pos.getX() + 1 == hit.x() && s == Direction.WEST
                || pos.getY() == hit.y() && s == Direction.UP
                || pos.getY() + 1 == hit.y() && s == Direction.DOWN
                || pos.getZ() == hit.z() && s == Direction.SOUTH
                || pos.getZ() + 1 == hit.z() && s == Direction.NORTH
                || type == SlabType.TOP && placementFace == Direction.DOWN
                || type == SlabType.BOTTOM && placementFace == Direction.UP);
        cr.cancel();
    }

    private int getLuminance(@NotNull BlockState a, @NotNull BlockState b) {
        int al = a.getLightEmission();
        int bl = b.getLightEmission();
        int m = Math.max(al, bl);
        m = Math.min(15, m);
        m = Math.max(0, m);
        return m;
    }

    private boolean isValidDoubleSlab(BlockState placed, BlockState inventory) {
        try {
            return inventory.getBlock() instanceof SlabBlock
                    && RefabslabRegistry.isAllowedSlab(Registry.BLOCK.getKey(placed.getBlock()))
                    && RefabslabRegistry.isAllowedSlab(Registry.BLOCK.getKey(inventory.getBlock()))
                    && !placed.getValue(SlabBlock.TYPE).equals(SlabType.DOUBLE)
                    && !placed.getBlock().equals(inventory.getBlock());
        } catch (Exception e) {
            return false;
        }
    }

    private BlockState getDoubleSlabState() {
        if (doubleSlabSupplier == null) {
            var block = Registry.BLOCK.get(RefabslabConstants.DOUBLE_SLAB_BLOCK_KEY);
            if (block == Blocks.AIR) {
                throw new RuntimeException("Failed to load Refabslab double slab block - It does not exist in registry");
            }
            doubleSlabSupplier = block::defaultBlockState;
        }
        return doubleSlabSupplier.get();
    }
}