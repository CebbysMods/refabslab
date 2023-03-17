package lv.cebbys.mcmods.refabslab.content.model.provider;

import lv.cebbys.mcmods.refabslab.RefabslabCommon;
import lv.cebbys.mcmods.refabslab.content.model.DoubleSlabModel;
import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelResourceProvider;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class DoubleSlabModelProvider implements ModelResourceProvider {

    @Override
    public @Nullable UnbakedModel loadModelResource(ResourceLocation resourceId, ModelProviderContext context) {
        if (resourceId.equals(new ResourceLocation(RefabslabCommon.MODID, "block/double_slab_block"))) {
            return new DoubleSlabModel();
        }
        return null;
    }

}
