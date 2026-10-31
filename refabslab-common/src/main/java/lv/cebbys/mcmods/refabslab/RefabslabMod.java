package lv.cebbys.mcmods.refabslab;

import lv.cebbys.mcmods.modularity.api.ModContext;
import lv.cebbys.mcmods.modularity.api.ModularMod;
import lv.cebbys.mcmods.modularity.api.block.BlockHandle;
import lv.cebbys.mcmods.modularity.api.block.BlockSelector;
import lv.cebbys.mcmods.modularity.api.block.BlockTag;
import lv.cebbys.mcmods.modularity.api.block.BlockType;
import lv.cebbys.mcmods.modularity.api.block.PushReaction;
import lv.cebbys.mcmods.modularity.api.block.property.IntegerProperty;
import lv.cebbys.mcmods.modularity.api.block.property.Property;
import lv.cebbys.mcmods.modularity.api.client.ClientContext;
import lv.cebbys.mcmods.modularity.api.core.Id;
import lv.cebbys.mcmods.modularity.api.data.BlockData;
import lv.cebbys.mcmods.refabslab.client.DoubleSlabModel;
import lv.cebbys.mcmods.refabslab.client.DoubleSlabParticles;
import lv.cebbys.mcmods.refabslab.compat.SodiumCompatibility;
import lv.cebbys.mcmods.refabslab.content.DoubleSlab;
import lv.cebbys.mcmods.refabslab.content.DoubleSlabBehaviour;
import lv.cebbys.mcmods.refabslab.content.SlabCombining;

/**
 * Refabricated Slabs: put any two different slabs into one block space.
 *
 * <p>This class is the whole mod setup. It only uses the Modularity API, so it runs on every
 * loader and Minecraft version Modularity supports.
 */
public final class RefabslabMod implements ModularMod {
    public static final String MOD_ID = "refabslab";

    /** Light level of a double slab: the brighter of both halves. */
    public static final IntegerProperty LIGHT_LEVEL = Property.integer("light_level", 0, 15);

    /** Slabs in this tag are never combined (data driven, other mods/packs can add to it). */
    public static final BlockTag NON_COMBINABLE = BlockTag.of(Id.of(MOD_ID, "non_combinable"));

    /** Which slabs a double slab block consists of. */
    public static final BlockData<DoubleSlab> DOUBLE_SLAB_DATA = BlockData.of(Id.of(MOD_ID, "double_slab"), DoubleSlab.CODEC);

    private static BlockHandle doubleSlab;

    public static BlockHandle doubleSlab() {
        if (doubleSlab == null) {
            throw new IllegalStateException("Refabslab is not initialized");
        }
        return doubleSlab;
    }

    @Override
    public void init(ModContext mod) {
        doubleSlab = mod.block("double_slab_block", block -> block
                .property(LIGHT_LEVEL, 0)
                .strength(2.0F, 6.0F)
                .lightLevel(state -> state.get(LIGHT_LEVEL))
                .pushReaction(PushReaction.BLOCK)
                .noLootTable()
                .data(DOUBLE_SLAB_DATA)
                .behaviour(new DoubleSlabBehaviour()));

        mod.intercept(BlockSelector.type(BlockType.SLAB), new SlabCombining());
    }

    @Override
    public void initClient(ClientContext client) {
        SodiumCompatibility.check(client.platform());
        client.blockModel(doubleSlab, new DoubleSlabModel());
        client.hitParticles(doubleSlab, new DoubleSlabParticles());
    }
}
