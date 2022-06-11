package lv.cebbys.mcmods.refabslab.content.model;

import lv.cebbys.mcmods.refabslab.content.component.DoubleSlabComponent;
import lv.cebbys.mcmods.refabslab.utility.TransformUtils;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.block.BlockModels;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.MultipartBakedModel.Builder;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;

import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

public class DoubleSlabModel extends AbstractDoubleSlabModel {
    private final SpriteIdentifier PARTICLE_ID = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("block/cobblestone"));
    private Sprite PARTICLE;

    @Override
    public BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter,
                           ModelBakeSettings rotationContainer, Identifier modelId) {
        this.PARTICLE = textureGetter.apply(this.PARTICLE_ID);
        return this;
    }

    @Override
    public void emitBlockQuads(BlockRenderView view, BlockState state, BlockPos pos, Supplier<Random> randomSupplier,
                               RenderContext context) {
        DoubleSlabComponent component;
        Builder builder = new Builder();
        if ((component = TransformUtils.toDoubleSlabComponent(view, pos)) == null) return;
        BlockModels models = MinecraftClient.getInstance().getBakedModelManager().getBlockModels();
        BakedModel baseModel = models.getModel(component.getBottomSlabState(pos));
        BakedModel extendModel = models.getModel(component.getTopSlabState(pos));
        builder.addComponent(a -> true, baseModel);
        builder.addComponent(a -> true, extendModel);
        context.fallbackConsumer().accept(builder.build());
    }

    @Override
    public Sprite getParticleSprite() {
        return this.PARTICLE;
    }

}
