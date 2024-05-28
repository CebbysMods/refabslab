package lv.cebbys.mcmods.refabslab.api.bridge;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lv.cebbys.mcmods.refabslab.api.component.BlockApi;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class RefabslabMod {
    public static RefabslabMod INSTANCE;

    public static void initialize(RefabslabMod instance) {
        INSTANCE = instance;
    }

    public abstract <B> B createDoubleSlab(BlockApi block);
}
