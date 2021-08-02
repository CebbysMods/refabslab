package lv.cebbys.mcmods.refabslab.resources;

import lv.cebbys.mcmods.celib.api.registries.CelibResourceRegistry;
import lv.cebbys.mcmods.refabslab.Refabslab;
import lv.cebbys.mcmods.refabslab.content.RefabslabSlabBlocks;
import net.minecraft.util.Identifier;

public class ResourceEntrypoint {

    public static void registerAssets() {
        CelibResourceRegistry.registerAssetPack(
                new Identifier(Refabslab.MODID, "assets"),
                "Refabricated Slabs Assets",
                pack -> {
                    pack.addPackMeta(mcmeta -> {
                        mcmeta.packFormat(7);
                        mcmeta.description("Refabricated Slabs Asset Pack");
                    });
                    pack.addPackIcon(icon -> {
                        icon.image(new Identifier(Refabslab.MODID, "textures/icon/icon.png"));
                    });

                    pack.addVariantBlockState(RefabslabSlabBlocks.DOUBLE_SLAB, (blockstate) -> {
                        blockstate.variant("", (variant) -> {
                            variant.model(new Identifier(Refabslab.MODID, "block/double_slab_block"));
                        });
                    });
                    pack.addVariantBlockState(RefabslabSlabBlocks.HAND_DOUBLE_SLAB, (blockstate) -> {
                        blockstate.variant("", (variant) -> {
                            variant.model(new Identifier(Refabslab.MODID, "block/double_slab_block"));
                        });
                    });
                });
    }

    public static void registerData() {
        CelibResourceRegistry.registerDataPack(
                new Identifier(Refabslab.MODID, "built_in_data"),
                "Refabricated Slabs Built-In Data",
                pack -> {
                    pack.addPackMeta(mcmeta -> {
                        mcmeta.packFormat(7);
                        mcmeta.description("Block tags and drops");
                    });

                    pack.addPackIcon(icon -> {
                        icon.image(new Identifier(Refabslab.MODID, "textures/icon/icon.png"));
                    });

//                    pack.addBlockTag(new Identifier("mineable/axe"), tag -> {
//                        tag.add("#" + Refabslab.MODID + ":doubleslabs/axe");
//                    });
//                    pack.addBlockTag(new Identifier("mineable/hoe"), tag -> {
//                        tag.add("#" + Refabslab.MODID + ":doubleslabs/hoe");
//                    });
//                    pack.addBlockTag(new Identifier("mineable/pickaxe"), tag -> {
//                        tag.add("#" + Refabslab.MODID + ":doubleslabs/pickaxe");
//                    });
//                    pack.addBlockTag(new Identifier("mineable/shovel"), tag -> {
//                        tag.add("#" + Refabslab.MODID + ":doubleslabs/shovel");
//                    });

//                    pack.addBlockTag(new Identifier(Refabslab.MODID + "doubleslabs/axe"), tag -> {
//                        RefabslabSlabBlocks.MAPPED_SLABS.get("axe").values().forEach(tag::add);
//                    });
//                    pack.addBlockTag(new Identifier(Refabslab.MODID + "doubleslabs/hoe"), tag -> {
//                        RefabslabSlabBlocks.MAPPED_SLABS.get("hoe").values().forEach(tag::add);
//                    });
//                    pack.addBlockTag(new Identifier(Refabslab.MODID + "doubleslabs/pickaxe"), tag -> {
//                        RefabslabSlabBlocks.MAPPED_SLABS.get("pickaxe").values().forEach(tag::add);
//                    });
//                    pack.addBlockTag(new Identifier(Refabslab.MODID + "doubleslabs/shovel"), tag -> {
//                        RefabslabSlabBlocks.MAPPED_SLABS.get("shovel").values().forEach(tag::add);
//                    });
                }
        );
    }
}
