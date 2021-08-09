package lv.cebbys.mcmods.refabslab.utilities;

import net.minecraft.util.Identifier;

// TODO Implement namespace cases
public class IdentifierUtils {

    private static final String SLAB = "slab";
    private static final String WALL = "wall_slab";

    public static String slabIdToWallName(ModNamespace namespace, Identifier slabId) {
        return slabId.getPath().replaceAll(SLAB, WALL);
    }

    public static String wallIdToSlabName(ModNamespace namespace, Identifier wallId) {
        return wallId.getPath().replaceAll(WALL, SLAB);
    }

    public static Identifier wallIdToSlabId(ModNamespace namespace, Identifier wallId) {
        return namespace.toIdentifier(wallId.getPath().replaceAll(WALL, SLAB));
    }

    public static Identifier wrap(String prefix, Identifier id, String suffix) {
        return new Identifier(id.getNamespace(), prefix + id.getPath() + suffix);
    }

    public static Identifier suffix(Identifier id, String suffix) {
        return new Identifier(id.getNamespace(), id.getPath() + suffix);
    }

    public static Identifier prefix(String prefix, Identifier id) {
        return new Identifier(id.getNamespace(), prefix + id.getPath());
    }
}
