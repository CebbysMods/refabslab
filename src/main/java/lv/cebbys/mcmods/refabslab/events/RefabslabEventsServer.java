package lv.cebbys.mcmods.refabslab.events;

import lv.cebbys.mcmods.refabslab.content.RefabslabBlocks;
import lv.cebbys.mcmods.refabslab.content.blocks.DoubleWallBlock;
import lv.cebbys.mcmods.refabslab.content.blocks.WallBlock;
import lv.cebbys.mcmods.refabslab.content.entities.DoubleSlabEntity;
import lv.cebbys.mcmods.refabslab.content.entities.DoubleWallEntity;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.block.SlabBlock;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public final class RefabslabEventsServer extends RefabslabEvents {

    public static void registerEventReceivers() {

        ServerPlayNetworking.registerGlobalReceiver(DOUBLE_SLAB_PLACED_EVENT, (server, player, handler, data, responseSender) -> {
            BlockPos pos = data.readBlockPos();
            SlabBlock bottom = (SlabBlock) Registry.BLOCK.get(data.readIdentifier());
            SlabBlock top = (SlabBlock) Registry.BLOCK.get(data.readIdentifier());
            World world = player.getWorld();

            server.execute(() -> {
                if (!player.isCreative() && !player.isSpectator()) {
                    player.getMainHandStack().decrement(1);
                }

                BlockState state = RefabslabBlocks.DOUBLE_SLAB.getDefaultState();
                world.setBlockState(pos, state, 3);

                DoubleSlabEntity entity = new DoubleSlabEntity(bottom, top, pos, state);
                world.addBlockEntity(entity);
                entity.setWorld(world);
                entity.markDirty();

                BlockSoundGroup group = top.getDefaultState().getSoundGroup();
                world.playSound(null, pos, group.getPlaceSound(), SoundCategory.BLOCKS,
                        (group.getVolume() + 1.0F) / 2.0F, group.getPitch() * 0.8F);
            });
        });

        ServerPlayNetworking.registerGlobalReceiver(DOUBLE_WALL_PLACED_EVENT, ((server, player, handler, buf, responseSender) -> {
            BlockPos pos = buf.readBlockPos();
            WallBlock placed = (WallBlock) Registry.BLOCK.get(buf.readIdentifier());
            WallBlock inventory = (WallBlock) Registry.BLOCK.get(buf.readIdentifier());
            boolean isXAxis = buf.readBoolean();
            boolean isNegative = buf.readBoolean();
            World world = player.getWorld();

            server.execute(() -> {
                if (!player.isCreative() && !player.isSpectator()) {
                    player.getMainHandStack().decrement(1);
                }

                BlockState doubleWallState = RefabslabBlocks.DOUBLE_WALL.getDefaultState()
                        .with(DoubleWallBlock.AXIS, isXAxis ? Direction.Axis.X : Direction.Axis.Z);
                world.setBlockState(pos, doubleWallState);

                WallBlock base = isNegative ? placed : inventory;
                WallBlock extend = isNegative ? inventory : placed;
                DoubleWallEntity entity = new DoubleWallEntity(base, extend, pos, doubleWallState);
                world.addBlockEntity(entity);
                entity.setWorld(world);
                entity.markDirty();

                BlockSoundGroup group = inventory.getDefaultState().getSoundGroup();
                world.playSound(null, pos, group.getPlaceSound(), SoundCategory.BLOCKS,
                        (group.getVolume() + 1.0F) / 2.0F, group.getPitch() * 0.8F);
            });
        }));

    }
}
