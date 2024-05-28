package lv.cebbys.mcmods.refabslab;

import lv.cebbys.mcmods.refabslab.compatibility.sodium.SodiumCompatibility;
import lv.cebbys.mcmods.refabslab.locator.ChunkLocator;
import lv.cebbys.mcmods.refabslab.content.model.provider.DoubleSlabModelProvider;
import lv.cebbys.mcmods.refabslab.locator.RenderChunkRegionChunkLocator;
import lv.cebbys.mcmods.refabslab.resource.ResourceEntrypoint;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.minecraft.client.renderer.chunk.RenderChunkRegion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RefabslabClient implements ClientModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RefabslabClient.class);

    static {
        ChunkLocator.register(RenderChunkRegion.class, new RenderChunkRegionChunkLocator());
    }

    @Override
    public void onInitializeClient() {
        LOGGER.info("Initializing Refabslab client side");
        SodiumCompatibility.load();
        ResourceEntrypoint.registerAssets();
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(rm -> new DoubleSlabModelProvider());
    }
}
