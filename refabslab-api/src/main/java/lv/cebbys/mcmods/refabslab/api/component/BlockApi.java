package lv.cebbys.mcmods.refabslab.api.component;

import lv.cebbys.mcmods.refabslab.api.component.blockstate.BlockStateApi;
import lv.cebbys.mcmods.refabslab.api.component.math.Vector3;

public interface BlockApi {
    float getHardness(
            BlockStateApi top,
            BlockStateApi bottom,
            Vector3<Integer> pos,
            LevelApi level
    );

    float getMiningSpeed(
            BlockStateApi top,
            BlockStateApi bottom,
            PlayerApi player
    );

    float getHarvestSpeed(
            BlockStateApi top,
            BlockStateApi bottom,
            PlayerApi player
    );

    boolean canPlayerHarvest(
            BlockStateApi top,
            BlockStateApi bottom,
            PlayerApi player
    );
}
