package lv.cebbys.mcmods.refabslab.api.registry;

import lv.cebbys.mcmods.refabslab.api.component.BlockApi;
import lv.cebbys.mcmods.refabslab.api.component.identifier.ResourceIdentifier;

public abstract class RefabslabRegistry<T> {
    public static RefabslabRegistry<BlockApi> BLOCK;


    public static <T1> T1 register(RefabslabRegistry<T1> registry, ResourceIdentifier id, T1 resource) {
        return registry.register(id, resource);
    }

    protected abstract T register(ResourceIdentifier id, T resource);
}
