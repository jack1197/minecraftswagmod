package jack_1197.swag.common;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

public class SwagYoloConverterGuiContainer extends GuiContainer {
	protected SwagYoloConverterTileEntity tileEntity;

	public SwagYoloConverterGuiContainer(InventoryPlayer playerInventory, SwagYoloConverterTileEntity tileEntity) {
		super(new SwagYoloConverterContainer(tileEntity, playerInventory));
		this.tileEntity = tileEntity;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		// draw gui
		int gui = mc.renderEngine.getTexture("/jack_1197/swag/common/textures/SwagYoloConverter.png");
		int x = (width / 2 - 176 / 2);
		int y = (height / 2 - 178 / 2);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		float calculatedProgress = tileEntity.progress;
		calculatedProgress = calculatedProgress / tileEntity.maxProgress * 113;
		float calculatedFuel = tileEntity.fuel;
		calculatedFuel = calculatedFuel / tileEntity.maxFuel * 28;
		this.mc.renderEngine.bindTexture(gui);
		this.drawTexturedModalRect(x, y, 0, 0, 176, 178);
		this.drawTexturedModalRect(x + 31, y + 18, 0, 179, (int) calculatedProgress, 44);
		this.drawTexturedModalRect(x + 81, y + 62 - (int) calculatedFuel, 114, 179 + 28 - (int) calculatedFuel, 14, (int) calculatedFuel);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int i, int j) {
		// draw text
		fontRenderer.drawString("YOLO-O-Matic", 6, 2, 0x404040);
		fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 6, ySize - 88, 0x404040);
	}
}
