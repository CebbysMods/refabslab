package lv.cebbys.mcmods.refabslab.utility;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.SharedConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MinecraftVersion {
    private static final List<Supplier<String>> VERSION_PROVIDERS;
    private static final Pattern MINECRAFT_VERSION_PATTERN;
    private static final ClassLoader CLASS_LOADER;
    private static final Logger LOGGER;
    private static final String MIXIN_BASE_PATH;

    private final int release;
    private final int major;
    private final int minor;
    private String mixinBasePath;

    public static MinecraftVersion get() {
        try {
            var versions = VERSION_PROVIDERS.stream()
                    .map(Supplier::get)
                    .filter(Objects::nonNull)
                    .filter(Predicate.not(String::isEmpty))
                    .collect(Collectors.toCollection(HashSet::new));
            if (versions.size() == 0) {
                throw new RuntimeException("version providers did not locate any version");
            } else if (versions.size() > 1) {
                throw new RuntimeException("version providers located multiple versions - " + versions);
            }
            var list = new ArrayList<>(versions);
            return toMinecraftVersion(list.get(0));
        } catch (Exception e) {
            throw new RuntimeException("Failed to identify minecraft version - " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        if (minor == 0) {
            return String.format("%d.%d", release, major);
        }
        return String.format("%d.%d.%d", release, major, minor);
    }

    public String getMixinPrefix() {
        if (mixinBasePath == null) {
            if (minor == 0) {
                mixinBasePath = String.format("v%d.v%d", release, major);
            } else {
                mixinBasePath = String.format("v%d.v%d.v%d", release, major, minor);
            }
        }
        return mixinBasePath;
    }

    static {
        LOGGER = LoggerFactory.getLogger(MinecraftVersion.class);
        MINECRAFT_VERSION_PATTERN = Pattern.compile("1.[0-9]+(.[0-9]+|)");
        CLASS_LOADER = MinecraftVersion.class.getClassLoader();
        MIXIN_BASE_PATH = "lv.cebbys.mcmods.refabslab.bridge.mixin";
        VERSION_PROVIDERS = List.of(
                MinecraftVersion::post_minecraft_1_19_provider,
                MinecraftVersion::pre_minecraft_1_19_provider
        );
    }

    private static String post_minecraft_1_19_provider() {
        try {
            var sharedConstants = CLASS_LOADER.loadClass("net.minecraft.SharedConstants");
            var detectVersion = sharedConstants.getDeclaredMethod("tryDetectVersion");
            detectVersion.invoke(null);
            var getVersion = sharedConstants.getDeclaredMethod("getCurrentVersion");
            var versionObject = getVersion.invoke(null);
            var worldVersion = versionObject.getClass();
            var getName = worldVersion.getDeclaredMethod("getName");
            return (String) getName.invoke(versionObject);
        } catch (Exception e) {
            LOGGER.debug("Failed to load minecraft version from 'Post 1.19 version provider'", e);
            return null;
        }
    }

    private static String pre_minecraft_1_19_provider() {
        try {
            var sharedConstants = CLASS_LOADER.loadClass("net.minecraft.SharedConstants");
            var detectVersion = sharedConstants.getDeclaredMethod("tryDetectVersion");
            detectVersion.invoke(null);
            var getVersion = sharedConstants.getDeclaredMethod("getCurrentVersion");
            var versionObject = getVersion.invoke(null);
            var worldVersion = versionObject.getClass();
            var getName = worldVersion.getDeclaredMethod("getReleaseTarget");
            return (String) getName.invoke(versionObject);
        } catch (Exception e) {
            LOGGER.debug("Failed to load minecraft version from 'Pre 1.19 version provider'", e);
            return null;
        }
    }


    private static MinecraftVersion toMinecraftVersion(String string) {
        try {
            if (string == null || "".equals(string)) {
                throw new RuntimeException("string is empty or null");
            }
            if (!MINECRAFT_VERSION_PATTERN.matcher(string).matches()) {
                throw new RuntimeException("string does not match regex " + MINECRAFT_VERSION_PATTERN);
            }
            var parts = string.split("\\.");
            var release = Integer.parseInt(parts[0]);
            var major = Integer.parseInt(parts[1]);
            var minor = parts.length == 3 ? Integer.parseInt(parts[2]) : 0;
            return new MinecraftVersion(release, major, minor);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse '" + string + "' to minecraft version - " + e.getMessage(), e);
        }
    }
}
