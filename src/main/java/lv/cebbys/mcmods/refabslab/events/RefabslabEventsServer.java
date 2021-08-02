package lv.cebbys.mcmods.refabslab.events;

import lv.cebbys.mcmods.refabslab.content.RefabslabSlabBlocks;
import lv.cebbys.mcmods.refabslab.content.entities.DoubleSlabEntity;
import lv.cebbys.mcmods.celib.loggers.CelibLogger;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.impl.networking.server.ServerPlayNetworkAddon;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public final class RefabslabEventsServer extends RefabslabEvents {
	
	public static final class Initialize {
		
		public static void doubleSlabCreatedEvent() {
			ServerPlayNetworking.registerGlobalReceiver(IDENTIFIER_PACKET, (server, player, handler, data, responseSender) -> {
				BlockPos pos = data.readBlockPos();
				Identifier bottom = data.readIdentifier();
				Identifier top = data.readIdentifier();
				World world = player.getServerWorld();

				server.execute(() -> {
					player.getMainHandStack().decrement(1);

					BlockState state = RefabslabSlabBlocks.DOUBLE_SLAB.getDefaultState();
					world.setBlockState(pos, state, 3);

					DoubleSlabEntity entity = new DoubleSlabEntity(bottom, top, pos, state);
					world.addBlockEntity(entity);
					entity.setWorld(world);
					entity.sync();

					BlockSoundGroup group = Registry.BLOCK.get(top).getDefaultState().getSoundGroup();
					world.playSound(null, pos, group.getPlaceSound(), SoundCategory.BLOCKS,
							(group.getVolume() + 1.0F) / 2.0F, group.getPitch() * 0.8F);
				});
			});
		}
	}
	
}
