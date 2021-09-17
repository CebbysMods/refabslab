package lv.cebbys.mcmods.refabslab.events;

import io.netty.buffer.Unpooled;
import lv.cebbys.mcmods.refabslab.content.blocks.WallBlock;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

public final class RefabslabEventsClient extends RefabslabEvents {

	public static final class Execute {

		public static void createDoubleSlabEvent(BlockPos pos, Identifier bottom, Identifier top) {
			PacketByteBuf data = new PacketByteBuf(Unpooled.buffer());
			data.writeBlockPos(pos);
			data.writeIdentifier(bottom);
			data.writeIdentifier(top);
			ClientPlayNetworking.send(DOUBLE_SLAB_PLACED_EVENT, data);
		}

		public static void createDoubleWallEvent(BlockPos pos, WallBlock base, WallBlock extend, Direction facing) {
			PacketByteBuf data = new PacketByteBuf(Unpooled.buffer());
			data.writeBlockPos(pos);
			data.writeIdentifier(Registry.BLOCK.getId(base));
			data.writeIdentifier(Registry.BLOCK.getId(extend));
			data.writeBoolean(facing.getAxis().equals(Direction.Axis.X));
			data.writeBoolean(facing.getDirection().equals(Direction.AxisDirection.NEGATIVE));
			ClientPlayNetworking.send(DOUBLE_WALL_PLACED_EVENT, data);
		}

	}

}
