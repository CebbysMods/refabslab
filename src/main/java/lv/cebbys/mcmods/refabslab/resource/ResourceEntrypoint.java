package lv.cebbys.mcmods.refabslab.resource;

import com.mojang.bridge.game.PackType;
import lv.cebbys.mcmods.respro.api.ResproRegistry;
import net.minecraft.SharedConstants;
import net.minecraft.util.Identifier;

import static lv.cebbys.mcmods.refabslab.Refabslab.MODID;

public class ResourceEntrypoint {

    public static void registerAssets() {
        ResproRegistry.registerAssetPack(new Identifier(MODID, "assets"), pack -> {
            pack.addPackName("Refabslab Assets");
            pack.addPackIcon("assets/refabslab/icon.png");
            pack.addPackMeta(meta -> {
                meta.packFormat(SharedConstants.getGameVersion().getPackVersion(PackType.RESOURCE));
                meta.description("Slab assets");
            });

            pack.addVariantBlockState(new Identifier(MODID, "double_slab_block"), state -> {
                state.variant(v -> v.model(new Identifier(MODID, "block/double_slab_block")));
            });
        });
    }

    public static void registerData() {
        
    }
}
