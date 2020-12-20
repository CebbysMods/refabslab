package com.cebbys.slabref.content.resources;

import com.cebbys.slabref.Slabref;
import com.cebbys.slabref.content.blocks.properties.IdentifierProperty;

import net.minecraft.util.Identifier;

public class SlabrefResUtilities {

	protected static Identifier id(String name) {
		return new Identifier(Slabref.MODID, name);
	}

	protected static String getNameFromId(Identifier id) {
		return id.getNamespace() + IdentifierProperty.SPLIT + id.getPath();
	}

}
