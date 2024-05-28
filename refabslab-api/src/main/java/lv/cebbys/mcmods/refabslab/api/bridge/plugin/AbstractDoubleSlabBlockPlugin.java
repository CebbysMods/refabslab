package lv.cebbys.mcmods.refabslab.api.bridge.plugin;

import lv.cebbys.mcmods.refabslab.api.component.BlockApi;

public abstract class AbstractDoubleSlabBlockPlugin<B> implements Plugin {
    @Override
    public final String identifier() {
        return "plugin:double_slab_block";
    }

    public abstract B createDoubleSlabBlock(BlockApi block);
}
