package jack_1197.swag.common;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

//generic block class
public class SwagModBlock extends Block {

	public SwagModBlock(int par1, int par2, Material par3Material) {
		super(par1, par2, par3Material);
	}

	public SwagModBlock(int par1, Material par2Material) {
		super(par1, par2Material);
	}

	@Override
	public String getTextureFile() {
		return CommonProxy.BLOCKS;
	}
}
