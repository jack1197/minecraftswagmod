package jack_1197.swag.common;

import net.minecraft.item.Item;

//generic item
public class SwagModItem extends Item {

	public SwagModItem(int par1) {
		super(par1);
	}

	@Override
	public String getTextureFile() {
		return CommonProxy.ITEMS;
	}
}
