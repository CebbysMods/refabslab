package lv.cebbys.mcmods.refabslab.content.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import lombok.NonNull;
import lv.cebbys.mcmods.refabslab.utility.TransformUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class DoubleSlabComponent implements AutoSyncedComponent {
    private static final Logger LOGGER = LoggerFactory.getLogger(DoubleSlabComponent.class);
    private static final String KEY_DOUBLE_SLAB_CONTEXT;
    private static final String KEY_BOTTOM;
    private static final String KEY_TOP;
    private static final SlabBlock DEFAULT_BLOCK;
    private static final ResourceLocation DEFAULT_LOCATION;
    private static final Pair<ResourceLocation, ResourceLocation> DEFAULT_LOCATION_PAIR;

    private final Map<BlockPos, Pair<ResourceLocation, ResourceLocation>> context;
    private final ChunkAccess chunk;

    public DoubleSlabComponent(ChunkAccess c) {
        context = new HashMap<>();
        chunk = c;
    }

    public void pushSlabCombination(BlockPos pos, ResourceLocation top, ResourceLocation bottom) {
        if (top == null) {
            LOGGER.warn("DoubleSlab top slab identifier is 'null', using default '{}'", DEFAULT_LOCATION);
            top = DEFAULT_LOCATION;
        }
        if (bottom == null) {
            LOGGER.warn("DoubleSlab bottom slab identifier is 'null', using default '{}'", DEFAULT_LOCATION);
            bottom = DEFAULT_LOCATION;
        }
        context.put(pos, new ImmutablePair<>(top, bottom));
        chunk.setUnsaved(true);
    }

    @NonNull
    public Pair<BlockState, BlockState> getSlabBlockStateCombination(BlockPos pos) {
        var identifiers = getSlabCombination(pos);
        var top = getSlabBlockStateFromResourceLocation(identifiers.getKey(), SlabType.TOP);
        var bottom = getSlabBlockStateFromResourceLocation(identifiers.getValue(), SlabType.BOTTOM);
        return new ImmutablePair<>(top, bottom);
    }

    public Pair<ResourceLocation, ResourceLocation> popSlabCombination(BlockPos pos) {
        if (!context.containsKey(pos)) {
            LOGGER.warn(
                    "Chunk context does not have information about double slab in position '{}', returning default '{}'",
                    pos, DEFAULT_LOCATION_PAIR
            );
            return DEFAULT_LOCATION_PAIR;
        }
        var result = context.remove(pos);
        chunk.setUnsaved(true);
        return result;
    }

    @Override
    public void readFromNbt(CompoundTag tag) {
        if (!tag.contains(KEY_DOUBLE_SLAB_CONTEXT)) return;
        CompoundTag compound = tag.getCompound(KEY_DOUBLE_SLAB_CONTEXT);
        for (String pos : compound.getAllKeys()) {
            var element = compound.getCompound(pos);
            var blockPos = TransformUtils.toBlockPos(pos);
            var topLocation = new ResourceLocation(element.getString(KEY_TOP));
            var bottomLocation = new ResourceLocation(element.getString(KEY_BOTTOM));
            context.put(blockPos, new ImmutablePair<>(topLocation, bottomLocation));
        }
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        CompoundTag compound = new CompoundTag();
        context.forEach((var blockPos, var combination) -> {
            var element = new CompoundTag();
            var top = combination.getKey().toString();
            var bottom = combination.getValue().toString();
            element.putString(KEY_TOP, top);
            element.putString(KEY_BOTTOM, bottom);
            var key = TransformUtils.toString(blockPos);
            compound.put(key, element);
        });
        tag.put(KEY_DOUBLE_SLAB_CONTEXT, compound);
    }


    private @NonNull BlockState getSlabBlockStateFromResourceLocation(@Nullable ResourceLocation identifier, SlabType type) {
        var block = getSlabBlockFromResourceLocation(identifier);
        return block.defaultBlockState().setValue(SlabBlock.TYPE, type);
    }

    private @NonNull SlabBlock getSlabBlockFromResourceLocation(@Nullable ResourceLocation identifier) {
        if (identifier != null) {
            var block = Registry.BLOCK.get(identifier);
            if (block instanceof SlabBlock slab) {
                return slab;
            }
        }
        return DEFAULT_BLOCK;
    }

    public Pair<ResourceLocation, ResourceLocation> getSlabCombination(BlockPos pos) {
        return context.getOrDefault(pos, DEFAULT_LOCATION_PAIR);
    }

    static {
        KEY_TOP = "top";
        KEY_BOTTOM = "bottom";
        DEFAULT_BLOCK = (SlabBlock) Blocks.COBBLESTONE_SLAB;
        KEY_DOUBLE_SLAB_CONTEXT = "double_slab_queue";
        DEFAULT_LOCATION = new ResourceLocation("minecraft:cobblestone_slab");
        DEFAULT_LOCATION_PAIR = new ImmutablePair<>(DEFAULT_LOCATION, DEFAULT_LOCATION);
    }
}
