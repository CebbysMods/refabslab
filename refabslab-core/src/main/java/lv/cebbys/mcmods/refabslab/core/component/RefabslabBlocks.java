package lv.cebbys.mcmods.refabslab.core.component;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lv.cebbys.mcmods.refabslab.api.component.BlockApi;
import lv.cebbys.mcmods.refabslab.api.component.identifier.ResourceIdentifier;
import lv.cebbys.mcmods.refabslab.api.registry.RefabslabRegistry;
import lv.cebbys.mcmods.refabslab.core.component.block.DoubleSlabBlock;

import static lv.cebbys.mcmods.refabslab.api.constant.RefabslabConstants.MODID;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class RefabslabBlocks {
    public static final BlockApi DOUBLE_SLAB_BLOCK;

    public static void initialize() {
    }

    static {
        DOUBLE_SLAB_BLOCK = RefabslabRegistry.register(
                RefabslabRegistry.BLOCK,
                new ResourceIdentifier(MODID, "double_slab_block"),
                new DoubleSlabBlock()
        );
    }
}
