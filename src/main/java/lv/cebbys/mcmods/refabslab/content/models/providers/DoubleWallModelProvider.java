package lv.cebbys.mcmods.refabslab.content.models.providers;

import lv.cebbys.mcmods.refabslab.Refabslab;
import lv.cebbys.mcmods.refabslab.content.models.DoubleWallModel;
import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelResourceProvider;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

public class DoubleWallModelProvider implements ModelResourceProvider {

    @Override
    public @Nullable UnbakedModel loadModelResource(Identifier resourceId, ModelProviderContext context) {
        if (resourceId.equals(new Identifier(Refabslab.MODID, "block/double_wall_block_x"))) {
            return new DoubleWallModel(Direction.Axis.X);
        } else if (resourceId.equals(new Identifier(Refabslab.MODID, "block/double_wall_block_z"))) {
            return new DoubleWallModel(Direction.Axis.Z);
        }
        return null;
    }
}
