package jack_1197.swag.common;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class ServerPacketHandler implements IPacketHandler {

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload payload, Player player) {
		// send to proper function
		if (payload.channel == "SwagMod") {
			System.out.println("Receiving SwagMod packet at server");
			swagModPacket(payload, (EntityPlayer) player);
		}
		return;
	}

	private void swagModPacket(Packet250CustomPayload packet, EntityPlayer player) {
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(packet.data));
		int type;
		try {
			// get the type of data expected for packet
			type = data.readInt();
			if (type == SwagMod.packetType.FUSER_BUTTON_CLICK.ordinal()) {
				// if this packet is for getting button clicks from client
				int x = data.readInt();
				int y = data.readInt();
				int z = data.readInt();
				TileEntity tileEntity = player.worldObj.getBlockTileEntity(x, y, z);
				if (tileEntity != null) {
					if (tileEntity instanceof FuserTileEntity) {
						// then set the appropriate properties
						FuserTileEntity typedTileEntity = (FuserTileEntity) tileEntity;
						if (data.readInt() == 1) {
							int dir = data.readInt();
							if (dir == 1 || dir == 0) {
								if (typedTileEntity.isUseableByPlayer(player)) {
									typedTileEntity.direction = dir;
								}
							} else {
								player.addChatMessage("Packet error, Hacking?");
							}
						}
					}
				}
			}
		} catch (IOException exception) {
			exception.printStackTrace();
			player.addChatMessage("Packet error, Hacking?");
		}
		return;
	}

}
