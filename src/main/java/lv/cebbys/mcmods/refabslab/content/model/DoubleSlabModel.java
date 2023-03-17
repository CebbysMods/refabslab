package lv.cebbys.mcmods.refabslab.content.model;

import lv.cebbys.mcmods.refabslab.content.RefabslabComponents;
import lv.cebbys.mcmods.refabslab.content.model.multipart.RefabslabBakedModel;
import lv.cebbys.mcmods.refabslab.locator.ChunkLocator;
import lv.cebbys.mcmods.refabslab.utility.TransformUtils;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockModelShaper;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.checkerframework.checker.units.qual.A;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class DoubleSlabModel extends AbstractDoubleSlabModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(DoubleSlabModel.class);

    private static final Map<BlockState, Map<BlockState, BakedModel>> MODEL_CACHE;
    private static final BlockState DEFAULT_TOP_STATE;
    private static final BlockState DEFAULT_BOTTOM_STATE;
    private final Material PARTICLE_ID = new Material(TextureAtlas.LOCATION_BLOCKS, new ResourceLocation("block/cobblestone"));
    private TextureAtlasSprite PARTICLE;


    @Override
    public BakedModel bake(
            ModelBakery loader,
            Function<Material, TextureAtlasSprite> textureGetter,
            ModelState rotationContainer, ResourceLocation modelId
    ) {
        this.PARTICLE = textureGetter.apply(this.PARTICLE_ID);
        return this;
    }

    @Override
    public void emitBlockQuads(
            BlockAndTintGetter view,
            BlockState state,
            BlockPos pos,
            Supplier<RandomSource> randomSupplier,
            RenderContext context
    ) {
        var combination = getSlabCombination(view, pos);
        var topState = combination.getKey();
        var bottomState = combination.getValue();

        if (!MODEL_CACHE.containsKey(topState)) {
            MODEL_CACHE.put(topState, new HashMap<>());
        }
        if (!MODEL_CACHE.get(topState).containsKey(bottomState)) {
            var random = randomSupplier.get();
            var models = Minecraft.getInstance().getModelManager().getBlockModelShaper();
            var baseQuads = getQuads(models, bottomState, random);
            var extendQuads = getQuads(models, topState, random);
            var quads = combineQuads(baseQuads, extendQuads);
            var model = new SlabBakedModel(quads, getParticleIcon());
            MODEL_CACHE.get(topState).put(bottomState, model);
        }

        context.fallbackConsumer().accept(MODEL_CACHE.get(topState).get(bottomState));
    }

    private Map<Direction, List<BakedQuad>> combineQuads(
            Map<Direction, List<BakedQuad>> baseQuads,
            Map<Direction, List<BakedQuad>> extendQuads
    ) {
        var defaultList = new ArrayList<BakedQuad>();
        var out = new HashMap<Direction, List<BakedQuad>>();
        for (var dir : Direction.values()) {
            var combined = new ArrayList<BakedQuad>();
            combined.addAll(baseQuads.getOrDefault(dir, defaultList));
            combined.addAll(extendQuads.getOrDefault(dir, defaultList));
            out.put(dir, combined);
        }
        return out;
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return this.PARTICLE;
    }

    @Override
    public List<BakedQuad> getQuads(BlockState state, Direction face, RandomSource random) {
        return super.getQuads(state, face, random);
    }

    private Map<Direction, List<BakedQuad>> getQuads(BlockModelShaper shaper, BlockState state, RandomSource source) {
        if (shaper == null || state == null) {
            return Map.of();
        }
        try {
            var map = new HashMap<Direction, List<BakedQuad>>();
            var model = shaper.getBlockModel(state);
            for (var dir : Direction.values()) {
                var quads = model.getQuads(state, dir, source);
                map.put(dir, quads);
            }
            return map;
        } catch (Exception e) {
            LOGGER.error("Failed to load quads for '{}' blockstate", state, e);
        }
        return Map.of();
    }

    private Pair<BlockState, BlockState> getSlabCombination(BlockAndTintGetter view, BlockPos pos) {
        BlockState top = null;
        BlockState bottom = null;
        var chunk = ChunkLocator.getChunk(view, pos);
        if (chunk == null) {
            LOGGER.error("Failed to load DoubleSlab chunk component as chunk provided was 'null'");
        } else {
            var component = RefabslabComponents.DOUBLE_SLAB_QUEUE.getNullable(chunk);
            if (component == null) {
                LOGGER.error("Failed to load DoubleSlab chunk component as component retrieved was 'null'");
            } else {
                var combination = component.getSlabBlockStateCombination(pos);
                top = combination.getKey();
                bottom = combination.getValue();
            }
        }
        if (top == null) {
            LOGGER.warn("Using '{}' as default top slab state", DEFAULT_TOP_STATE);
            top = DEFAULT_TOP_STATE;
        }
        if (bottom == null) {
            LOGGER.warn("Using '{}' as default bottom slab state", DEFAULT_TOP_STATE);
            bottom = DEFAULT_TOP_STATE;
        }
        return new ImmutablePair<>(top, bottom);
    }

    static {
        MODEL_CACHE = new HashMap<>();
        DEFAULT_TOP_STATE = Blocks.COBBLESTONE_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.TOP);
        DEFAULT_BOTTOM_STATE = Blocks.COBBLESTONE_SLAB.defaultBlockState().setValue(SlabBlock.TYPE, SlabType.BOTTOM);
    }
}
