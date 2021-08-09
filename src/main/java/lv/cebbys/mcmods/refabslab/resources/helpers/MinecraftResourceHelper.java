package lv.cebbys.mcmods.refabslab.resources.helpers;

import lv.cebbys.mcmods.celib.api.factories.CelibAssetPackFactory;
import lv.cebbys.mcmods.celib.api.factories.CelibDataPackFactory;
import lv.cebbys.mcmods.refabslab.content.blocks.WallBlock;
import lv.cebbys.mcmods.refabslab.utilities.IdentifierUtils;
import lv.cebbys.mcmods.refabslab.utilities.ModNamespace;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MinecraftResourceHelper {

    public static void addWallAssets(CelibAssetPackFactory pack, Block wall) {

        Identifier wallId = Registry.BLOCK.getId(wall);
        Identifier itemId = Registry.ITEM.getId(wall.asItem());
        Identifier slabId = IdentifierUtils.wallIdToSlabId(ModNamespace.MINECRAFT, wallId);
        Identifier modelId = IdentifierUtils.prefix("block/", slabId);

        pack.addVariantBlockState(wall, blockstate -> {
            blockstate.variant("facing", "north", variant -> {
                variant.model(modelId);
                variant.y(180);
                variant.x(90);
                variant.uvlock(true);
            });
            blockstate.variant("facing", "south", variant -> {
                variant.model(modelId);
                variant.y(0);
                variant.x(90);
                variant.uvlock(true);
            });
            blockstate.variant("facing", "west", variant -> {
                variant.model(modelId);
                variant.y(270);
                variant.x(90);
                variant.uvlock(true);
            });
            blockstate.variant("facing", "east", variant -> {
                variant.model(modelId);
                variant.y(90);
                variant.x(90);
                variant.uvlock(true);
            });
        });

        pack.addItemModel(itemId, model -> {
            model.parent(modelId);
            model.display(display -> {
                display.thirdPerson(element -> {
                    element.rotation(-30, 0, 100);
                    element.translation(-2F, 3.25F, 1.5F);
                    element.scale(0.375F, 0.375F, 0.375F);
                });
                display.firstPerson(element -> {
                    element.rotation(90, 0, 100);
                    element.translation(0F, 2F, 0F);
                    element.scale(0.4F, 0.4F, 0.4F);
                });
                display.ground(element -> {
                    element.rotation(90, 0, 0);
                    element.translation(0F, 3F, 0F);
                    element.scale(0.25F, 0.25F, 0.25F);
                });
                display.gui(element -> {
                    element.rotation(120, 0, 45);
                    element.translation(-1.75F, -0.75F, 0F);
                    element.scale(0.625F, 0.625F, 0.625F);
                });
                display.ground(element -> {
                    element.rotation(0, 0, -90);
                    element.scale(0.5F, 0.5F, 0.5F);
                });
            });
        });
    }

    public static void addWallData(CelibDataPackFactory pack, WallBlock wall) {

    }
}
