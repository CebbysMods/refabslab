package lv.cebbys.mcmods.refabslab.resources.helpers;

import lv.cebbys.mcmods.celib.respro.imp.builders.AssetPackBuilder;
import lv.cebbys.mcmods.celib.respro.imp.utilities.BlockProperty;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockStateHelper {
    public static void addMinecraftWallBlockstate(AssetPackBuilder pack, Block wall) {
        pack.addVariantBlockState(wall, s -> {
            Identifier wallId = Registry.BLOCK.getId(wall);
            Identifier slabId = new Identifier("block/" + wallId.getPath().replaceAll("wall_slab", "slab"));
            s.variant(new BlockProperty("facing", "north"), m -> m.model(slabId).y(180).x(90).uvlock(true));
            s.variant(new BlockProperty("facing", "south"), m -> m.model(slabId).y(0).x(90).uvlock(true));
            s.variant(new BlockProperty("facing", "west"), m -> m.model(slabId).y(270).x(90).uvlock(true));
            s.variant(new BlockProperty("facing", "east"), m -> m.model(slabId).y(90).x(90).uvlock(true));
        });
    }
}
