package lv.cebbys.mcmods.refabslab.content.entities;

import lv.cebbys.mcmods.refabslab.content.RefabslabEntities;
import lv.cebbys.mcmods.refabslab.content.blocks.WallBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

public class DoubleWallEntity extends CombinedBlockEntity<WallBlock> {

    public DoubleWallEntity(BlockPos pos, BlockState state) {
        this(null, null, pos, state);
    }

    public DoubleWallEntity(WallBlock b, WallBlock e, BlockPos pos, BlockState state) {
        super(RefabslabEntities.WALL_ENTITY, b, e, pos, state);
    }

    public WallBlock getBaseWall() {
        return this.getBase() != null ? (WallBlock) Registry.BLOCK.get(this.getBase()) : null;
    }

    public WallBlock getExtendWall() {
        return this.getExtend() != null ? (WallBlock) Registry.BLOCK.get(this.getExtend()) : null;
    }

    @Override
    protected String getBaseIdentifier() {
        return "positive_wall";
    }

    @Override
    protected String getExtendIdentifier() {
        return "negative_wall";
    }
}
