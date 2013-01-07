package jack_1197.swag.common;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.PacketDispatcher;

public class FuserTileEntity extends TileEntity implements IInventory {
	private ItemStack[] inventory = new ItemStack[3];
	public int progress = 300;
	public static int maxProgress = 600;
	public int direction = 0;// 0 = infuse, 1 = defuse
	private static int updateInterval = 5;
	private int update = 5;
	public boolean processing = false;

	protected static ItemStack[][] fuseList = {
			{
					new ItemStack(Block.stone),
					new ItemStack(SwagMod.swagDropItem),
					new ItemStack(SwagMod.swagOreBlock) },
			{
					new ItemStack(Block.stone),
					new ItemStack(SwagMod.yoloEssenceDenseItem),
					new ItemStack(SwagMod.yoloOreBlock) },
			{
					new ItemStack(Item.pickaxeDiamond),
					new ItemStack(SwagMod.swagDropItem, 3),
					new ItemStack(SwagMod.swagPickaxeItem) },
			{
					new ItemStack(Item.axeDiamond),
					new ItemStack(SwagMod.swagDropItem, 3),
					new ItemStack(SwagMod.swagAxeItem) },
			{
					new ItemStack(Item.shovelDiamond),
					new ItemStack(SwagMod.swagDropItem, 3),
					new ItemStack(SwagMod.swagShovelItem) },
			{
					new ItemStack(Item.swordDiamond),
					new ItemStack(SwagMod.swagDropItem, 3),
					new ItemStack(SwagMod.swagSwordItem) },
			{
					new ItemStack(Item.hoeDiamond),
					new ItemStack(SwagMod.swagDropItem, 3),
					new ItemStack(SwagMod.swagHoeItem) },
			{
					new ItemStack(Item.pickaxeDiamond),
					new ItemStack(SwagMod.yoloOrbItem, 1),
					new ItemStack(SwagMod.yoloPickaxeItem) },
			{
					new ItemStack(Item.axeDiamond),
					new ItemStack(SwagMod.yoloOrbItem, 1),
					new ItemStack(SwagMod.yoloAxeItem) },
			{
					new ItemStack(Item.shovelDiamond),
					new ItemStack(SwagMod.yoloOrbItem, 1),
					new ItemStack(SwagMod.yoloShovelItem) },
			{
					new ItemStack(Item.swordDiamond),
					new ItemStack(SwagMod.yoloOrbItem, 1),
					new ItemStack(SwagMod.yoloSwordItem) },
			{
					new ItemStack(Item.hoeDiamond),
					new ItemStack(SwagMod.yoloOrbItem, 1),
					new ItemStack(SwagMod.yoloHoeItem) }, };

	public boolean canProcess() {
		return true;
	}

	@Override
	public void closeChest() {
	}

	@Override
	public ItemStack decrStackSize(int index, int amount) {
		ItemStack stack = getStackInSlot(index);
		if (stack != null) {
			if (stack.stackSize <= amount) {
				setInventorySlotContents(index, null);
			} else {
				stack = stack.splitStack(amount);
				if (stack.stackSize == 0) {
					setInventorySlotContents(index, null);
				}
			}
		}
		return stack;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public String getInvName() {
		return "Fuser";
	}

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return inventory[slot];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		ItemStack stack = getStackInSlot(slot);
		if (stack != null) {
			setInventorySlotContents(slot, null);
		}
		return stack;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
	}

	void onDone() {

	}

	@Override
	public void openChest() {
	}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		// get inventory
		NBTTagList tagList = tagCompound.getTagList("Inventory");
		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) tagList.tagAt(i);
			byte slot = tag.getByte("Slot");
			if (slot >= 0 && slot < inventory.length) {
				inventory[slot] = ItemStack.loadItemStackFromNBT(tag);
			}
		}
		// get stats
		progress = tagCompound.getInteger("progress");
	}

	public void sendUpdatePackets() {
		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream(28);
		DataOutputStream dataOutputStream = new DataOutputStream(byteOutputStream);
		try {
			dataOutputStream.writeInt(SwagMod.packetType.FUSER_UPDATE.ordinal());
			dataOutputStream.writeInt(xCoord);
			dataOutputStream.writeInt(yCoord);
			dataOutputStream.writeInt(zCoord);
			dataOutputStream.writeInt(progress);
			dataOutputStream.writeInt(direction);
			dataOutputStream.writeBoolean(processing);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "SwagMod";
		packet.data = byteOutputStream.toByteArray();
		packet.length = byteOutputStream.size();
		PacketDispatcher.sendPacketToAllPlayers(packet);
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		inventory[slot] = stack;
	}

	@Override
	public void updateEntity() {
		if (!this.worldObj.isRemote && update == 0) {
			update = updateInterval;
			sendUpdatePackets();
		} else {
			update--;
		}
		if (canProcess()) {
			if (++progress > maxProgress) {
				progress = 0;
				onDone();
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		NBTTagList tagList = new NBTTagList();
		// write inventory
		for (int i = 0; i < inventory.length; i++) {
			ItemStack stack = inventory[i];
			if (stack != null) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setByte("Slot", (byte) i);
				stack.writeToNBT(tag);
				tagList.appendTag(tag);
			}
		}
		// write stats
		tagCompound.setTag("Inventory", tagList);
		tagCompound.setInteger("progress", progress);
		tagCompound.setString("id", "fuser");
		tagCompound.setInteger("x", this.xCoord);
		tagCompound.setInteger("y", this.yCoord);
		tagCompound.setInteger("z", this.zCoord);
	}
}
