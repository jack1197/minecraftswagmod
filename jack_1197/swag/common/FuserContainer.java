package jack_1197.swag.common;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class FuserContainer extends Container {
	protected FuserTileEntity tileEntity;

	public FuserContainer(FuserTileEntity tileEntity, InventoryPlayer playerInventory) {
		this.tileEntity = tileEntity;
		addSlotToContainer(new Slot(tileEntity, 0, 44, 19));
		addSlotToContainer(new Slot(tileEntity, 1, 44, 55));
		addSlotToContainer(new Slot(tileEntity, 2, 116, 37));
		bindPlayerInventory(playerInventory);
	}

	protected void bindPlayerInventory(InventoryPlayer playerInventory) {
		// bind player inventory
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 9; j++) {
				addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 91 + i * 18));
			}
		}
		for (int i = 0; i < 9; i++) {
			addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 149));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return tileEntity.isUseableByPlayer(var1);
	}

	public ItemStack putStackInSlot(int index) {
		// for right clicking
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

	@Override
	// to stop crashes when shift clicking
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		return null;
	}
}
