package lv.cebbys.mcmods.refabslab;

import lv.cebbys.mcmods.refabslab.content.RefabslabBlocks;
import lv.cebbys.mcmods.refabslab.resource.ResourceEntrypoint;

public class RefabslabServer {

    protected static void initContent() {
        new RefabslabBlocks();
    }

    protected static void initData() {
        ResourceEntrypoint.registerData();
    }
}
