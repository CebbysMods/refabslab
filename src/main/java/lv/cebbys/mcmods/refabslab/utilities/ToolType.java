package lv.cebbys.mcmods.refabslab.utilities;

import net.minecraft.util.StringIdentifiable;

public enum ToolType implements StringIdentifiable {
    AXE("axe"),
    HAND("hand"),
    PICKAXE("pickaxe"),
    SHOVEL("shovel"),
    SWORD("sword");

    private final String name;

    ToolType(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return this.name;
    }
}
