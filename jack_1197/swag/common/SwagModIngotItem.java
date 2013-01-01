package jack_1197.swag.common;

import net.minecraft.item.Item;

public class SwagModIngotItem extends Item {

	public SwagModIngotItem(int par1) {
		super(par1);
	}
	@Override
	public String getTextureFile() {
		return CommonProxy.ITEMS;
	}
}
