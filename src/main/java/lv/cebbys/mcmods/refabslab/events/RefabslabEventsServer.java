package lv.cebbys.mcmods.refabslab.events;

import lv.cebbys.mcmods.refabslab.content.RefabslabSlabBlocks;
import lv.cebbys.mcmods.refabslab.content.entities.DoubleSlabEntity;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public final class RefabslabEventsServer extends RefabslabEvents {
	
	public static final class Initialize {
		
		public static void doubleSlabCreatedEvent() {
			ServerSidePacketRegistry.INSTANCE.register(IDENTIFIER_PACKET, (context, data) -> {
				BlockPos pos = data.readBlockPos();
				Identifier base = data.readIdentifier();
				Identifier extend = data.readIdentifier();
				context.getTaskQueue().execute(() -> {
					World world = context.getPlayer().getEntityWorld();
					BlockState state = RefabslabSlabBlocks.DOUBLE_SLAB.getDefaultState();
					world.setBlockState(pos, state, 3);
					
					BlockSoundGroup group = Registry.BLOCK.get(extend).getDefaultState().getSoundGroup();
		            world.playSound(null, pos, group.getPlaceSound(), SoundCategory.BLOCKS, (group.getVolume() + 1.0F) / 2.0F, group.getPitch() * 0.8F);
					
		            DoubleSlabEntity entity = new DoubleSlabEntity(base, extend, pos, state);
		            world.addBlockEntity(entity);
					entity.setWorld(world);
					entity.sync();
				});
			});
		}
	}
	
}
