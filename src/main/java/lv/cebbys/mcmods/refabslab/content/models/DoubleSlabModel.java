package lv.cebbys.mcmods.refabslab.content.models;

import lv.cebbys.mcmods.refabslab.content.entities.DoubleSlabEntity;
import lv.cebbys.mcmods.refabslab.content.models.helpers.SimpleCombinedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.block.BlockModels;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.MultipartBakedModel.Builder;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockRenderView;

import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

public class DoubleSlabModel extends SimpleCombinedModel {

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
        if (view.getBlockEntity(pos) instanceof DoubleSlabEntity entity) {
            Identifier b = entity.getBase();
            Identifier e = entity.getExtend();
            if (b != null && e != null) {
                BlockState base = Registry.BLOCK.get(b).getDefaultState();
                BlockState extend = Registry.BLOCK.get(e).getDefaultState();
                BlockModels models = MinecraftClient.getInstance().getBakedModelManager().getBlockModels();
                BakedModel baseModel = models.getModel(base.with(SlabBlock.TYPE, SlabType.BOTTOM));
                BakedModel extendModel = models.getModel(extend.with(SlabBlock.TYPE, SlabType.TOP));
                Builder builder = new Builder();
                builder.addComponent(a -> true, baseModel);
                builder.addComponent(a -> true, extendModel);
                context.fallbackConsumer().accept(builder.build());
//                view.getLightingProvider().addLightSource(pos, this.getLightLevel(base, extend));
            }
        }
    }

    @Override
    public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context) {

    }

    private int getLightLevel(BlockState b0, BlockState b1) {
        return Math.max(b0.getLuminance(), b1.getLuminance());
    }

    @Override
    public Sprite getParticleSprite() {
        return this.PARTICLE;
    }
}
