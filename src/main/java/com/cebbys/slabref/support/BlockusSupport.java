package com.cebbys.slabref.support;

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import net.minecraft.util.Identifier;

public class BlockusSupport {
	private static final ImmutableSet<Identifier> IDENTIFIERS;
	private static final String[] STRINGS;

	public static ImmutableSet<Identifier> getSlabIdentifiers() {
		return IDENTIFIERS;
	}
	
	private static ImmutableSet<Identifier> generateIdentifiers() {
		Set<Identifier> set = new HashSet<Identifier>();
		for(String s : STRINGS) {
			set.add(new Identifier(s));
		}
		return ImmutableSet.copyOf(set);
	}
	
	static {
		STRINGS = new String[] {
				"blockus:andesite_bricks_slab","blockus:diorite_bricks_slab","blockus:granite_bricks_slab",
				"blockus:crimson_warty_blackstone_bricks_slab","blockus:warped_warty_blackstone_bricks_slab",
				"blockus:limestone_slab","blockus:limestone_bricks_slab","blockus:marble_slab",
				"blockus:marble_bricks_slab","blockus:bluestone_slab","blockus:bluestone_tiles_slab",
				"blockus:smooth_bluestone_slab","blockus:lava_bricks_slab",
				"blockus:lava_polished_blackstone_slab","blockus:water_bricks_slab",
				"blockus:magma_bricks_slab","blockus:blaze_bricks_slab","blockus:polished_netherrack_slab",
				"blockus:charred_nether_bricks_slab","blockus:teal_nether_brick_slab",
				"blockus:obsidian_bricks_slab","blockus:soaked_bricks_slab","blockus:sandy_bricks_slab",
				"blockus:charred_bricks_slab","blockus:rough_sandstone_slab","blockus:sandstone_bricks_slab",
				"blockus:small_sandstone_bricks_slab","blockus:rough_red_sandstone_slab",
				"blockus:red_sandstone_bricks_slab","blockus:small_red_sandstone_bricks_slab",
				"blockus:soul_sandstone_slab","blockus:soul_sandstone_bricks_slab",
				"blockus:smooth_soul_sandstone_slab","blockus:rough_soul_sandstone_slab",
				"blockus:cut_soul_sandstone_slab","blockus:small_soul_sandstone_bricks_slab",
				"blockus:honeycomb_bricks_slab","blockus:smooth_purpur_slab","blockus:bamboo_slab",
				"blockus:charred_slab","blockus:white_oak_slab","blockus:rainbow_bricks_slab",
				"blockus:asphalt_slab","blockus:white_asphalt_slab","blockus:orange_asphalt_slab",
				"blockus:magenta_asphalt_slab","blockus:light_blue_asphalt_slab","blockus:yellow_asphalt_slab",
				"blockus:lime_asphalt_slab","blockus:pink_asphalt_slab","blockus:light_gray_asphalt_slab",
				"blockus:gray_asphalt_slab","blockus:cyan_asphalt_slab","blockus:purple_asphalt_slab",
				"blockus:blue_asphalt_slab","blockus:brown_asphalt_slab","blockus:green_asphalt_slab",
				"blockus:red_asphalt_slab","blockus:shingles_slab","blockus:white_shingles_slab",
				"blockus:orange_shingles_slab","blockus:magenta_shingles_slab",
				"blockus:light_blue_shingles_slab","blockus:yellow_shingles_slab","blockus:lime_shingles_slab",
				"blockus:pink_shingles_slab","blockus:light_gray_shingles_slab","blockus:gray_shingles_slab",
				"blockus:cyan_shingles_slab","blockus:purple_shingles_slab","blockus:blue_shingles_slab",
				"blockus:brown_shingles_slab","blockus:green_shingles_slab","blockus:red_shingles_slab",
				"blockus:black_shingles_slab","blockus:thatch_slab","blockus:netherite_slab"
		};
		IDENTIFIERS = generateIdentifiers();
	}
}
