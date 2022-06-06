package lv.cebbys.mcmods.refabslab.resources;

import com.mojang.bridge.game.PackType;
import lv.cebbys.mcmods.refabslab.content.RefabslabBlocks;
import lv.cebbys.mcmods.refabslab.resources.helpers.MinecraftResourceHelper;
import lv.cebbys.mcmods.respro.api.ResproRegistry;
import lv.cebbys.mcmods.respro.imp.component.BlockProperty;
import net.minecraft.SharedConstants;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;

import static lv.cebbys.mcmods.refabslab.Refabslab.MODID;

public class ResourceEntrypoint {

    public static void registerAssets() {
        ResproRegistry.registerAssetPack(new Identifier(MODID, "assets"), pack -> {
            pack.addPackName("Refabslab Assets");
            pack.addPackIcon("assets/refabslab/icon.png");
            pack.addPackMeta(meta -> {
                meta.packFormat(SharedConstants.getGameVersion().getPackVersion(PackType.RESOURCE));
                meta.description("Slab and wall assets");
            });

            pack.addVariantBlockState(new Identifier(MODID, "double_slab_block"), state -> {
                state.variant(v -> v.model(new Identifier(MODID, "block/double_slab_block")));
            });

            pack.addVariantBlockState(new Identifier(MODID, "double_wall_block"), state -> {
                state.variant(
                        new BlockProperty("axis", "x"),
                        b -> b.model(new Identifier(MODID, "block/double_wall_block_x"))
                );
                state.variant(
                        new BlockProperty("axis", "z"),
                        b -> b.model(new Identifier(MODID, "block/double_wall_block_z"))
                );
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
