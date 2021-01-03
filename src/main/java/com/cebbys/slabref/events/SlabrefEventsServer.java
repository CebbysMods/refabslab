package com.cebbys.slabref.events;

import com.cebbys.slabref.content.SlabrefSlabBlocks;
import com.cebbys.slabref.content.entities.DoubleSlabEntity;

import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public final class SlabrefEventsServer extends SlabrefEvents {
	
	public static final class Initialize {
		
		public static void doubleSlabCreatedEvent() {
			ServerSidePacketRegistry.INSTANCE.register(IDENTIFIER_PACKET, (context, data) -> {
				BlockPos pos = data.readBlockPos();
				Identifier base = data.readIdentifier();
				Identifier extend = data.readIdentifier();
				context.getTaskQueue().execute(() -> {
					World world = context.getPlayer().getEntityWorld();
					BlockState state = SlabrefSlabBlocks.DOUBLE_SLAB.getDefaultState();
					world.setBlockState(pos, state, 3);
					
					BlockSoundGroup group = Registry.BLOCK.get(extend).getDefaultState().getSoundGroup();
		            world.playSound(null, pos, group.getPlaceSound(), SoundCategory.BLOCKS, (group.getVolume() + 1.0F) / 2.0F, group.getPitch() * 0.8F);
					
		            DoubleSlabEntity entity = new DoubleSlabEntity(base, extend);
					world.setBlockEntity(pos, entity);
					entity.setLocation(world, pos);
					entity.sync();
				});
			});
		}
	}
	
}
