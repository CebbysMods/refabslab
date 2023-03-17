package lv.cebbys.mcmods.refabslab;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class RefabslabRegistry {
    private static final Logger LOGGER = LoggerFactory.getLogger(RefabslabRegistry.class);
    private static final Set<ResourceLocation> BLACKLISTED_SLABS;

    public static void registerSlabForBlacklist(ResourceLocation id) {
        LOGGER.info("Registering Slab with Id: '{}' in the blacklist", id);
        BLACKLISTED_SLABS.add(id);
    }

    public static boolean isSlabInBlacklist(ResourceLocation id) {
        return BLACKLISTED_SLABS.contains(id);
    }

    static {
        BLACKLISTED_SLABS = new HashSet<>();
    }
}
