package lv.cebbys.mcmods.refabslab;

import lv.cebbys.mcmods.refabslab.content.RefabslabBlocks;
import lv.cebbys.mcmods.refabslab.content.RefabslabEntities;
import lv.cebbys.mcmods.refabslab.events.RefabslabEventsServer;

public class RefabslabServer {

    protected static void initContent() {
        new RefabslabBlocks();
        new RefabslabBlocks();
        new RefabslabEntities();
    }

    protected static void initData() {
    }

    protected static void initEvents() {
        RefabslabEventsServer.registerEventReceivers();
    }
}
