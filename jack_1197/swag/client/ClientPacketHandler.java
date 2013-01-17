package jack_1197.swag.client;

import jack_1197.swag.common.FuserTileEntity;
import jack_1197.swag.common.SwagMod;
import jack_1197.swag.common.SwagYoloConverterTileEntity;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class ClientPacketHandler implements IPacketHandler {

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload payload, Player player) {
		// if packet does belong to mod, redirect to proper function
		if (payload.channel == "SwagMod") {
			swagModPacket(payload, (EntityPlayer) player);
		}
		return;
	}

	private void swagModPacket(Packet250CustomPayload packet, EntityPlayer player) {
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(packet.data));
		int type;
		try {
			// get the tpye of data expected for packet
			type = data.readInt();
			if (type == SwagMod.packetType.SWAG_YOLO_CONVERTER_UPDATE.ordinal()) {
				// if it is a packet with yolo-o-matic data then find the tile
				// entity
				int x = data.readInt();
				int y = data.readInt();
				int z = data.readInt();
				TileEntity tileEntity = player.worldObj.getBlockTileEntity(x, y, z);
				if (tileEntity != null) {
					if (tileEntity instanceof SwagYoloConverterTileEntity) {
						// then set the approprite properties
						SwagYoloConverterTileEntity typedTileEntity = (SwagYoloConverterTileEntity) tileEntity;
						typedTileEntity.progress = data.readInt();
						typedTileEntity.fuel = data.readInt();
						typedTileEntity.burning = data.readBoolean();
					}
				}
			}
			if (type == SwagMod.packetType.FUSER_UPDATE.ordinal()) {
				// if it is a packet with fuser data then find the tile entity
				int x = data.readInt();
				int y = data.readInt();
				int z = data.readInt();
				TileEntity tileEntity = player.worldObj.getBlockTileEntity(x, y, z);
				if (tileEntity != null) {
					if (tileEntity instanceof FuserTileEntity) {
						// then set the approprite properties
						FuserTileEntity typedTileEntity = (FuserTileEntity) tileEntity;
						typedTileEntity.progress = data.readInt();
						typedTileEntity.direction = data.readInt();
					}
				}
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
