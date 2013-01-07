package jack_1197.swag.common;

import net.minecraft.item.EnumToolMaterial;

public class SwagPickaxeItem extends net.minecraft.item.ItemPickaxe {

	public SwagPickaxeItem(int id, EnumToolMaterial material) {
		super(id, material);
	}

	@Override
	public String getTextureFile() {
		return CommonProxy.ITEMS;
	}

}
