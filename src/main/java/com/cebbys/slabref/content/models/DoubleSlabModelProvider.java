package com.cebbys.slabref.content.models;

import org.jetbrains.annotations.Nullable;

import com.cebbys.slabref.Slabref;

import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelProviderException;
import net.fabricmc.fabric.api.client.model.ModelResourceProvider;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.util.Identifier;

public class DoubleSlabModelProvider implements ModelResourceProvider {

	@Override
	public @Nullable UnbakedModel loadModelResource(Identifier resourceId, ModelProviderContext context)
			throws ModelProviderException {
		if(resourceId.equals(new Identifier(Slabref.MODID, "double_slab"))) {
			return new DoubleSlabModel();
		}
		return null;
	}

}
