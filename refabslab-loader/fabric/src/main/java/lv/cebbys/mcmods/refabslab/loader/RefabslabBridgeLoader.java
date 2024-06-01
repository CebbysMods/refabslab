package lv.cebbys.mcmods.refabslab.loader;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import lv.cebbys.mcmods.mvl.api.model.MinecraftVersion;
import lv.cebbys.mcmods.refabslab.api.annotation.RefabslabPluginIdentifier;
import lv.cebbys.mcmods.refabslab.api.bridge.plugin.AbstractDoubleSlabBlockPlugin;
import lv.cebbys.mcmods.refabslab.api.bridge.plugin.Plugin;
import org.reflections.Reflections;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static lv.cebbys.mcmods.refabslab.api.constant.RefabslabConstants.MODID;

@Slf4j
public class RefabslabBridgeLoader {
    private static final int MIN_MAJOR_VERSION = 1;
    private static final int MIN_MINOR_VERSION = 0;

    public static final Reflections REFLECTIONS = new Reflections();

    @SuppressWarnings("unchecked")
    public static void initializeContext(MinecraftVersion minecraftVersion) {
        log.info("Loading '{}' mod plugins and context", MODID);
        // Load all plugins by plugin annotation
        // Sort plugins by type
        // For each sorted collection
        //      Create plugin stack
        //      Select latest matching plugin based on minecraft version
        //      Initialize context using plugin

        var doubleSlabBlockPlugins = new ArrayList<PluginContainer<AbstractDoubleSlabBlockPlugin<?>>>();

        var types = REFLECTIONS.getTypesAnnotatedWith(RefabslabPluginIdentifier.class);
        for (var type : types) {
            try {
                var plugin = parsePlugin(type);
                if (plugin.getPluginInstance() instanceof AbstractDoubleSlabBlockPlugin<?>) {
                    doubleSlabBlockPlugins.add((PluginContainer<AbstractDoubleSlabBlockPlugin<?>>) plugin);
                }
            } catch (RuntimeException e) {
                log.error("Failure processing plugin class '{}'", type, e);
            }
        }

        var sortedDoubleSlabBlockPlugins = sort(doubleSlabBlockPlugins);
        var plugin = get(minecraftVersion, sortedDoubleSlabBlockPlugins);

        log.info("sorted");
    }

    private static <P extends Plugin> List<PluginContainer<P>> sort(List<PluginContainer<P>> plugins) {
        return plugins.stream()
                .sorted(Comparator.comparing(PluginContainer::getVersion))
                .toList()
                .reversed();
    }

    private static <P extends Plugin> PluginContainer<P> get(MinecraftVersion version, List<PluginContainer<P>> plugins) {
        var optional = plugins.stream().filter((var plugin) -> plugin.getVersion().compareTo(version) <= 0).findFirst();
        return optional.orElseThrow(() -> new IllegalStateException(String.format(
                "Plugin for version '%s' not found", version
        )));
    }

    private static PluginContainer<?> parsePlugin(Class<?> type) {
        try {
            var annotation = type.getAnnotation(RefabslabPluginIdentifier.class);

            var version = parsePluginMinecraftVersion(annotation);

            var pluginType = annotation.type();
            if (!pluginType.isAssignableFrom(type)) {
                throw new IllegalStateException(String.format(
                        "Plugin class '%s' is not instance of plugin type '%s'", type, pluginType
                ));
            }
            Plugin instance;
            try {
                instance = (Plugin) type.getDeclaredConstructor().newInstance();
            } catch (Throwable t) {
                throw new IllegalStateException(String.format("Failure constructing type '%s'", type));
            }

            return PluginContainer.builder()
                    .pluginType(pluginType)
                    .pluginInstance(instance)
                    .version(version)
                    .build();
        } catch (Throwable t) {
            throw new IllegalStateException(String.format("Failed to parse plugin '%s'", type), t);
        }
    }

    private static MinecraftVersion parsePluginMinecraftVersion(RefabslabPluginIdentifier annotation) {
        var major = annotation.major();
        var minor = annotation.minor();
        var patch = annotation.patch();
        if (major < MIN_MAJOR_VERSION) {
            throw new IllegalStateException(String.format(
                    "Invalid Minecraft major version '%d'", major
            ));
        }
        if (minor < MIN_MINOR_VERSION) {
            throw new IllegalStateException(String.format(
                    "Invalid Minecraft minor version '%d'", minor
            ));
        }
        var builder = MinecraftVersion.builder()
                .major(major)
                .minor(minor);
        if (patch > 0) {
            builder.patch(patch);
        }
        return builder.build();
    }

    @Data
    @Builder
    private static class PluginContainer<P extends Plugin> {
        private final Class<? extends P> pluginType;
        private final MinecraftVersion version;
        private final P pluginInstance;
    }

    @Builder
    private static class RefabslabPlugins {
        private final List<PluginContainer<AbstractDoubleSlabBlockPlugin<?>>> doubleSlabBlockPlugins;
    }
}
