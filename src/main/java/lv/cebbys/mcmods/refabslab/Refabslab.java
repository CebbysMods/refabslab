package lv.cebbys.mcmods.refabslab;

import lv.cebbys.mcmods.celib.utilities.CelibRegistrator;
import lv.cebbys.mcmods.refabslab.resources.ResourceEntrypoint;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

public class Refabslab implements ModInitializer, ClientModInitializer {

    public static final String MODID;
    public static final CelibRegistrator REGISTRY;

    static {
        MODID = "refabslab";
        REGISTRY = new CelibRegistrator(MODID);
    }

    @Override
    public void onInitialize() {
        RefabslabServer.initContent();
        RefabslabServer.initData();
        RefabslabServer.initEvents();
    }

    @Override
    public void onInitializeClient() {
        RefabslabClient.initResources();
        RefabslabClient.initProviders();
    }
}
