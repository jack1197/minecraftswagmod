package jack_1197.swag.common;

import net.minecraft.src.Item;

public class ItemSwagModIngot extends Item {

	public ItemSwagModIngot(int par1) {
		super(par1);
	}
	@Override
	public String getTextureFile() {
		return CommonProxy.ITEMS;
	}
}
