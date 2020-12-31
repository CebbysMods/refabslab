package com.cebbys.slabref.registries;

import java.util.HashSet;
import java.util.Set;

import com.cebbys.slabref.support.BetterEndSupport;
import com.cebbys.slabref.support.BlockusSupport;
import com.cebbys.slabref.support.MinecraftSupport;
import com.google.common.collect.ImmutableSet;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SlabRegistry {
	private static final ImmutableSet<Identifier> IDENTIFIERS;

	public static ImmutableSet<Identifier> getSlabIdentifiers() {
		return IDENTIFIERS;
	}

	private static ImmutableSet<Identifier> registerIdentifiers() {
		Set<Identifier> ids = new HashSet<Identifier>();

		ids.addAll(MinecraftSupport.getSlabIdentifiers());

		if (FabricLoader.getInstance().isModLoaded("betterend")) {
			ids.addAll(BetterEndSupport.getSlabIdentifiers());
		}
		if (FabricLoader.getInstance().isModLoaded("blockus")) {
			ids.addAll(BlockusSupport.getSlabIdentifiers());
		}

		return ImmutableSet.copyOf(ids);
	}

	public static boolean contains(Identifier id) {
		return IDENTIFIERS.contains(id);
	}

	public static boolean contains(Block block) {
		return IDENTIFIERS.contains(Registry.BLOCK.getId(block));
	}

	public static boolean contains(Block... blocks) {
		for (Block block : blocks) {
			if (!IDENTIFIERS.contains(Registry.BLOCK.getId(block))) {
				return false;
			}
		}
		return true;
	}

	static {
		IDENTIFIERS = registerIdentifiers();
	}
}
