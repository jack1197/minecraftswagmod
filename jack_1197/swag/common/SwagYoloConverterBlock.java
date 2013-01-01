package jack_1197.swag.common;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class SwagYoloConverterBlock extends BlockContainer {

	protected SwagYoloConverterBlock(int par1, int par2, Material par3Material) {
		super(par1, par2, par3Material);
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return null;
	}

	@Override
	public String getTextureFile() {
		return CommonProxy.BLOCKS;
	}
}
