package lv.cebbys.mcmods.refabslab;

import lv.cebbys.mcmods.refabslab.content.RefabslabSlabBlocks;
import lv.cebbys.mcmods.refabslab.content.RefabslabSlabEntities;
import lv.cebbys.mcmods.refabslab.events.RefabslabEventsServer;

public class RefabslabServer {

	protected static void initContent() {
		new RefabslabSlabBlocks();
		new RefabslabSlabEntities();
	}
	
	protected static void initData() {

	}
	
	protected static void initEvents() {
		RefabslabEventsServer.Initialize.doubleSlabCreatedEvent();
	}
}
