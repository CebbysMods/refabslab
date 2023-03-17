package lv.cebbys.mcmods.refabslab.utility;

import lv.cebbys.mcmods.refabslab.RefabslabCommon;
import lv.cebbys.mcmods.refabslab.content.model.multipart.RefabslabBakedModel;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.MultiPartBakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class TransformUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(RefabslabCommon.class);
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

    public static RefabslabBakedModel toRefabslabBakedModel(MultiPartBakedModel model) {
        try {
            var fields = Arrays.asList(MultiPartBakedModel.class.getDeclaredFields());
            var selectorFields = fields.stream()
                    .filter((var field) -> List.class.isAssignableFrom(field.getType()))
                    .toList();
            if (selectorFields.size() != 1) {
                throw new RuntimeException("Multiple fields matching selectors");
            }
            var selector = selectorFields.get(0);
            selector.setAccessible(true);
            var selectorValue = selector.get(model);
            return new RefabslabBakedModel((List<Pair<Predicate<BlockState>, BakedModel>>) selectorValue);
        } catch (Exception e) {
            throw new RuntimeException("Failed to transform model to refabslab model", e);
        }
    }
}
