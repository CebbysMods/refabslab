package com.cebbys.slabref.utilities;

import java.util.ArrayList;
import com.cebbys.celib.loggers.CelibLogger;

import net.minecraft.block.SlabBlock;
import net.minecraft.util.registry.Registry;

public class SlabIdentifierLogger {

	public static void log(String modid) {
		ArrayList<String> strings = new ArrayList<String>();
		Registry.BLOCK.getEntries().forEach(entry -> {
			if(entry.getValue()	instanceof SlabBlock && entry.getKey().getValue().getNamespace() == modid) {
				strings.add(entry.getKey().getValue().toString());
			}
		});
		String msg = "{";
		boolean test = true;
		for(String s : strings) {
			if(test) {
				msg = msg + "\"" + s + "\"";
				test = false;
				continue;
			}
			msg = msg + ",\"" + s + "\"";
		}
		msg = msg + "}";
		CelibLogger.log(modid, msg);
	}
}
