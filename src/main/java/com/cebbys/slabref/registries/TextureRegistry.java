package com.cebbys.slabref.registries;

import java.util.HashMap;
import net.minecraft.util.Identifier;

public class TextureRegistry {

	public static HashMap<Identifier, Identifier> TEXTURES;
	
	public static void initializeTexturePairs() {
		// Initializing plank pairs
		String[] minecraftPlanks = new String[] {
				"oak", "spruce", "birch", "jungle", "acacia", "dark_oak", "crimson", "warped"
		};
		for(int i = 0; i < minecraftPlanks.length; i++) {
			Identifier id0 = new Identifier(minecraftPlanks[i] + "_slab");
			Identifier id1 = new Identifier("block/" + minecraftPlanks[i] + "_planks");
			registerSlabTexture(id0, id1);
		}

		// Initializing basic blocks
		String[] minecraftBlocks = new String[] {
				"stone", "smooth_stone", "sandstone", "cut_sandstone", "cobblestone", "brick", "stone_brick"
		};
		for(int i = 0; i < minecraftBlocks.length; i++) {
			Identifier id0 = new Identifier(minecraftBlocks[i] + "_slab");
			Identifier id1 = new Identifier("block/" + minecraftBlocks[i]);
			registerSlabTexture(id0, id1);
		}
		
		// Initializing basic bricks
		String[] minecraftBricks = new String[] {
				"brick", "stone_brick"
		};
		for(int i = 0; i < minecraftBricks.length; i++) {
			Identifier id0 = new Identifier(minecraftBricks[i] + "_slab");
			Identifier id1 = new Identifier("block/" + minecraftBricks[i] + "s");
			registerSlabTexture(id0, id1);
		}
	}

	public static void registerSlabTexture(Identifier slab, Identifier texture) {
		TEXTURES.put(slab, texture);
	}

	static {
		TEXTURES = new HashMap<Identifier, Identifier>();
	}
}
