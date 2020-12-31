package com.cebbys.slabref.support;

import java.util.HashSet;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import net.minecraft.util.Identifier;

public class BetterEndSupport {

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
				"betterend:flavolite_slab","betterend:flavolite_bricks_slab","betterend:violecite_slab",
				"betterend:violecite_bricks_slab","betterend:sulphuric_rock_slab","betterend:sulphuric_rock_bricks_slab",
				"betterend:mossy_glowshroom_slab","betterend:pythadendron_slab","betterend:end_lotus_slab",
				"betterend:lacugrove_slab","betterend:dragon_tree_slab","betterend:tenanea_slab","betterend:helix_tree_slab"
		};
		IDENTIFIERS = generateIdentifiers();
	}
}
