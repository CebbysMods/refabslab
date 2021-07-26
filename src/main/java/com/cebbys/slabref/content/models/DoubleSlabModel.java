package com.cebbys.slabref.content.models;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import com.cebbys.slabref.content.entities.DoubleSlabEntity;
import com.mojang.datafixers.util.Pair;

import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.block.BlockModels;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.MultipartBakedModel;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.render.model.MultipartBakedModel.Builder;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockRenderView;

public class DoubleSlabModel implements BakedModel, FabricBakedModel, UnbakedModel {
	
	private SpriteIdentifier PARTICLE_ID = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("block/cobblestone"));
	private Sprite PARTICLE;

	@Override
	public Collection<Identifier> getModelDependencies() {
		return Collections.emptyList();
	}

	@Override
	public Collection<SpriteIdentifier> getTextureDependencies(Function<Identifier, UnbakedModel> unbakedModelGetter,
			Set<Pair<String, String>> unresolvedTextureReferences) {
		return Collections.emptyList();
	}

	@Override
	public BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter,
			ModelBakeSettings rotationContainer, Identifier modelId) {
		this.PARTICLE = textureGetter.apply(this.PARTICLE_ID);
		return this;
	}

	@Override
	public boolean isVanillaAdapter() {
		return false;
	}

	@Override
	public void emitBlockQuads(BlockRenderView view, BlockState state, BlockPos pos, Supplier<Random> randomSupplier,
			RenderContext context) {
		DoubleSlabEntity entity = ((DoubleSlabEntity) view.getBlockEntity(pos));
		Identifier b = entity.getBase();
		Identifier e = entity.getExtend();
		if (b != null && e != null) {
			BlockState base = Registry.BLOCK.get(b).getDefaultState();
			BlockState extend = Registry.BLOCK.get(e).getDefaultState();
			BlockModels models = MinecraftClient.getInstance().getBakedModelManager().getBlockModels();
			BakedModel baseModel = models.getModel(base.with(SlabBlock.TYPE, SlabType.BOTTOM));
			BakedModel extendModel = models.getModel(extend.with(SlabBlock.TYPE, SlabType.TOP));
			Builder builder = new MultipartBakedModel.Builder();
			builder.addComponent(a -> true, baseModel);
			builder.addComponent(a -> true, extendModel);
			context.fallbackConsumer().accept(builder.build());
			view.getLightingProvider().addLightSource(pos, this.getLightLevel(base, extend));
		}
	}
	
	private int getLightLevel(BlockState b0, BlockState b1) {
		return Math.max(b0.getLuminance(), b1.getLuminance());
	}

	@Override
	public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context) {

	}

	@Override
	public List<BakedQuad> getQuads(BlockState state, Direction face, Random random) {
		return null;
	}

	@Override
	public boolean useAmbientOcclusion() {
		return true;
	}

	@Override
	public boolean hasDepth() {
		return false;
	}

	@Override
	public boolean isSideLit() {
		return false;
	}

	@Override
	public boolean isBuiltin() {
		return false;
	}

	@Override
	public Sprite getSprite() {
		return this.PARTICLE;
	}

	@Override
	public ModelTransformation getTransformation() {
		return ModelTransformation.NONE;
	}

	@Override
	public ModelOverrideList getOverrides() {
		return ModelOverrideList.EMPTY;
	}

}
