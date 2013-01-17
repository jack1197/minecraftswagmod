package jack_1197.swag.common;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class SwagModOreItemBlock extends ItemBlock {
	

	
	public SwagModOreItemBlock(int par1) {
		super(par1);
		setHasSubtypes(true);
	}
	
	@Override
	public int getMetadata(int damageValue){
		return damageValue;
	}
	@Override
	public String getItemNameIS(ItemStack itemStack){
		int damage = itemStack.getItemDamage();
		return getItemName() + ".LV" + (damage < 8 ? (damage) + "Swag" : (damage - 8) + "Yolo");
	}
}
