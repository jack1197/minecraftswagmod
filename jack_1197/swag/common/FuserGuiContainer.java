package jack_1197.swag.common;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiSmallButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.common.network.PacketDispatcher;

public class FuserGuiContainer extends GuiContainer {
	protected FuserTileEntity tileEntity;
	protected GuiSmallButton button;

	public FuserGuiContainer(InventoryPlayer playerInventory, FuserTileEntity tileEntity) {
		super(new FuserContainer(tileEntity, playerInventory));
		this.tileEntity = tileEntity;
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if (button.id == 1) {
			if (tileEntity.direction == 0) {
				tileEntity.direction = 1;
			} else {
				tileEntity.direction = 0;
			}
		}
		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream(28);
		DataOutputStream dataOutputStream = new DataOutputStream(byteOutputStream);
		try {
			dataOutputStream.writeInt(SwagMod.packetType.FUSER_BUTTON_CLICK.ordinal());
			dataOutputStream.writeInt(tileEntity.xCoord);
			dataOutputStream.writeInt(tileEntity.yCoord);
			dataOutputStream.writeInt(tileEntity.zCoord);
			dataOutputStream.writeInt(1);// button id
			dataOutputStream.writeInt(tileEntity.direction);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "SwagMod";
		packet.data = byteOutputStream.toByteArray();
		packet.length = byteOutputStream.size();
		PacketDispatcher.sendPacketToServer(packet);// wont need world.isRemote,
													// gui only opens on client
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		int gui = mc.renderEngine.getTexture("/jack_1197/swag/common/textures/Fuser.png");
		int x = (width / 2 - 176 / 2);
		int y = (height / 2 - 178 / 2);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(gui);
		this.drawTexturedModalRect(x, y, 0, 0, 176, 178);
		int adjustedProgress = (int) Math.floor((float) tileEntity.progress / (float) tileEntity.maxProgress * 50F);
		if (tileEntity.direction == 0) {
			this.drawTexturedModalRect(x + 63, y + 27, 176, 0, adjustedProgress, 48);
		} else {
			this.drawTexturedModalRect(x + 113 - adjustedProgress, y + 27, 226 - adjustedProgress, 0, adjustedProgress, 48);
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int var1, int var2) {
		fontRenderer.drawString("Fuser", 6, 2, 0x404040);
		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 6, ySize - 88, 0x404040);
		button.displayString = tileEntity.direction == 0 ? ">" : "<";
	}

	@Override
	public void initGui() {
		super.initGui();
		int x = (width / 2 - 176 / 2);
		int y = (height / 2 - 178 / 2);
		button = new GuiSmallButton(1, x + 137, y + 41, 20, 20, ">");
		controlList.add(button);
	}
}
