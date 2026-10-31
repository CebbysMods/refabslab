package lv.cebbys.mcmods.refabslab.compat;

import lv.cebbys.mcmods.modularity.api.Platform;
import lv.cebbys.mcmods.modularity.api.core.Loader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Double slabs are rendered through the Fabric Renderer API. Sodium implements it natively since
 * 0.6; older Sodium versions need Indium for that.
 */
public final class SodiumCompatibility {
    private static final Pattern VERSION = Pattern.compile("(\\d+)\\.(\\d+)");

    private SodiumCompatibility() {
    }

    public static void check(Platform platform) {
        if (platform.loader() != Loader.FABRIC && platform.loader() != Loader.QUILT && platform.loader() != Loader.TEST) {
            return;
        }
        if (!platform.isModLoaded("sodium") || platform.isModLoaded("indium")) {
            return;
        }
        String version = platform.modVersion("sodium").orElse("");
        if (needsIndium(version)) {
            throw new CompatibilityException("Refabslab requires Indium when Sodium " + version
                    + " is installed. Install Indium or update Sodium to 0.6 or newer.");
        }
    }

    static boolean needsIndium(String sodiumVersion) {
        Matcher matcher = VERSION.matcher(sodiumVersion);
        if (!matcher.find()) {
            return false;
        }
        int major = Integer.parseInt(matcher.group(1));
        int minor = Integer.parseInt(matcher.group(2));
        return major == 0 && minor < 6;
    }

    public static final class CompatibilityException extends RuntimeException {
        public CompatibilityException(String message) {
            super(message);
        }
    }
}
