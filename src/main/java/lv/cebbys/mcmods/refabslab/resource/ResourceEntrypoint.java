package lv.cebbys.mcmods.refabslab.resource;

import lv.cebbys.mcmods.refabslab.RefabslabCommon;
import lv.cebbys.mcmods.refabslab.content.block.DoubleSlabBlock;
import lv.cebbys.mcmods.respro.api.ResproRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.SharedConstants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.level.block.entity.BlockEntityType;

import static lv.cebbys.mcmods.refabslab.RefabslabCommon.MODID;

public class ResourceEntrypoint {

    public static void registerAssets() {
        ResproRegistry.registerAssets(assets -> {
            assets.setDumpMode(FabricLoader.getInstance().isDevelopmentEnvironment());
            assets.setPackId(new ResourceLocation(MODID, "assets"));
            assets.setPackProfile(profile -> {
                profile.setAlwaysEnabled(true);
                profile.setPackName(name -> name.setText("Refabslab Assets"));
                profile.setPackIcon(icon -> icon.setFromResources(RefabslabCommon.class, "assets/refabslab/icon.png"));
                profile.setPackMeta(meta -> {
                    meta.setDescription("Slab Assets");
                    meta.setFormat(PackType.CLIENT_RESOURCES.getVersion(SharedConstants.getCurrentVersion()));
                });
            });

            ResourceLocation modelId = new ResourceLocation(MODID, "block/double_slab_block");
            assets.setVariantBlockstate(new ResourceLocation(MODID, "double_slab_block"), variants -> {
                for (int i = 0; i < 16; i++) {
                    Integer lightLevel = i;
                    variants.setVariant(
                            property -> property.setProperty(DoubleSlabBlock.LIGHT_LEVEL, lightLevel),
                            variant -> variant.setModel(modelId)
                    );
                }
            });
        });
    }

    public static void registerData() {

    }
}
