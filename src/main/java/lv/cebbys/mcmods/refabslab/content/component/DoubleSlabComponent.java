package lv.cebbys.mcmods.refabslab.content.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import lv.cebbys.mcmods.refabslab.utility.TransformUtils;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.chunk.Chunk;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class DoubleSlabComponent implements AutoSyncedComponent {
    private static final Identifier DEFAULT = new Identifier("minecraft:cobblestone_slab");
    private static final Pair<Identifier, Identifier> DEFAULT_PAIR = new Pair<>(DEFAULT, DEFAULT);

    private final Map<BlockPos, Pair<Identifier, Identifier>> queuedSlabs;
    private final Chunk chunk;

    public DoubleSlabComponent(Chunk c) {
        queuedSlabs = new HashMap<>();
        chunk = c;
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        if (!tag.contains("double_slab_queue")) return;
        NbtCompound compound = tag.getCompound("double_slab_queue");
        for (String pos : compound.getKeys()) {
            NbtCompound element = compound.getCompound(pos);
            queuedSlabs.put(TransformUtils.toBlockPos(pos), new Pair<>(
                    new Identifier(element.getString("top")),
                    new Identifier(element.getString("bottom"))
            ));
        }
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        NbtCompound compound = new NbtCompound();
        for (BlockPos pos : queuedSlabs.keySet()) {
            NbtCompound element = new NbtCompound();
            element.putString("top", queuedSlabs.get(pos).getLeft().toString());
            element.putString("bottom", queuedSlabs.get(pos).getRight().toString());
            compound.put(TransformUtils.toString(pos), element);
        }
        tag.put("double_slab_queue", compound);
    }

    @SuppressWarnings("all")
    public void setSlabIds(@Nullable BlockPos pos, @Nullable Identifier topId, @Nullable Identifier bottomId) {
        if(pos == null || topId == null || bottomId == null) return;
        queuedSlabs.put(pos, new Pair<>(topId, bottomId));
        chunk.setNeedsSaving(true);
    }

    @NotNull
    public Pair<Identifier, Identifier> getSlabIds(@Nullable BlockPos pos) {
        if(pos == null || !queuedSlabs.containsKey(pos)) return DEFAULT_PAIR;
        return queuedSlabs.get(pos);
    }

    @NotNull
    public Identifier getTopSlabId(@Nullable BlockPos pos) {
        return getSlabIds(pos).getLeft();
    }

    @NotNull
    public Identifier getBottomSlabId(@Nullable BlockPos pos) {
        return getSlabIds(pos).getRight();
    }

    @NotNull
    public Pair<BlockState, BlockState> getSlabStates(@Nullable BlockPos pos) {
        Pair<Identifier, Identifier> ids = getSlabIds(pos);
        return new Pair<>(
                Registry.BLOCK.get(ids.getLeft()).getDefaultState().with(SlabBlock.TYPE, SlabType.TOP),
                Registry.BLOCK.get(ids.getRight()).getDefaultState().with(SlabBlock.TYPE, SlabType.BOTTOM)
        );
    }

    @NotNull
    public BlockState getTopSlabState(@Nullable BlockPos pos) {
        return Registry.BLOCK.get(getSlabIds(pos).getLeft()).getDefaultState().with(SlabBlock.TYPE, SlabType.TOP);
    }

    @NotNull
    public BlockState getBottomSlabState(@Nullable BlockPos pos) {
        return Registry.BLOCK.get(getSlabIds(pos).getRight()).getDefaultState().with(SlabBlock.TYPE, SlabType.BOTTOM);
    }
}
