package lv.cebbys.mcmods.refabslab.utilities;

import net.minecraft.util.Identifier;

public enum ModNamespace {
    MINECRAFT("minecraft"),
    BLOCKUS("blockus");

    private final String namespace;

    ModNamespace(String name) {
        this.namespace = name;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public Identifier toIdentifier(String path) {
        return new Identifier(this.getNamespace(), path);
    }
}
