package com.cebbys.slabref.content;

import java.util.function.Supplier;

import com.cebbys.slabref.Slabref;
import com.cebbys.slabref.content.entities.DoubleSlabEntity;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SlabrefSlabEntities {
	
	public static final BlockEntityType<DoubleSlabEntity> SLAB_ENTITY;
	
	private static <T extends BlockEntity> BlockEntityType<T> createBlockEntity(String entityName, Supplier<T> supplier, Block block) {
		return Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Slabref.MODID, entityName), BlockEntityType.Builder.create(supplier, block).build(null));
	}
	
	static {
		SLAB_ENTITY = createBlockEntity("double_slab_entity", DoubleSlabEntity::new, SlabrefSlabBlocks.DOUBLE_SLAB);
	}
}
