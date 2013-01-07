package jack_1197.swag.common;

import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemAxe;

public class SwagAxeItem extends ItemAxe {

	public SwagAxeItem(int par1, EnumToolMaterial par2EnumToolMaterial) {
		super(par1, par2EnumToolMaterial);
	}

	@Override
	public String getTextureFile() {
		return CommonProxy.ITEMS;
	}
}
