package lv.cebbys.mcmods.refabslab.content;

import lv.cebbys.mcmods.refabslab.Refabslab;
import lv.cebbys.mcmods.refabslab.content.entities.DoubleSlabEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RefabslabSlabEntities {
	
	public static final BlockEntityType<DoubleSlabEntity> SLAB_ENTITY;
	
	private static <T extends BlockEntity> BlockEntityType<T> createBlockEntity(
			String entityName,
			FabricBlockEntityTypeBuilder.Factory<T> factory,
			Block... blocks
	) {
		return Registry.register(
				Registry.BLOCK_ENTITY_TYPE,
				new Identifier(Refabslab.MODID, entityName),
				FabricBlockEntityTypeBuilder.create(factory, blocks).build()
		);
	}
	
	static {
		SLAB_ENTITY = createBlockEntity("double_slab_entity", DoubleSlabEntity::new,
				RefabslabSlabBlocks.DOUBLE_SLAB,
				RefabslabSlabBlocks.HAND_DOUBLE_SLAB
		);
	}
}
