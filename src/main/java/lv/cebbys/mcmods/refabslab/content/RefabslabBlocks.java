package lv.cebbys.mcmods.refabslab.content;

import com.google.common.collect.ImmutableList;
import lv.cebbys.mcmods.refabslab.content.blocks.DoubleSlabBlock;
import lv.cebbys.mcmods.refabslab.content.blocks.DoubleWallBlock;
import lv.cebbys.mcmods.refabslab.content.blocks.WallBlock;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static lv.cebbys.mcmods.refabslab.Refabslab.REGISTRY;

public class RefabslabBlocks {
    private static final Logger LOGGER = LoggerFactory.getLogger(RefabslabBlocks.class);

    public static final Block DOUBLE_WALL;
    public static final Block DOUBLE_SLAB;
    public static final MinecraftWalls MINECRAFT;

    @Nullable
    public static final BlockusWalls BLOCKUS;

    static {
        DOUBLE_WALL = REGISTRY.registerBlock("double_wall_block", new DoubleWallBlock());
        DOUBLE_SLAB = REGISTRY.registerBlock("double_slab_block", new DoubleSlabBlock(s -> s));

        MINECRAFT = new MinecraftWalls();
        BLOCKUS = isModLoaded("blockus") ? new BlockusWalls() : null;
    }

    private static boolean isModLoaded(String id) {
        return FabricLoader.getInstance().isModLoaded(id);
    }

    public static final class MinecraftWalls {
        public final ImmutableList<Block> WALL_SLABS;
        private final List<Block> TEMP_LIST = new ArrayList<>();
        // Stones
        public final Block STONE_WALL_SLAB = registerVanillaWall(Blocks.STONE_SLAB);
        public final Block ANDESITE_WALL_SLAB = registerVanillaWall(Blocks.ANDESITE_SLAB);
        public final Block BLACKSTONE_WALL_SLAB = registerVanillaWall(Blocks.BLACKSTONE_SLAB);
        // Woods
        public final Block OAK_WALL_SLAB = registerVanillaWall(Blocks.OAK_SLAB);
        public final Block BIRCH_WALL_SLAB = registerVanillaWall(Blocks.BIRCH_SLAB);
        public final Block JUNGLE_WALL_SLAB = registerVanillaWall(Blocks.JUNGLE_SLAB);
        public final Block SPRUCE_WALL_SLAB = registerVanillaWall(Blocks.SPRUCE_SLAB);
        public final Block ACACIA_WALL_SLAB = registerVanillaWall(Blocks.ACACIA_SLAB);
        public final Block DARK_OAK_WALL_SLAB = registerVanillaWall(Blocks.DARK_OAK_SLAB);
        public final Block CRIMSON_WALL_SLAB = registerVanillaWall(Blocks.CRIMSON_SLAB);
        public final Block WARPED_WALL_SLAB = registerVanillaWall(Blocks.WARPED_SLAB);

        private MinecraftWalls() {
            LOGGER.info("Registering Minecraft wall slab blocks");
            WALL_SLABS = ImmutableList.copyOf(this.TEMP_LIST);
        }

        private Block registerVanillaWall(Block block) {
            if (block instanceof SlabBlock slab) {
                Identifier slabIdentifier = Registry.BLOCK.getId(block);
                String wallName = slabIdentifier.getPath().replaceAll("slab", "wall_slab");
                Block wall = REGISTRY.registerBlock(wallName, new WallBlock(slab));
                REGISTRY.registerItem(wallName + "_item", new BlockItem(wall, new Item.Settings()
                        .group(ItemGroup.BUILDING_BLOCKS)
                        .maxCount(64)
                ));
                TEMP_LIST.add(wall);
                return wall;
            } else {
                throw new RuntimeException("Tried to register no slab block [" + block + "] as a wall slab");
            }
        }

        ;
    }

    public static final class BlockusWalls {

        private BlockusWalls() {
            LOGGER.info("Registering Blockus wall slab blocks");
        }
    }
}
