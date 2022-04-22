package lv.cebbys.mcmods.refabslab.resources;

import lv.cebbys.mcmods.celib.respro.api.ResproRegistry;
import lv.cebbys.mcmods.celib.respro.imp.utilities.BlockProperty;
import lv.cebbys.mcmods.refabslab.Refabslab;
import lv.cebbys.mcmods.refabslab.content.RefabslabBlocks;
import lv.cebbys.mcmods.refabslab.resources.helpers.MinecraftResourceHelper;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;

public class ResourceEntrypoint {

    public static void registerAssets() {
        ResproRegistry.registerAssetPack(new Identifier(Refabslab.MODID, "assets"), pack -> {
            pack.setPackName("Refabslab Assets");
            pack.setPackIcon(new Identifier(Refabslab.MODID, "textures/icon/icon.png"));
            pack.setPackMeta(meta -> meta.packFormat(7).description("Slab Assets"));

            pack.addVariantBlockState(RefabslabBlocks.DOUBLE_SLAB, state -> {
                state.variant(v -> v.model(new Identifier(Refabslab.MODID, "block/double_slab_block")));
            });

            pack.addVariantBlockState(RefabslabBlocks.DOUBLE_WALL, state -> {
                state.variant(
                        new BlockProperty("axis", "x"),
                        b -> b.model(new Identifier(Refabslab.MODID, "block/double_wall_block_x")));
                state.variant(
                        new BlockProperty("axis", "z"),
                        b -> b.model(new Identifier(Refabslab.MODID, "block/double_wall_block_z")));
            });

            for (Block wall : RefabslabBlocks.MINECRAFT.WALL_SLABS) {
                MinecraftResourceHelper.addWallAssets(pack, wall);
            }
        });
    }

    public static void registerData() {
//        CelibResourceRegistry.registerDataPack(
//                new Identifier(Refabslab.MODID, "built_in_data"),
//                "Refabricated Slabs Built-In Data",
//                pack -> {
////                    pack.addPackMeta(mcmeta -> {
////                        mcmeta.packFormat(7);
////                        mcmeta.description("Block tags and drops");
////                    });
////
////                    pack.addPackIcon(icon -> {
////                        icon.image(new Identifier(Refabslab.MODID, "textures/icon/icon.png"));
////                    });
//                }
//        );
    }
}
