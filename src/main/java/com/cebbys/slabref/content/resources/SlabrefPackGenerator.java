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
				DoubleSlabBlock.TOP.getValues().forEach(top -> {
					appendBlockstate(state, top);
				});
				state.build();
			});
		});
	}

	@Environment(EnvType.CLIENT)
	private static void appendBlockstate(BlockStateBuilder state, Identifier id) {
		state.multipartCase(s -> {
			s.when("top", SlabrefResUtilities.getNameFromId(id)).apply(v -> {
				v.model(new Identifier(id.getNamespace(), "block/" + id.getPath() + "_top"));
			});
		});
		state.multipartCase(s -> {
			s.when("bottom", SlabrefResUtilities.getNameFromId(id)).apply(v -> {
				v.model(new Identifier(id.getNamespace(), "block/" + id.getPath()));
			});
		});
	}
}
