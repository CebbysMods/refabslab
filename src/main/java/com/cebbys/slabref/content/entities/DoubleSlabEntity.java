package com.cebbys.slabref.content.entities;

import com.cebbys.slabref.content.SlabrefSlabEntities;

import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;

public class DoubleSlabEntity extends BlockEntity implements BlockEntityClientSerializable {

	private Identifier base;
	private Identifier extend;

	public DoubleSlabEntity() {
		super(SlabrefSlabEntities.SLAB_ENTITY);
	}
	
	public DoubleSlabEntity(Identifier b, Identifier e) {
		super(SlabrefSlabEntities.SLAB_ENTITY);
		this.base = b;
		this.extend = e;
	}
	
	public Identifier getBase() {
		return this.base;
	}
	
	public Identifier getExtend() {
		return this.extend;
	}

	@Override
	public CompoundTag toTag(CompoundTag tag) {
		tag = super.toTag(tag);
		tag.putString("base", this.base.toString());
		tag.putString("extend", this.extend.toString());
		return tag;
	}

	@Override
	public void fromTag(BlockState state, CompoundTag tag) {
		super.fromTag(state, tag);
		this.base = new Identifier(tag.getString("base"));
		this.extend = new Identifier(tag.getString("extend"));
	}

	@Override
	public void fromClientTag(CompoundTag tag) {
		this.fromTag(this.getCachedState(), tag);
		if(this.getWorld() instanceof ClientWorld) {
			((ClientWorld) this.getWorld()).scheduleBlockRenders(this.getPos().getX(), this.getPos().getY(), this.getPos().getZ());
		}
	}

	@Override
	public CompoundTag toClientTag(CompoundTag tag) {
		return this.toTag(tag);
	}
}
