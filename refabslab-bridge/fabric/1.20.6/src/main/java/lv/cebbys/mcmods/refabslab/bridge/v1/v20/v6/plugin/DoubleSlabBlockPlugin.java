package lv.cebbys.mcmods.refabslab.bridge.v1.v20.v6.plugin;

import lv.cebbys.mcmods.refabslab.api.bridge.plugin.AbstractDoubleSlabBlockPlugin;
import lv.cebbys.mcmods.refabslab.api.component.BlockApi;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

public class DoubleSlabBlockPlugin extends AbstractDoubleSlabBlockPlugin<Block> {
    @Override
    public Block createDoubleSlabBlock(BlockApi block) {
        return new Block(BlockBehaviour.Properties.of()) {
            public static final IntegerProperty LIGHT_LEVEL = IntegerProperty.create("light_level", 0, 15);

            {
                registerDefaultState(getStateDefinition().any().setValue(LIGHT_LEVEL, 0));
            }

            @Override
            protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
                builder.add(LIGHT_LEVEL);
            }
        };
    }
}
