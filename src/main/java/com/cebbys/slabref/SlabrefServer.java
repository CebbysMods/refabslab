package com.cebbys.slabref;

import com.cebbys.slabref.content.SlabrefSlabBlocks;
import com.cebbys.slabref.content.SlabrefSlabEntities;
import com.cebbys.slabref.content.resources.SlabrefDataGenerator;
import com.cebbys.slabref.events.SlabrefEventsServer;

public class SlabrefServer {

	protected static void initContent() {
		new SlabrefSlabBlocks();
		new SlabrefSlabEntities();
	}
	
	protected static void initData() {
		SlabrefDataGenerator.generateData();
	}
	
	protected static void initEvents() {
		SlabrefEventsServer.Initialize.doubleSlabCreatedEvent();
	}
}
