package lv.cebbys.mcmods.refabslab.events;

import lv.cebbys.mcmods.refabslab.Refabslab;
import net.minecraft.util.Identifier;

public class RefabslabEvents {

	public static final Identifier DOUBLE_SLAB_PLACED_EVENT;
	public static final Identifier DOUBLE_WALL_PLACED_EVENT;

	
	static {
		DOUBLE_SLAB_PLACED_EVENT = new Identifier(Refabslab.MODID, "double_slab_placed");
		DOUBLE_WALL_PLACED_EVENT = new Identifier(Refabslab.MODID, "double_wall_placed");
	}
}
