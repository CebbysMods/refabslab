package lv.cebbys.mcmods.refabslab.content;

import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentInitializer;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import lv.cebbys.mcmods.refabslab.content.component.DoubleSlabComponent;
import net.minecraft.util.Identifier;

import static lv.cebbys.mcmods.refabslab.Refabslab.MODID;

public class RefabslabComponents implements ChunkComponentInitializer {
    public static final ComponentKey<DoubleSlabComponent> DOUBLE_SLAB_QUEUE;

    static {
        DOUBLE_SLAB_QUEUE = ComponentRegistry.getOrCreate(
                new Identifier(MODID, "double_slabs"),
                DoubleSlabComponent.class
        );
    }

    @Override
    public void registerChunkComponentFactories(ChunkComponentFactoryRegistry registry) {
        registry.register(DOUBLE_SLAB_QUEUE, DoubleSlabComponent::new);
    }
}
