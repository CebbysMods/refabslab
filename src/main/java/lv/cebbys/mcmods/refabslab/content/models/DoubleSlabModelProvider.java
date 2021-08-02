package lv.cebbys.mcmods.refabslab.content.models;

import lv.cebbys.mcmods.refabslab.Refabslab;
import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelProviderException;
import net.fabricmc.fabric.api.client.model.ModelResourceProvider;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class DoubleSlabModelProvider implements ModelResourceProvider {

	@Override
	public @Nullable UnbakedModel loadModelResource(Identifier resourceId, ModelProviderContext context) {
		if(resourceId.equals(new Identifier(Refabslab.MODID, "block/double_slab_block"))) {
			return new DoubleSlabModel();
		}
		return null;
	}

}
