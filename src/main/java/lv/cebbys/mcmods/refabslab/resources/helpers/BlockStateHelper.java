package lv.cebbys.mcmods.refabslab.resources.helpers;

import lv.cebbys.mcmods.celib.api.builders.CelibAssetPackBuilder;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockStateHelper {
    public static void addMinecraftWallBlockstate(CelibAssetPackBuilder pack, Block wall) {
        pack.variantBlockState(wall, s -> {
            Identifier wallId = Registry.BLOCK.getId(wall);
            Identifier slabId = new Identifier("block/" + wallId.getPath().replaceAll("wall_slab", "slab"));
            s.variant("facing", "north").model(slabId).y(180).x(90).uvlock(true);
            s.variant("facing", "south").model(slabId).y(0).x(90).uvlock(true);
            s.variant("facing", "west").model(slabId).y(270).x(90).uvlock(true);
            s.variant("facing", "east").model(slabId).y(90).x(90).uvlock(true);
        });
    }
}
