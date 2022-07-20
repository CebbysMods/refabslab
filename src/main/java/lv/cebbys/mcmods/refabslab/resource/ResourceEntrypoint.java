package lv.cebbys.mcmods.refabslab.resource;

import lv.cebbys.mcmods.refabslab.Refabslab;
import lv.cebbys.mcmods.refabslab.content.block.DoubleSlabBlock;
import lv.cebbys.mcmods.respro.api.ResproRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.SharedConstants;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import static lv.cebbys.mcmods.refabslab.Refabslab.MODID;

public class ResourceEntrypoint {

    public static void registerAssets() {
        ResproRegistry.registerAssets(assets -> {
            assets.setDumpMode(FabricLoader.getInstance().isDevelopmentEnvironment());
            assets.setPackId(new Identifier(MODID, "assets"));
            assets.setPackProfile(profile -> {
                profile.setAlwaysEnabled(true);
                profile.setPackName(name -> name.setText("Refabslab Assets"));
                profile.setPackIcon(icon -> icon.setFromResources(Refabslab.class, "assets/refabslab/icon.png"));
                profile.setPackMeta(meta -> {
                    meta.setDescription("Slab Assets");
                    meta.setFormat(ResourceType.CLIENT_RESOURCES.getPackVersion(SharedConstants.getGameVersion()));
                });
            });

            Identifier modelId = new Identifier(MODID, "block/double_slab_block");
            assets.setVariantBlockstate(new Identifier(MODID, "double_slab_block"), variants -> {
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
