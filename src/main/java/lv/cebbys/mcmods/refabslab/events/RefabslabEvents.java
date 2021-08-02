package lv.cebbys.mcmods.refabslab.events;

import lv.cebbys.mcmods.refabslab.Refabslab;
import net.minecraft.util.Identifier;

public class RefabslabEvents {

	public static final Identifier IDENTIFIER_PACKET;
	
	static {
		IDENTIFIER_PACKET = new Identifier(Refabslab.MODID, "identifier_packet");
	}
}
