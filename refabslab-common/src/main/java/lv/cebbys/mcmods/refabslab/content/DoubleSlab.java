package lv.cebbys.mcmods.refabslab.content;

import lv.cebbys.mcmods.modularity.api.Modularity;
import lv.cebbys.mcmods.modularity.api.block.BlockStateRef;
import lv.cebbys.mcmods.modularity.api.block.BlockType;
import lv.cebbys.mcmods.modularity.api.block.property.SlabType;
import lv.cebbys.mcmods.modularity.api.block.property.VanillaProperties;
import lv.cebbys.mcmods.modularity.api.core.Id;
import lv.cebbys.mcmods.modularity.api.data.DataCodec;

import java.util.Objects;
import java.util.Optional;

/** The two slabs a double slab block is made of. */
public record DoubleSlab(Id top, Id bottom) {
    /** Used when data is missing or a slab no longer exists (mod removed). */
    public static final Id FALLBACK = Id.minecraft("cobblestone_slab");
    public static final DoubleSlab DEFAULT = new DoubleSlab(FALLBACK, FALLBACK);

    public static final DataCodec<DoubleSlab> CODEC = DataCodec.of(
            (slab, out) -> out.putId("top", slab.top()).putId("bottom", slab.bottom()),
            in -> new DoubleSlab(
                    in.has("top") ? in.getId("top") : FALLBACK,
                    in.has("bottom") ? in.getId("bottom") : FALLBACK));

    public DoubleSlab {
        Objects.requireNonNull(top, "top");
        Objects.requireNonNull(bottom, "bottom");
    }

    public static DoubleSlab of(BlockStateRef top, BlockStateRef bottom) {
        return new DoubleSlab(top.block().id(), bottom.block().id());
    }

    public static DoubleSlab orDefault(Optional<DoubleSlab> data) {
        return data.orElse(DEFAULT);
    }

    /** The single slab forming the given half ({@code TOP} or {@code BOTTOM}). */
    public BlockStateRef half(SlabType type) {
        return switch (type) {
            case TOP -> topState();
            case BOTTOM -> bottomState();
            case DOUBLE -> throw new IllegalArgumentException("A half is either TOP or BOTTOM");
        };
    }

    public BlockStateRef topState() {
        return slab(top, SlabType.TOP);
    }

    public BlockStateRef bottomState() {
        return slab(bottom, SlabType.BOTTOM);
    }

    private static BlockStateRef slab(Id id, SlabType type) {
        var block = Modularity.block(id)
                .filter(b -> b.is(BlockType.SLAB))
                .or(() -> Modularity.block(FALLBACK))
                .orElseThrow(() -> new IllegalStateException("Fallback slab " + FALLBACK + " is missing"));
        return block.defaultState().with(VanillaProperties.SLAB_TYPE, type);
    }
}
