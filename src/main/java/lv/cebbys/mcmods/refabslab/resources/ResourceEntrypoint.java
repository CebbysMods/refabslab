package lv.cebbys.mcmods.refabslab.resources;

import lv.cebbys.mcmods.celib.api.registries.CelibResourceRegistry;
import lv.cebbys.mcmods.refabslab.Refabslab;
import lv.cebbys.mcmods.refabslab.content.RefabslabBlocks;
import lv.cebbys.mcmods.refabslab.resources.helpers.MinecraftResourceHelper;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;

public class ResourceEntrypoint {

    public static void registerAssets() {
        CelibResourceRegistry.registerAssetPack(
                new Identifier(Refabslab.MODID, "assets"),
                "Refabricated Slabs Assets",
                pack -> {
                    pack.packMeta().packFormat(7).description("Refabricated Slabs Asset Pack");
                    pack.packIcon().path(new Identifier(Refabslab.MODID, "textures/icon/icon.png"));

                    pack.variantBlockState(RefabslabBlocks.DOUBLE_SLAB, b -> {
                        b.variant().model(new Identifier(Refabslab.MODID, "block/double_slab_block"));
                    });

                    pack.variantBlockState(RefabslabBlocks.DOUBLE_WALL, b -> {
                        b.variant("axis", "x").model(new Identifier(Refabslab.MODID, "block/double_wall_block_x"));
                        b.variant("axis", "z").model(new Identifier(Refabslab.MODID, "block/double_wall_block_z"));
                    });

                    for (Block wall : RefabslabBlocks.MINECRAFT.WALL_SLABS) {
                        MinecraftResourceHelper.addWallAssets(pack, wall);
                    }
                });
    }

    public static void registerData() {
        CelibResourceRegistry.registerDataPack(
                new Identifier(Refabslab.MODID, "built_in_data"),
                "Refabricated Slabs Built-In Data",
                pack -> {
//                    pack.addPackMeta(mcmeta -> {
//                        mcmeta.packFormat(7);
//                        mcmeta.description("Block tags and drops");
//                    });
//
//                    pack.addPackIcon(icon -> {
//                        icon.image(new Identifier(Refabslab.MODID, "textures/icon/icon.png"));
//                    });
                }
        );
    }
}
