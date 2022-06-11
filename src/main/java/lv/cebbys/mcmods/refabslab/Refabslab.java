package lv.cebbys.mcmods.refabslab;

import lv.cebbys.mcmods.celib.utilities.CelibRegistrator;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

public class Refabslab implements ModInitializer, ClientModInitializer {

    public static final String MODID;
    public static final CelibRegistrator REGISTRY;

    @Override
    public void onInitialize() {
        RefabslabServer.initContent();
        RefabslabServer.initData();
    }

    @Override
    public void onInitializeClient() {
        RefabslabClient.initResources();
        RefabslabClient.initProviders();
    }

    static {
        MODID = "refabslab";
        REGISTRY = new CelibRegistrator(MODID);
    }
}
