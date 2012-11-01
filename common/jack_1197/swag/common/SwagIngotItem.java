package jack_1197.swag.common;

import net.minecraft.src.Item;

public class SwagIngotItem extends Item {

	public SwagIngotItem(int par1) {
		super(par1);
	}
	@Override
	public String getTextureFile() {
		return CommonProxy.ITEMS;
	}
}
