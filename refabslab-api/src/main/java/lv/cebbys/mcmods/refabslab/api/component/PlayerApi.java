package lv.cebbys.mcmods.refabslab.api.component;

import lv.cebbys.mcmods.refabslab.api.component.blockstate.BlockStateApi;

public interface PlayerApi {
    float getMiningSpeed(BlockStateApi blockstate);

    boolean hasCorrectToolForDrops(BlockStateApi blockstate);
}
