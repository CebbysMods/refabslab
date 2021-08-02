package lv.cebbys.mcmods.refabslab.content.entities;

import lv.cebbys.mcmods.refabslab.content.RefabslabSlabEntities;
import lv.cebbys.mcmods.refabslab.utilities.BlockStateEntityProvider;
import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class DoubleSlabEntity extends BlockEntity implements BlockEntityClientSerializable {

    private Identifier bottom;
    private Identifier top;

    public DoubleSlabEntity(BlockPos pos, BlockState state) {
        this(null, null, pos, state);
    }

    public DoubleSlabEntity(Identifier b, Identifier e, BlockPos pos, BlockState state) {
        super(RefabslabSlabEntities.SLAB_ENTITY, pos, state);
        ((BlockStateEntityProvider) state).setBlockEntity(this);
        this.bottom = b;
        this.top = e;
    }

    public Identifier getBottom() {
        return this.bottom;
    }

    public Identifier getTop() {
        return this.top;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound tag) {
        tag = super.writeNbt(tag);
        tag.putString("bottom_slab", this.bottom.toString());
        tag.putString("top_slab", this.top.toString());
        return tag;
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        this.bottom = new Identifier(tag.getString("bottom_slab"));
        this.top = new Identifier(tag.getString("top_slab"));
    }

    @Override
    public void fromClientTag(NbtCompound tag) {
        this.readNbt(tag);
        if (this.getWorld() instanceof ClientWorld) {
            ((ClientWorld) this.getWorld()).scheduleBlockRenders(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ());
        }
    }

    @Override
    public NbtCompound toClientTag(NbtCompound tag) {
        return this.writeNbt(tag);
    }
}
