package jack_1197.swag.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

//manages which gui opens and when
public class GuiHandler implements IGuiHandler {

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if (tileEntity instanceof SwagYoloConverterTileEntity) {
			return new SwagYoloConverterGuiContainer(player.inventory, (SwagYoloConverterTileEntity) tileEntity);
		}
		if (tileEntity instanceof FuserTileEntity) {
			return new FuserGuiContainer(player.inventory, (FuserTileEntity) tileEntity);
		}
		return null;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if (tileEntity instanceof SwagYoloConverterTileEntity) {
			return new SwagYoloConverterContainer((SwagYoloConverterTileEntity) tileEntity, player.inventory);
		}
		if (tileEntity instanceof FuserTileEntity) {
			return new FuserContainer((FuserTileEntity) tileEntity, player.inventory);
		}
		return null;
	}

}
