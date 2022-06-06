package lv.cebbys.mcmods.refabslab.resources.helpers;

import lv.cebbys.mcmods.refabslab.utilities.IdentifierUtils;
import lv.cebbys.mcmods.refabslab.utilities.ModNamespace;
import lv.cebbys.mcmods.respro.imp.builders.assetpack.AssetPackBuilder;
import lv.cebbys.mcmods.respro.imp.component.BlockProperty;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MinecraftResourceHelper {

    public static void addWallAssets(AssetPackBuilder pack, Block wall) {

        Identifier wallId = Registry.BLOCK.getId(wall);
        Identifier itemId = Registry.ITEM.getId(wall.asItem());
        Identifier slabId = IdentifierUtils.wallIdToSlabId(ModNamespace.MINECRAFT, wallId);
        Identifier modelId = IdentifierUtils.prefix("block/", slabId);

        pack.addVariantBlockState(wall, s -> {
            s.variant(new BlockProperty("facing", "north"), v -> v.model(modelId).y(180).x(90).uvlock(true));
            s.variant(new BlockProperty("facing", "south"), v -> v.model(modelId).y(0).x(90).uvlock(true));
            s.variant(new BlockProperty("facing", "west"), v -> v.model(modelId).y(90).x(90).uvlock(true));
            s.variant(new BlockProperty("facing", "east"), v -> v.model(modelId).y(270).x(90).uvlock(true));
        });

        pack.addItemModel(itemId, m -> {
            m.parent(modelId);
            m.display(d -> {
                d.thirdPerson()
                        .rotation(-30, 0, 100)
                        .translation(-2F, 3.25F, 1.5F)
                        .scale(0.375F, 0.375F, 0.375F);
                d.firstPerson()
                        .rotation(90, 0, 100)
                        .translation(0F, 2F, 0F)
                        .scale(0.4F, 0.4F, 0.4F);
                d.ground()
                        .rotation(90, 0, 0)
                        .translation(0F, 3F, 0F)
                        .scale(0.25F, 0.25F, 0.25F);
                d.gui()
                        .rotation(120, 0, 45)
                        .translation(-1.75F, -0.75F, 0F)
                        .scale(0.625F, 0.625F, 0.625F);
                d.fixed()
                        .rotation(0, 0, -90)
                        .scale(0.5F, 0.5F, 0.5F);
            });
        });
    }
}
