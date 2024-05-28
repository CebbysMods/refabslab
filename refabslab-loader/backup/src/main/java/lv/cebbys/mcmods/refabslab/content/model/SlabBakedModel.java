package lv.cebbys.mcmods.refabslab.content.model;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class SlabBakedModel implements BakedModel {
    @NonNull
    private final Map<Direction, List<BakedQuad>> quads;
    @NonNull
    private final TextureAtlasSprite particle;

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState blockState, @Nullable Direction direction, RandomSource randomSource) {
        return quads.getOrDefault(direction, List.of());
    }

    @Override
    public boolean useAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean usesBlockLight() {
        return true;
    }

    @Override
    public boolean isCustomRenderer() {
        return true;
    }

    @Override
    public TextureAtlasSprite getParticleIcon() {
        return particle;
    }

    @Override
    public ItemTransforms getTransforms() {
        return ItemTransforms.NO_TRANSFORMS;
    }

    @Override
    public ItemOverrides getOverrides() {
        return ItemOverrides.EMPTY;
    }
}
