package jack_1197.swag.common;

import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.ItemSword;

public class SwagSwordItem extends ItemSword {
	public SwagSwordItem(int par1, EnumToolMaterial mat) {
		super(par1, mat);
	}
	@Override
	public String getTextureFile() {
		return CommonProxy.ITEMS;
	}

}
