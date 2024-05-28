package lv.cebbys.mcmods.refabslab.content.model.multipart;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenCustomHashMap;
import net.minecraft.Util;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.MultiPartBakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class RefabslabBakedModel extends MultiPartBakedModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(RefabslabBakedModel.class);
    private final List<Pair<Predicate<BlockState>, BakedModel>> selectors;
    private final Map<BlockState, BitSet> selectorCache = new Object2ObjectOpenCustomHashMap(Util.identityStrategy());

    public RefabslabBakedModel(List<Pair<Predicate<BlockState>, BakedModel>> list) {
        super(list);
        selectors = list;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState blockState, @Nullable Direction direction, RandomSource randomSource) {
        if (blockState == null) {
            return Collections.emptyList();
        } else {
            BitSet bitSet = this.selectorCache.get(blockState);
            if (bitSet == null) {
                bitSet = new BitSet();

                for (int i = 0; i < this.selectors.size(); ++i) {
                    Pair<Predicate<BlockState>, BakedModel> pair = this.selectors.get(i);
//                    if (pair.getLeft().test(blockState)) {
//                        bitSet.set(i);
//                    }
                    bitSet.set(i);
                }

                this.selectorCache.put(blockState, bitSet);
            }

            List<BakedQuad> list = Lists.newArrayList();
            long l = randomSource.nextLong();

            for (int j = 0; j < bitSet.length(); ++j) {
                if (bitSet.get(j)) {
                    var selector = this.selectors.get(j);
                    var right = selector.getRight();
                    try {
                        var quads = right.getQuads(blockState, direction, RandomSource.create(l));
                        list.addAll(quads);
                    } catch (Exception e) {
                        LOGGER.error("Failed to load baked quads for '{}'", blockState, e);
                    }
                    try {
                        var quads = right.getQuads(blockState, direction, RandomSource.create(l));
                    } catch (Exception e) {

                    }
                }
            }

            return list;
        }
    }

    public static class Builder {
        private final List<Pair<Predicate<BlockState>, BakedModel>> selectors = Lists.newArrayList();

        public Builder() {
        }

        public void add(Predicate<BlockState> predicate, BakedModel bakedModel) {
            this.selectors.add(Pair.of(predicate, bakedModel));
        }

        public BakedModel build() {
            return new RefabslabBakedModel(this.selectors);
        }
    }
}
