package lv.cebbys.mcmods.refabslab.content;

import lv.cebbys.mcmods.refabslab.Refabslab;
import lv.cebbys.mcmods.refabslab.content.entities.DoubleSlabEntity;
import lv.cebbys.mcmods.refabslab.content.entities.DoubleWallEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RefabslabEntities {

	public static final BlockEntityType<DoubleSlabEntity> SLAB_ENTITY;
	public static final BlockEntityType<DoubleWallEntity> WALL_ENTITY;

	static {
		SLAB_ENTITY = createBlockEntity("double_slab_entity", DoubleSlabEntity::new,
				RefabslabBlocks.DOUBLE_SLAB
		);
		WALL_ENTITY = createBlockEntity("double_wall_entity", DoubleWallEntity::new,
				RefabslabBlocks.DOUBLE_WALL
		);
	}

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
}
