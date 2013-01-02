package jack_1197.swag.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SwagYoloConverterContainer extends Container {
	protected SwagYoloConverterTileEntity tileEntity;

	public SwagYoloConverterContainer(SwagYoloConverterTileEntity tileEntity,
			InventoryPlayer playerInventory) {
		this.tileEntity = tileEntity;
		int o = 0;
		addSlotToContainer(new Slot(tileEntity, 0, 8, 15));
		addSlotToContainer(new Slot(tileEntity, 1, 8, 37));
		addSlotToContainer(new Slot(tileEntity, 2, 8, 59));
		addSlotToContainer(new Slot(tileEntity, 3, 30, 59));
		addSlotToContainer(new Slot(tileEntity, 4, 52, 59));
		addSlotToContainer(new Slot(tileEntity, 5, 80, 59));
		addSlotToContainer(new Slot(tileEntity, 6, 107, 59));
		addSlotToContainer(new Slot(tileEntity, 7, 130, 59));
		addSlotToContainer(new Slot(tileEntity, 8, 152, 59));
		addSlotToContainer(new Slot(tileEntity, 9, 152, 37));
		addSlotToContainer(new Slot(tileEntity, 10, 152, 15));
		bindPlayerInventory(playerInventory);
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return tileEntity.isUseableByPlayer(player);
	}

	protected void bindPlayerInventory(InventoryPlayer playerInventory) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9,
						8 + j * 18, 91 + i * 18));
			}
		}
		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 149));
		}
	}
	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		return null;
	}
	
	public ItemStack putStackInSlot(int index) {
		ItemStack stack = null;
		Slot slotObject = (Slot) inventorySlots.get(index);
		if (slotObject != null && slotObject.getHasStack()) {
			ItemStack stackInSlot = slotObject.getStack();
			stack = stackInSlot.copy();
			if (index == 0) {
				if (!mergeItemStack(stackInSlot, 1, inventorySlots.size(), true)) {
					return null;
				}
			} else if (!mergeItemStack(stackInSlot, 0, 1, false)) {
				return null;
			}
			if (stackInSlot.stackSize == 0) {
				slotObject.putStack(null);
			} else {
				slotObject.onSlotChanged();
			}
		}
		return stack;

	}
}
