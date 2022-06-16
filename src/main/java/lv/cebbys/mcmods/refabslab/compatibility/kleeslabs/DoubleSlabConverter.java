package lv.cebbys.mcmods.refabslab.compatibility.kleeslabs;

import net.blay09.mods.kleeslabs.converter.SlabConverter;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.enums.SlabType;

import java.lang.StackWalker.StackFrame;
import java.util.List;
import java.util.stream.Stream;

public class DoubleSlabConverter implements SlabConverter {
    public DoubleSlabConverter(Block ignored) {

    }

    @Override
    public BlockState getSingleSlab(BlockState blockState, SlabType slabType) {
        StackWalker walker = StackWalker.getInstance();
        List<StackFrame> frames = walker.walk((Stream<StackFrame> stack) -> {
            return stack.filter(f -> f.getClassName().startsWith("net.blay09.mods")).toList();
        });

        StackFrame onBreakBlock = null;
        for(StackFrame frame : frames) {
            if(frame.getClassName().contains("onBreakBlock")) {
                onBreakBlock = frame;
                break;
            }
        }

        if(onBreakBlock != null) {

        }

        return Blocks.COBBLESTONE_SLAB.getDefaultState().with(SlabBlock.TYPE, slabType);
    }
}
