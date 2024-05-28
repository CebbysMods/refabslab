package lv.cebbys.mcmods.refabslab.core;

import lv.cebbys.mcmods.refabslab.api.entrypoint.RefabslabCommonInitializer;
import lv.cebbys.mcmods.refabslab.core.component.RefabslabBlocks;

public class RefabslabCommon implements RefabslabCommonInitializer {
    @Override
    public void onInitializeCommon() {
        RefabslabBlocks.initialize();
    }
}
