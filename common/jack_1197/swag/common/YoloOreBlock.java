package jack_1197.swag.common;

import net.minecraft.src.Block;
import net.minecraft.src.Material;

public class YoloOreBlock extends Block {

	public YoloOreBlock(int par1, Material par2Material) {
		super(par1, par2Material);
	}

	public YoloOreBlock(int par1, int par2, Material par3Material) {
		super(par1, par2, par3Material);
	}
	@Override
	public String getTextureFile() {
		return CommonProxy.BLOCKS;
	}
}
