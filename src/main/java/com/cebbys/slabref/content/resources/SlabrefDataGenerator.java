package com.cebbys.slabref.content.resources;

import com.cebbys.slabref.Slabref;
import com.swordglowsblue.artifice.api.Artifice;

import net.minecraft.util.Identifier;

public class SlabrefDataGenerator {

	public static void generateData() {
		Artifice.registerDataPack(new Identifier(Slabref.MODID, "pack"), pack -> {
			pack.setDisplayName("Slabref Data");
			pack.setDescription("Default Slabref Data");
			pack.shouldOverwrite();

//			pack.addLootTable(SlabrefResUtilities.id("blocks/double_slab_block"), roll -> {
//				roll.type(new Identifier("block"));
////				DoubleSlabBlock.EXTEND.getValues().forEach(val -> {
////					appendPool(roll, val);
////				});
//			});
		});
	}

//	private static void appendPool(LootTableBuilder roll, Identifier slab) {
//		roll.pool(pool -> {
//			pool.rolls(1);
//			pool.entry(e -> {
//				e.type(new Identifier("item"));
//				e.condition(new Identifier("block_state_property"), s -> {
//					s.add("block", (new Identifier(Slabref.MODID, "double_slab_block").toString()));
//					s.addObject("properties", prop -> {
//						prop.add("extend", SlabrefResUtilities.getNameFromId(slab));
//					});
//				});
//				e.name(slab);
//			});
//		});
//		roll.pool(pool -> {
//			pool.rolls(1);
//			pool.entry(e -> {
//				e.type(new Identifier("item"));
//				e.condition(new Identifier("block_state_property"), s -> {
//					s.add("block", (new Identifier(Slabref.MODID, "double_slab_block").toString()));
//					s.addObject("properties", prop -> {
//						prop.add("base", SlabrefResUtilities.getNameFromId(slab));
//					});
//				});
//				e.name(slab);
//			});
//		});
//	}

}
