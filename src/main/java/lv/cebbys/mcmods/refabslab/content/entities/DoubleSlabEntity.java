package lv.cebbys.mcmods.refabslab.content.entities;

import lv.cebbys.mcmods.refabslab.content.RefabslabEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

public class DoubleSlabEntity extends CombinedBlockEntity<SlabBlock> {

    public DoubleSlabEntity(BlockPos pos, BlockState state) {
        this(null, null, pos, state);
    }

    public DoubleSlabEntity(SlabBlock b, SlabBlock e, BlockPos pos, BlockState state) {
        super(RefabslabEntities.SLAB_ENTITY, b, e, pos, state);
    }

    public SlabBlock getBaseSlab() {
        return this.getBase() == null ? null : (SlabBlock) Registry.BLOCK.get(this.getBase());
    }

    public SlabBlock getExtendSlab() {
        return this.getExtend() == null ? null : (SlabBlock) Registry.BLOCK.get(this.getExtend());
    }

    @Override
    protected String getBaseIdentifier() {
        return "bottom_slab";
    }

    @Override
    protected String getExtendIdentifier() {
        return "top_slab";
    }
}
