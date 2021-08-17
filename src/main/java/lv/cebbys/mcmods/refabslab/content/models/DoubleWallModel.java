package lv.cebbys.mcmods.refabslab.content.models;

import lv.cebbys.mcmods.refabslab.content.blocks.WallBlock;
import lv.cebbys.mcmods.refabslab.content.entities.DoubleWallEntity;
import lv.cebbys.mcmods.refabslab.content.models.helpers.SimpleCombinedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.block.BlockModels;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.ModelBakeSettings;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.MultipartBakedModel;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

public class DoubleWallModel extends SimpleCombinedModel {

    private final SpriteIdentifier PARTICLE_ID = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier("block/cobblestone"));
    private final Direction.Axis axis;
    private Sprite PARTICLE;

    public DoubleWallModel(Direction.Axis modelAxis) {
        this.axis = modelAxis;
    }

    @Override
    public void emitBlockQuads(BlockRenderView view,
                               BlockState state,
                               BlockPos pos,
                               Supplier<Random> randomSupplier,
                               RenderContext context) {
        if (view.getBlockEntity(pos) instanceof DoubleWallEntity entity) {
            Identifier baseId = entity.getBase();
            Identifier extendId = entity.getExtend();
            if (baseId != null && extendId != null) {
                BlockModels models = MinecraftClient.getInstance().getBakedModelManager().getBlockModels();
                BlockState base = Registry.BLOCK.get(baseId).getDefaultState()
                        .with(WallBlock.FACING, Direction.from(this.axis, Direction.AxisDirection.NEGATIVE));
                BlockState extend = Registry.BLOCK.get(extendId).getDefaultState()
                        .with(WallBlock.FACING, Direction.from(this.axis, Direction.AxisDirection.POSITIVE));
                BakedModel baseModel = models.getModel(base);
                BakedModel extendModel = models.getModel(extend);
                MultipartBakedModel.Builder builder = new MultipartBakedModel.Builder();
                builder.addComponent(a -> true, baseModel);
                builder.addComponent(a -> true, extendModel);
                context.fallbackConsumer().accept(builder.build());
            }
        }
    }

    @Override
    public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context) {

    }

    @Override
    public Sprite getSprite() {
        return this.PARTICLE;
    }

    @Nullable
    @Override
    public BakedModel bake(ModelLoader loader,
                           Function<SpriteIdentifier, Sprite> textureGetter,
                           ModelBakeSettings rotationContainer,
                           Identifier modelId) {
        this.PARTICLE = textureGetter.apply(this.PARTICLE_ID);
        return this;
    }
}
