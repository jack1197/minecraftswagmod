package jack_1197.swag.common;

import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemSword;

public class SwagToolItem extends ItemSword {
	public SwagToolItem(int par1, EnumToolMaterial mat) {
		super(par1, mat);
	}

	@Override
	public String getTextureFile() {
		return CommonProxy.ITEMS;
	}

}
