package lv.cebbys.mcmods.refabslab.api.bridge;

import lv.cebbys.mcmods.refabslab.api.component.BlockApi;
import lv.cebbys.mcmods.refabslab.api.component.identifier.ResourceIdentifier;

public interface BlockRegistry {
    void registerBlock(ResourceIdentifier identifier, BlockApi block);
}
