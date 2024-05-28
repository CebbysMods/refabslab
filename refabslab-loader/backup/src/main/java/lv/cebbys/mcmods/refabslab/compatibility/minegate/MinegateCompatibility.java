package lv.cebbys.mcmods.refabslab.compatibility.minegate;

import lv.cebbys.mcmods.refabslab.RefabslabRegistry;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class MinegateCompatibility {
    private static final String MODID = "minegate";
    private static final List<String> BLACKLISTED_SLABS;

    public static void loadCommon() {
        registerBlacklistSlabs();
    }

    private static void registerBlacklistSlabs() {
        for (var slab : BLACKLISTED_SLABS) {
            registerMinegateSlabInBlacklist(slab);
        }
    }

    private static void registerMinegateSlabInBlacklist(String slabId) {
        RefabslabRegistry.registerSlabForBlacklist(new ResourceLocation(MODID, slabId));
    }

    static {
        BLACKLISTED_SLABS = List.of(
                "grass_block_slab"
        );
    }
}
