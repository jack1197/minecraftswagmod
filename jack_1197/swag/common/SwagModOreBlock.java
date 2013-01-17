package jack_1197.swag.common;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class SwagModOreBlock extends Block {
	public SwagModOreBlock(int par1, Material par2Material) {
		super(par1, par2Material);
	}

	public SwagModOreBlock(int par1, int par2, Material par3Material) {
		super(par1, par2, par3Material);
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int metadata) {
		if (metadata < 8) {
			return metadata * 16;
		} else {
			return (metadata-8) * 16 + 1;
		}
	}

	@Override
	public String getTextureFile() {
		return CommonProxy.BLOCKS;
	}

	@Override
	public int damageDropped(int metadata) {
		return metadata;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(int unknown, CreativeTabs tab, List subItems) {
		for (int i = 0; i < 16; i++) {
			subItems.add(new ItemStack(this, 1, i));
		}
	}
}
