package lv.cebbys.mcmods.refabslab.utilities;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SlabUtilities {

    public static class Blocks {

        private static final ImmutableSet<Identifier> EXCLUDES;

        static {
            EXCLUDES = ImmutableSet.of(
                    new Identifier("atbyw", "grass_block_slab"), new Identifier("atbyw", "mycelium_slab"), new Identifier("atbyw", "podzol_slab"),
                    new Identifier("atbyw", "grass_path_slab"), new Identifier("atbyw", "crimson_nylium_slab"), new Identifier("atbyw", "warped_nylium_slab")
            );
        }

        public static boolean contains(Identifier id) {
            return !isExclueded(id) && Registry.BLOCK.get(id) instanceof SlabBlock;
        }

        public static boolean contains(Block block) {
            return block instanceof SlabBlock && !isExclueded(Registry.BLOCK.getId(block));
        }

        public static boolean contains(Block... blocks) {
            for (Block block : blocks) {
                if (!contains(block)) {
                    return false;
                }
            }
            return true;
        }

        private static boolean isExclueded(Identifier id) {
            return EXCLUDES.contains(id);
        }
    }
}
