package com.cebbys.slabref.events;

import com.cebbys.slabref.Slabref;

import net.minecraft.util.Identifier;

public class SlabrefEvents {

	public static final Identifier IDENTIFIER_PACKET;
	
	static {
		IDENTIFIER_PACKET = new Identifier(Slabref.MODID, "identifier_packet");
	}
}
