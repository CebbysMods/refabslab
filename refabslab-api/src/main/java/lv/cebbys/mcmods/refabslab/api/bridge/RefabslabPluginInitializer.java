package lv.cebbys.mcmods.refabslab.api.bridge;

import lv.cebbys.mcmods.refabslab.api.bridge.plugin.Plugin;

import java.util.List;

public interface RefabslabPluginInitializer {
    List<Plugin> onPluginLoad();
}
