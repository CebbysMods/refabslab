package lv.cebbys.mcmods.refabslab.core.component.block;

import lv.cebbys.mcmods.refabslab.api.component.BlockApi;
import lv.cebbys.mcmods.refabslab.api.component.LevelApi;
import lv.cebbys.mcmods.refabslab.api.component.PlayerApi;
import lv.cebbys.mcmods.refabslab.api.component.blockstate.BlockStateApi;
import lv.cebbys.mcmods.refabslab.api.component.math.Vector3;

public class DoubleSlabBlock implements BlockApi {
    @Override
    public float getHardness(BlockStateApi top, BlockStateApi bottom, Vector3<Integer> pos, LevelApi level) {
        return Math.max(top.getHardness(pos, level), bottom.getHardness(pos, level));
    }

    @Override
    public float getMiningSpeed(BlockStateApi top, BlockStateApi bottom, PlayerApi player) {
        return Math.max(player.getMiningSpeed(top), player.getMiningSpeed(bottom));
    }

    @Override
    public float getHarvestSpeed(BlockStateApi top, BlockStateApi bottom, PlayerApi player) {
        var harvest = 100.0F;
        harvest -= player.hasCorrectToolForDrops(top) ? 35 : 0;
        harvest -= player.hasCorrectToolForDrops(bottom) ? 35 : 0;
        return harvest;
    }

    @Override
    public boolean canPlayerHarvest(BlockStateApi top, BlockStateApi bottom, PlayerApi player) {
        return player.hasCorrectToolForDrops(top) || player.hasCorrectToolForDrops(bottom);
    }
}
