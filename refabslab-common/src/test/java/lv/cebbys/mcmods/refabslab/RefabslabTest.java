package lv.cebbys.mcmods.refabslab;

import lv.cebbys.mcmods.modularity.api.block.BlockStateRef;
import lv.cebbys.mcmods.modularity.api.block.BlockTag;
import lv.cebbys.mcmods.modularity.api.block.property.SlabType;
import lv.cebbys.mcmods.modularity.api.core.Id;
import lv.cebbys.mcmods.modularity.api.core.Side;
import lv.cebbys.mcmods.modularity.api.world.Direction;
import lv.cebbys.mcmods.modularity.api.world.Pos;
import lv.cebbys.mcmods.modularity.api.world.Vec3;
import lv.cebbys.mcmods.modularity.testkit.TestBlock;
import lv.cebbys.mcmods.modularity.testkit.TestGame;
import lv.cebbys.mcmods.modularity.testkit.TestItemStack;
import lv.cebbys.mcmods.modularity.testkit.TestLevel;
import lv.cebbys.mcmods.modularity.testkit.TestPlayer;
import lv.cebbys.mcmods.refabslab.compat.KleeSlabsCompat;
import lv.cebbys.mcmods.refabslab.compat.SodiumCompatibility;
import lv.cebbys.mcmods.refabslab.content.DoubleSlab;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static lv.cebbys.mcmods.modularity.api.block.property.VanillaProperties.SLAB_TYPE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Feature parity tests for every Refabslab feature, run on the Modularity test platform. */
class RefabslabTest {
    private static final Pos POS = new Pos(0, 64, 0);
    private static final Id DOUBLE = Id.of("refabslab", "double_slab_block");

    private TestGame game;
    private TestBlock oak;
    private TestBlock stone;
    private TestBlock cobblestone;
    private TestBlock glowing;
    private TestBlock grassSlab;
    private TestLevel level;

    @BeforeEach
    void setUp() {
        game = TestGame.create();
        oak = game.slab("minecraft:oak_slab").hardness(2.0F);
        stone = game.slab("minecraft:stone_slab").hardness(2.0F).tag(BlockTag.GUARDED_BY_PIGLINS);
        cobblestone = game.slab("minecraft:cobblestone_slab").hardness(2.0F);
        glowing = game.slab("example:glowing_slab").lightEmission(12);
        grassSlab = game.slab("minegate:grass_block_slab").tag(RefabslabMod.NON_COMBINABLE);
        game.load(RefabslabMod.MOD_ID, new RefabslabMod());
        level = game.level();
    }

    private BlockStateRef slab(TestBlock block, SlabType type) {
        return block.defaultState().with(SLAB_TYPE, type);
    }

    /** Places {@code held} by clicking the top face of a bottom slab at {@link #POS}. */
    private TestGame.PlaceOutcome placeOnBottomSlab(TestBlock existing, TestBlock held) {
        level.set(POS, slab(existing, SlabType.BOTTOM));
        return game.use(level, POS, Direction.UP, new Vec3(0.5, 64.5, 0.5), TestItemStack.of(held, 1), new TestPlayer());
    }

    private DoubleSlab dataAt(Pos pos) {
        return level.data(pos, RefabslabMod.DOUBLE_SLAB_DATA).orElseThrow();
    }

    @Nested
    class Placement {
        @Test
        void differentSlabOnBottomSlabCreatesDoubleSlab() {
            var outcome = placeOnBottomSlab(oak, stone);

            assertTrue(outcome.placed());
            assertEquals(POS, outcome.pos());
            assertEquals(DOUBLE, level.blockState(POS).block().id());
            assertEquals(new DoubleSlab(stone.id(), oak.id()), dataAt(POS));
        }

        @Test
        void differentSlabUnderTopSlabCreatesDoubleSlab() {
            level.set(POS, slab(oak, SlabType.TOP));
            var outcome = game.use(level, POS, Direction.DOWN, new Vec3(0.5, 64.5, 0.5), TestItemStack.of(stone, 1), new TestPlayer());

            assertTrue(outcome.placed());
            assertEquals(new DoubleSlab(oak.id(), stone.id()), dataAt(POS));
        }

        @Test
        void sameSlabKeepsVanillaDoubleSlab() {
            var outcome = placeOnBottomSlab(oak, oak);

            assertTrue(outcome.placed());
            assertEquals(oak.id(), level.blockState(POS).block().id());
            assertEquals(SlabType.DOUBLE, level.blockState(POS).get(SLAB_TYPE));
        }

        @Test
        void nonCombinableSlabsAreNotCombined() {
            var outcome = placeOnBottomSlab(grassSlab, oak);

            assertEquals(grassSlab.id(), level.blockState(POS).block().id());
            assertEquals(POS.offset(Direction.UP), outcome.pos());
        }

        @Test
        void obstructedSpaceIsNotCombined() {
            level.obstruct(POS);
            placeOnBottomSlab(oak, stone);

            assertEquals(oak.id(), level.blockState(POS).block().id());
        }

        @Test
        void nonSlabItemsDoNotReplaceSlabs() {
            level.set(POS, slab(oak, SlabType.BOTTOM));
            var andesite = game.block("minecraft:andesite");
            var outcome = game.use(level, POS, Direction.UP, new Vec3(0.5, 64.5, 0.5), TestItemStack.of(andesite, 1), new TestPlayer());

            assertEquals(POS.offset(Direction.UP), outcome.pos());
            assertEquals(oak.id(), level.blockState(POS).block().id());
        }

        @Test
        void fallingBlocksDoNotReplaceLowerSlabs() {
            // Regression: game crashed when sand landed on a lower slab (empty stack, no player).
            level.set(POS, slab(oak, SlabType.BOTTOM));
            var ctx = game.placementContext(level, POS, Direction.UP, new Vec3(0.5, 64.5, 0.5), TestItemStack.EMPTY, null);

            assertFalse(game.canBeReplaced(level.blockState(POS), ctx));
        }

        @Test
        void lightLevelIsBrightestHalf() {
            placeOnBottomSlab(oak, glowing);

            assertEquals(12, level.blockState(POS).get(RefabslabMod.LIGHT_LEVEL));
            assertEquals(12, level.blockState(POS).lightEmission());
        }
    }

    @Nested
    class Mining {
        @Test
        void destroyProgressUsesHarderSlabAndBetterTool() {
            placeOnBottomSlab(oak, stone);
            var player = new TestPlayer().speed(stone.id(), 8.0F).correctToolFor(stone.id());

            // hardness 2, best speed 8, harvest penalty 100 - 35 = 65
            assertEquals(8.0F / 2.0F / 65.0F, game.destroyProgress(level, POS, player), 1.0E-6F);
        }

        @Test
        void breakingDropsBothSlabsAndPlaysBothEffects() {
            placeOnBottomSlab(oak, stone);
            var player = new TestPlayer().correctToolFor(oak.id());

            game.breakBlock(level, POS, player);

            assertTrue(level.blockState(POS).isAir());
            assertEquals(List.of(stone.id(), oak.id()), level.drops().stream().map(d -> d.stack().itemId()).toList());
            assertEquals(List.of(slab(stone, SlabType.TOP), slab(oak, SlabType.BOTTOM)), level.breakEffects());
        }

        @Test
        void breakingWithoutSuitableToolDropsNothing() {
            placeOnBottomSlab(oak, stone);

            game.breakBlock(level, POS, new TestPlayer());

            assertTrue(level.drops().isEmpty());
        }

        @Test
        void creativeBreakingDropsNothing() {
            placeOnBottomSlab(oak, stone);

            game.breakBlock(level, POS, new TestPlayer().creative(true).correctToolFor(oak.id(), stone.id()));

            assertTrue(level.drops().isEmpty());
        }

        @Test
        void explosionsDropBothSlabs() {
            placeOnBottomSlab(oak, stone);

            game.explode(level, POS);

            assertEquals(2, level.drops().size());
        }

        @Test
        void missingDataFallsBackToCobblestone() {
            level.set(POS, game.block(DOUBLE).orElseThrow().defaultState());
            game.breakBlock(level, POS, new TestPlayer().correctToolFor(cobblestone.id()));

            assertEquals(List.of(cobblestone.id(), cobblestone.id()), level.drops().stream().map(d -> d.stack().itemId()).toList());
        }
    }

    @Nested
    class Client {
        @Test
        void modelRendersBottomAndTopSlab() {
            placeOnBottomSlab(oak, stone);

            assertEquals(List.of(slab(oak, SlabType.BOTTOM), slab(stone, SlabType.TOP)), game.render(level, POS));
            assertEquals(Id.minecraft("block/cobblestone"), game.model(DOUBLE).orElseThrow().particleTexture());
        }

        @Test
        void hitParticlesUseTheHalfBeingHit() {
            placeOnBottomSlab(oak, stone);

            assertEquals(slab(stone, SlabType.TOP), game.hitParticleState(level, POS, Direction.NORTH, new Vec3(0.5, 64.9, -0.1)));
            assertEquals(slab(oak, SlabType.BOTTOM), game.hitParticleState(level, POS, Direction.NORTH, new Vec3(0.5, 64.2, -0.1)));
        }

        @Test
        void dedicatedServerSkipsClientInit() {
            TestGame server = TestGame.create();
            server.testPlatform().side(Side.SERVER);
            server.slab("minecraft:cobblestone_slab");
            server.load(RefabslabMod.MOD_ID, new RefabslabMod());

            assertTrue(server.model(DOUBLE).isEmpty());
        }
    }

    @Nested
    class Compatibility {
        @Test
        void kleeSlabsSplitsIntoTheStoredHalves() {
            placeOnBottomSlab(oak, stone);
            BlockStateRef state = level.blockState(POS);

            assertTrue(KleeSlabsCompat.isDoubleSlab(state));
            assertFalse(KleeSlabsCompat.isDoubleSlab(slab(oak, SlabType.BOTTOM)));
            assertEquals(slab(stone, SlabType.TOP), KleeSlabsCompat.singleSlab(level, POS, SlabType.TOP));
            assertEquals(slab(oak, SlabType.BOTTOM), KleeSlabsCompat.singleSlab(level, POS, SlabType.BOTTOM));
        }

        @Test
        void kleeSlabsWithoutDataFallsBackToCobblestone() {
            level.set(POS, game.block(DOUBLE).orElseThrow().defaultState());

            assertEquals(slab(cobblestone, SlabType.TOP), KleeSlabsCompat.singleSlab(level, POS, SlabType.TOP));
        }

        @Test
        void oldSodiumWithoutIndiumIsRejected() {
            TestGame other = TestGame.create();
            other.testPlatform().withMod("sodium", "0.5.11+mc1.21.1");
            other.slab("minecraft:cobblestone_slab");

            assertThrows(SodiumCompatibility.CompatibilityException.class,
                    () -> other.load(RefabslabMod.MOD_ID, new RefabslabMod()));
        }

        @Test
        void oldSodiumWithIndiumOrNewSodiumIsAccepted() {
            TestGame withIndium = TestGame.create();
            withIndium.testPlatform().withMod("sodium", "0.5.11").withMod("indium", "1.0.35");
            withIndium.slab("minecraft:cobblestone_slab");
            withIndium.load(RefabslabMod.MOD_ID, new RefabslabMod());

            TestGame newSodium = TestGame.create();
            newSodium.testPlatform().withMod("sodium", "0.6.0+mc1.21.1");
            newSodium.slab("minecraft:cobblestone_slab");
            newSodium.load(RefabslabMod.MOD_ID, new RefabslabMod());
        }
    }
}
