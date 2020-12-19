package com.cebbys.slabref.registries;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.util.Identifier;

public class SlabRegistry {
	public static Set<Identifier> SLABS;
	
	static {
		SLABS = new HashSet<Identifier>();
	}
}
