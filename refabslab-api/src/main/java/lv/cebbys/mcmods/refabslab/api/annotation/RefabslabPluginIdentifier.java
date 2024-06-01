package lv.cebbys.mcmods.refabslab.api.annotation;

import lv.cebbys.mcmods.refabslab.api.bridge.plugin.Plugin;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Utility annotation for plugin autoloader to identify refabslab plugins
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface RefabslabPluginIdentifier {
    /**
     * @return Plugin type implemented by class annotated with this method
     */
    Class<? extends Plugin> type();

    /**
     * @return Minecraft version major number. Version <b>1.20.6</b> would be <b>1</b>
     */
    int major();

    /**
     * @return Minecraft version minor number. Version <b>1.20.6</b> would be <b>20</b>
     */
    int minor();

    /**
     * This value is optional. As Minecraft version can be <b>1.20</b>
     *
     * @return Minecraft version patch number. Version <b>1.20.6</b> would be <b>6</b>
     */
    int patch() default -1;
}
