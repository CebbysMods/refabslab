package lv.cebbys.mcmods.refabslab.content;

import lv.cebbys.mcmods.modularity.api.block.BlockInterceptor;
import lv.cebbys.mcmods.modularity.api.block.BlockRef;
import lv.cebbys.mcmods.modularity.api.block.BlockStateRef;
import lv.cebbys.mcmods.modularity.api.block.BlockType;
import lv.cebbys.mcmods.modularity.api.block.PlacementContext;
import lv.cebbys.mcmods.modularity.api.block.PlacementResult;
import lv.cebbys.mcmods.modularity.api.block.property.SlabType;
import lv.cebbys.mcmods.modularity.api.block.property.VanillaProperties;
import lv.cebbys.mcmods.modularity.api.world.Direction;
import lv.cebbys.mcmods.modularity.api.world.Pos;
import lv.cebbys.mcmods.modularity.api.world.Vec3;
import lv.cebbys.mcmods.refabslab.RefabslabMod;

import java.util.Optional;

/**
 * Makes every slab combinable with a different slab: when a slab is placed into a half-filled slab
 * space, a double slab block remembering both slabs is placed instead.
 */
public final class SlabCombining implements BlockInterceptor {

    @Override
    public Optional<PlacementResult> placement(PlacementContext ctx) {
        BlockStateRef placed = ctx.existingState();
        Optional<BlockRef> inventory = ctx.stack().block();
        if (inventory.isEmpty() || !isValidDoubleSlab(placed, inventory.get())) {
            return Optional.empty();
        }
        BlockStateRef held = inventory.get().defaultState();
        boolean placedIsBottom = placed.get(VanillaProperties.SLAB_TYPE) == SlabType.BOTTOM;
        BlockStateRef bottom = placedIsBottom ? placed : held;
        BlockStateRef top = placedIsBottom ? held : placed;

        BlockStateRef doubleSlab = RefabslabMod.doubleSlab().defaultState()
                .with(RefabslabMod.LIGHT_LEVEL, luminance(top, bottom));
        if (!ctx.level().isUnobstructed(doubleSlab, ctx.pos())) {
            return Optional.empty();
        }
        return Optional.of(PlacementResult.of(doubleSlab).with(RefabslabMod.DOUBLE_SLAB_DATA, DoubleSlab.of(top, bottom)));
    }

    @Override
    public Optional<Boolean> canBeReplaced(BlockStateRef existing, PlacementContext ctx) {
        Optional<BlockRef> inventory = ctx.stack().block();
        if (inventory.isEmpty() || !isValidDoubleSlab(existing, inventory.get())) {
            // Not a slab combination (e.g. falling blocks, other items): keep vanilla behaviour.
            return Optional.empty();
        }
        if (!ctx.level().isUnobstructed(RefabslabMod.doubleSlab().defaultState(), ctx.pos())) {
            return Optional.empty();
        }
        SlabType type = existing.get(VanillaProperties.SLAB_TYPE);
        Direction face = ctx.clickedFace();
        Vec3 hit = ctx.clickLocation();
        Pos pos = ctx.pos();
        return Optional.of(pos.x() == hit.x() && face == Direction.EAST
                || pos.x() + 1 == hit.x() && face == Direction.WEST
                || pos.y() == hit.y() && face == Direction.UP
                || pos.y() + 1 == hit.y() && face == Direction.DOWN
                || pos.z() == hit.z() && face == Direction.SOUTH
                || pos.z() + 1 == hit.z() && face == Direction.NORTH
                || type == SlabType.TOP && face == Direction.DOWN
                || type == SlabType.BOTTOM && face == Direction.UP);
    }

    static boolean isValidDoubleSlab(BlockStateRef placed, BlockRef inventory) {
        return inventory.is(BlockType.SLAB)
                && placed.block().is(BlockType.SLAB)
                && placed.has(VanillaProperties.SLAB_TYPE)
                && placed.get(VanillaProperties.SLAB_TYPE) != SlabType.DOUBLE
                && !placed.is(inventory)
                && !placed.is(RefabslabMod.NON_COMBINABLE)
                && !inventory.defaultState().is(RefabslabMod.NON_COMBINABLE);
    }

    static int luminance(BlockStateRef top, BlockStateRef bottom) {
        return Math.max(0, Math.min(15, Math.max(top.lightEmission(), bottom.lightEmission())));
    }
}
