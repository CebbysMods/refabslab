package lv.cebbys.mcmods.refabslab.content.entities;

import lv.cebbys.mcmods.celib.components.blockentity.ClientSyncBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

public abstract class CombinedBlockEntity<T extends Block> extends ClientSyncBlockEntity {

    private Identifier base;
    private Identifier extend;

    protected CombinedBlockEntity(BlockEntityType<? extends CombinedBlockEntity<?>> type, T b, T e, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.base = Registry.BLOCK.getId(b);
        this.extend = Registry.BLOCK.getId(e);
    }

    protected abstract String getBaseIdentifier();

    protected abstract String getExtendIdentifier();

    public Identifier getBase() {
        return this.base;
    }

    public Identifier getExtend() {
        return this.extend;
    }


    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        if (tag.contains(this.getBaseIdentifier()) && tag.contains(this.getExtendIdentifier())) {
            this.base = new Identifier(tag.getString(this.getBaseIdentifier()));
            this.extend = new Identifier(tag.getString(this.getExtendIdentifier()));
        }
    }

    @Override
    protected void writeNbt(NbtCompound tag) {
        super.writeNbt(tag);
        if (this.getBase() != null && this.getExtend() != null) {
            tag.putString(this.getBaseIdentifier(), this.getBase().toString());
            tag.putString(this.getExtendIdentifier(), this.getExtend().toString());
        }
    }
}
