package lv.cebbys.mcmods.refabslab.bridge.api;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RefabslabRegistry {
    private static final Logger LOGGER = LoggerFactory.getLogger(RefabslabRegistry.class);
    private static final String MESSAGE_FAILED_TO_REGISTER;
    private static final String MESSAGE_ALREADY_REGISTERED;

    private static final Set<ResourceLocation> BLACKLISTED_SLABS;
    private static DoubleSlabComponentLocator DOUBLE_SLAB_COMPONENT;

    public static void registerBlacklistedSlab(ResourceLocation block) {
        if (block == null) {
            LOGGER.error(MESSAGE_FAILED_TO_REGISTER, "blacklisted slab", null, "it being 'null'");
        } else if (BLACKLISTED_SLABS.contains(block)) {
            LOGGER.error(MESSAGE_FAILED_TO_REGISTER, "blacklisted slab", block, MESSAGE_ALREADY_REGISTERED);
        } else {
            BLACKLISTED_SLABS.add(block);
        }
    }

    public static boolean isAllowedSlab(ResourceLocation block) {
        return !BLACKLISTED_SLABS.contains(block);
    }

    public static void registerDoubleSlabComponentLocator(DoubleSlabComponentLocator component) {
        if (DOUBLE_SLAB_COMPONENT != null) {
            LOGGER.error(MESSAGE_FAILED_TO_REGISTER, "double slab component", component, MESSAGE_ALREADY_REGISTERED);
        } else {
            DOUBLE_SLAB_COMPONENT = component;
        }
    }

    public static DoubleSlabComponentLocator getDoubleSlabComponentLocator() {
        return DOUBLE_SLAB_COMPONENT;
    }

    static {
        MESSAGE_FAILED_TO_REGISTER = "Failed to register {} '{}' due to {}";
        MESSAGE_ALREADY_REGISTERED = "already being registered";
        BLACKLISTED_SLABS = new HashSet<>();
    }
}
