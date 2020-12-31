package com.cebbys.slabref.content.resources;

import com.cebbys.slabref.Slabref;
import com.cebbys.slabref.content.blocks.DoubleSlabBlock;
import com.swordglowsblue.artifice.api.Artifice;
import com.swordglowsblue.artifice.api.builder.assets.BlockStateBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

public class SlabrefPackGenerator {

	@Environment(EnvType.CLIENT)
	public static void generatePack() {
		Artifice.registerAssetPack(new Identifier(Slabref.MODID, "pack"), pack -> {
			pack.setDisplayName("Slabref Resources");
			pack.setDescription("Default Slabref Pack");
			pack.setVisible();

			pack.addBlockState(SlabrefResUtilities.id("double_slab_block"), state -> {
				DoubleSlabBlock.BASE.getValues().forEach(top -> {
					appendBlockstate(state, top);
				});
			});
		});
	}

	@Environment(EnvType.CLIENT)
	private static void appendBlockstate(BlockStateBuilder state, Identifier id) {
		state.multipartCase(s -> {
			s.when("extend", SlabrefResUtilities.getNameFromId(id)).apply(v -> {
				if(id.getNamespace().equals("betterend")) {
					v.model(new Identifier(id.getNamespace(), "block/" + id.getPath()));
					v.uvlock(true);
					v.rotationX(180);
				} else {
					v.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_top"));
				}
			});
		});
		state.multipartCase(s -> {
			s.when("base", SlabrefResUtilities.getNameFromId(id)).apply(v -> {
				v.model(new Identifier(id.getNamespace(), "block/" + id.getPath()));
			});
		});
	}
}
