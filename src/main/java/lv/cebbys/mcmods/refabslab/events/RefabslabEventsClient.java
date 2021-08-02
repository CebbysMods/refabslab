package lv.cebbys.mcmods.refabslab.events;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public final class RefabslabEventsClient extends RefabslabEvents {

	public static final class Execute {

		public static void createDoubleSlabEvent(BlockPos pos, Identifier bottom, Identifier top) {
			PacketByteBuf data = new PacketByteBuf(Unpooled.buffer());
			data.writeBlockPos(pos);
			data.writeIdentifier(bottom);
			data.writeIdentifier(top);
			ClientPlayNetworking.send(IDENTIFIER_PACKET, data);
		}
		
	}
	
}
