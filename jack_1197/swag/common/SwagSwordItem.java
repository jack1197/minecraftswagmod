package jack_1197.swag.common;

import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemSword;

//basic sword class, could probably strip it out if needed
public class SwagSwordItem extends ItemSword {
	public SwagSwordItem(int par1, EnumToolMaterial mat) {
		super(par1, mat);
	}

	@Override
	public String getTextureFile() {
		return CommonProxy.ITEMS;
	}

}
