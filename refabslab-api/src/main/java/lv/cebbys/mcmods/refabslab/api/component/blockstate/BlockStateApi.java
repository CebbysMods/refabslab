package lv.cebbys.mcmods.refabslab.api.component.blockstate;

import lv.cebbys.mcmods.refabslab.api.component.LevelApi;
import lv.cebbys.mcmods.refabslab.api.component.math.Vector3;

public interface BlockStateApi {
    float getHardness(Vector3<Integer> pos, LevelApi level);
}
