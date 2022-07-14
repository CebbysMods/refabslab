package lv.cebbys.mcmods.refabslab.utility;

import lv.cebbys.mcmods.refabslab.Refabslab;
import lv.cebbys.mcmods.refabslab.compatibility.sodium.SodiumCompatibility;
import lv.cebbys.mcmods.refabslab.content.RefabslabComponents;
import lv.cebbys.mcmods.refabslab.content.component.DoubleSlabComponent;
import net.minecraft.client.render.chunk.ChunkRendererRegion;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Arrays;

public class TransformUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(Refabslab.class);
    private static final String NULL_STRING = "NULL";
    
    public static String toString(BlockPos pos) {
        if (pos == null) return NULL_STRING;
        return String.format("%d;%d;%d", pos.getX(), pos.getY(), pos.getZ());
    }

    @Nullable
    public static BlockPos toBlockPos(String str) {
        if (str == null || NULL_STRING.equals(str)) return null;
        String[] parts;
        if (!str.contains(";") || (parts = str.split(";")).length != 3) return null;
        return new BlockPos(
                Integer.parseInt(parts[0]),
                Integer.parseInt(parts[1]),
                Integer.parseInt(parts[2])
        );
    }

    @Nullable
    public static DoubleSlabComponent toDoubleSlabComponent(BlockView view, BlockPos pos) {
        if (view instanceof World world) {
            return toDoubleSlabComponent(world, pos);
        } else if (view instanceof Chunk chunk) {
            return toDoubleSlabComponent(chunk);
        } else if (view instanceof ChunkRendererRegion region) {
            return toDoubleSlabComponent(toWorld(region), pos);
        } else if (SodiumCompatibility.isWorldSlice(view)) {
            return toDoubleSlabComponent(SodiumCompatibility.toWorld(view), pos);
        } else {
            LOGGER.error(
                    "Failed to transform {} to DoubleSlabComponent. {}", view.getClass().getName(),
                    "If you see this exception, please notify the developer of Refabslab mod"
            );
            return null;
        }
    }

    @Nullable
    public static DoubleSlabComponent toDoubleSlabComponent(Chunk chunk) {
        return RefabslabComponents.DOUBLE_SLAB_QUEUE.maybeGet(chunk).orElse(null);
    }

    @Nullable
    public static DoubleSlabComponent toDoubleSlabComponent(World world, BlockPos pos) {
        if (world == null || pos == null) return null;
        return toDoubleSlabComponent(world.getChunk(pos));
    }

    @Nullable
    public static World toWorld(ChunkRendererRegion region) {
        try {
            Class<?> regionClass = region.getClass();
            Field worldField = Arrays.stream(regionClass.getDeclaredFields())
                    .filter(f -> f.getType().equals(World.class))
                    .findFirst().orElse(null);
            if (worldField == null) return null;
            worldField.setAccessible(true);
            return (World) worldField.get(region);
        } catch (Exception ignored) {
            return null;
        }
    }
}
