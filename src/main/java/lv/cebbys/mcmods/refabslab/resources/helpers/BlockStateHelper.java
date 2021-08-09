package lv.cebbys.mcmods.refabslab.resources.helpers;

import lv.cebbys.mcmods.celib.api.factories.CelibAssetPackFactory;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class BlockStateHelper {
    public static void addMinecraftWallBlockstate(CelibAssetPackFactory pack, Block wall) {
        pack.addVariantBlockState(wall, blockstate -> {
            Identifier wallId = Registry.BLOCK.getId(wall);
            Identifier slabId = new Identifier("block/" + wallId.getPath().replaceAll("wall_slab", "slab"));
            blockstate.variant("facing", "north", variant -> {
                variant.model(slabId);
                variant.y(180);
                variant.x(90);
                variant.uvlock(true);
            });
            blockstate.variant("facing", "south", variant -> {
                variant.model(slabId);
                variant.y(0);
                variant.x(90);
                variant.uvlock(true);
            });
            blockstate.variant("facing", "west", variant -> {
                variant.model(slabId);
                variant.y(270);
                variant.x(90);
                variant.uvlock(true);
            });
            blockstate.variant("facing", "east", variant -> {
                variant.model(slabId);
                variant.y(90);
                variant.x(90);
                variant.uvlock(true);
            });
        });
    }
}
