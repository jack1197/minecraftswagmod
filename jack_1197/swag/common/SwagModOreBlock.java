package jack_1197.swag.common;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class SwagModOreBlock extends Block {

	public SwagModOreBlock(int par1, Material par2Material) {
		super(par1, par2Material);
	}

	public SwagModOreBlock(int par1, int par2, Material par3Material) {
		super(par1, par2, par3Material);
	}

	@Override
	public String getTextureFile() {
		return CommonProxy.BLOCKS;
	}
}
