package lv.cebbys.mcmods.refabslab;

import lv.cebbys.mcmods.refabslab.utility.MinecraftVersion;
import org.objectweb.asm.tree.ClassNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RefabslabMixinPlugin implements IMixinConfigPlugin {
    private static final Logger LOGGER = LoggerFactory.getLogger(RefabslabMixinPlugin.class);
    private static Exception INIT_FAILURE = null;

    public static boolean hasInitializationFailure() {
        return INIT_FAILURE != null;
    }

    public static Exception getInitializationFailure() {
        return INIT_FAILURE;
    }

    private final List<String> mixinNames = new ArrayList<>();

    @Override
    public void onLoad(String mixinPackage) {
        try {
            var version = MinecraftVersion.get();
            var mixinPrefix = version.getMixinPrefix();
            LOGGER.info("Loading mixins from '{}.{}' for release '{}'", mixinPackage, mixinPrefix, version);
            var mixinNames = new String[]{
                    "SlabBlockMixin", "SlabBlockParticleMixin"
            };
            for (var name : mixinNames) {
                this.mixinNames.add(String.format("%s.%s", mixinPrefix, name));
            }
        } catch (Exception e) {
            INIT_FAILURE = e;
        }
    }

    /**
     * Called during mixin intialisation, allows this plugin to control whether
     * a specific will be applied to the specified target. Returning false will
     * remove the target from the mixin's target set, and if all targets are
     * removed then the mixin will not be applied at all.
     *
     * @param targetClassName Fully qualified class name of the target class
     * @param mixinClassName  Fully qualified class name of the mixin
     * @return True to allow the mixin to be applied, or false to remove it from
     * target's mixin set
     */
    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        LOGGER.info("Should apply '{}' to '{}'", mixinClassName, targetClassName);
        return true;
    }


    /**
     * Called after all configurations are initialised, this allows this plugin
     * to observe classes targetted by other mixin configs and optionally remove
     * targets from its own set. The set myTargets is a direct view of the
     * targets collection in this companion config and keys may be removed from
     * this set to suppress mixins in this config which target the specified
     * class. Adding keys to the set will have no effect.
     *
     * @param myTargets    Target class set from the companion config
     * @param otherTargets Target class set incorporating targets from all other
     */
    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    /**
     * After mixins specified in the configuration have been processed, this
     * method is called to allow the plugin to add any additional mixins to
     * load. It should return a list of mixin class names or return null if the
     * plugin does not wish to append any mixins of its own.
     *
     * @return additional mixins to apply
     */
    @Override
    public List<String> getMixins() {
        return mixinNames;
    }

    /**
     * Called only if the "referenceMap" key in the config is <b>not</b> set.
     * This allows the refmap file name to be supplied by the plugin
     * programatically if desired. Returning <code>null</code> will revert to
     * the default behaviour of using the default refmap json file.
     *
     * @return Path to the refmap resource or null to revert to the default
     */
    @Override
    public String getRefMapperConfig() {
        return null;
    }

    /**
     * Called immediately <b>before</b> a mixin is applied to a target class,
     * allows any pre-application transformations to be applied.
     *
     * @param targetClassName Transformed name of the target class
     * @param targetClass     Target class tree
     * @param mixinClassName  Name of the mixin class
     * @param mixinInfo       Information about this mixin
     */
    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    /**
     * Called immediately <b>after</b> a mixin is applied to a target class,
     * allows any post-application transformations to be applied.
     *
     * @param targetClassName Transformed name of the target class
     * @param targetClass     Target class tree
     * @param mixinClassName  Name of the mixin class
     * @param mixinInfo       Information about this mixin
     */
    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }
}
