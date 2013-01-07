package jack_1197.swag.common;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.common.ISidedInventory;
import cpw.mods.fml.common.network.PacketDispatcher;

public class SwagYoloConverterTileEntity extends TileEntity implements IInventory, ISidedInventory {

	static public int getTotalYolo(ItemStack[] stacks) {
		int yolo = 0;
		if (stacks == null) {
			return 0;
		}
		for (int i = 0; i < stacks.length; i++) {
			if (stacks[i] != null) {
				if (stacks[i].getItem() == SwagMod.yoloEssenceItem) {
					yolo = yolo + 1;
				}
				if (stacks[i].getItem() == SwagMod.yoloDropItem) {
					yolo = yolo + 32;
				}
				if (stacks[i].getItem() == SwagMod.yoloOrbItem) {
					yolo = yolo + 1024;
				}
			}
		}
		return yolo;
	}

	ItemStack[] inventory = new ItemStack[11];
	public int progress = 0;
	public final int maxProgress = 600;
	public int fuel = 0;
	public final int maxFuel = 36000;
	final int burnSlot = 5;// base 0
	public boolean burning = false;
	int updateTime = 20;
	final int updateInterval = 5;

	int yolo = 0;

	void burnItems() {
		ItemStack stack = getStackInSlot(burnSlot);
		int itemValue = SwagMod.getSwagValue(stack);
		int remaining = maxFuel - fuel;
		int maxItemsOnFill = (int) Math.floor((float) remaining / (float) itemValue);
		if (maxItemsOnFill != 0) {
			if (maxItemsOnFill > stack.stackSize) {
				fuel = fuel + stack.stackSize * itemValue;
				decrStackSize(burnSlot, stack.stackSize);
				stack = null;
			} else {
				fuel = fuel + maxItemsOnFill * itemValue;
				decrStackSize(burnSlot, maxItemsOnFill);
			}
		}
	}

	// whether or not there are items to process, wont deplete item-fuel when
	// false
	private boolean canBurn() {
		return (inventory[4] != null && SwagMod.getYoloValue(inventory[4]) <= 327680 - yolo);
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

	public void dropItemStack(World world, int x, int y, int z, ItemStack[] items) {
		Random rand = new Random();
		for (int i = 0; i < items.length; i++) {
			ItemStack item = items[i];
			if (item != null) {
				float rx = rand.nextFloat() * 0.6F + 0.1F;
				float ry = rand.nextFloat() * 0.6F + 0.1F;
				float rz = rand.nextFloat() * 0.6F + 0.1F;
				EntityItem entityItem = new EntityItem(world, x + rx, y + ry, z + rz, new ItemStack(item.itemID, item.stackSize, item.getItemDamage()));
				if (item.hasTagCompound()) {
					entityItem.func_92014_d().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
				}
				float factor = 0.1F;
				entityItem.motionX = rand.nextGaussian() * factor;
				entityItem.motionY = rand.nextGaussian() * factor;
				entityItem.motionZ = rand.nextGaussian() * factor;
				world.spawnEntityInWorld(entityItem);
				item.stackSize = 0;
			}
		}

	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public String getInvName() {
		return "Yolo-o-Matic";
	}

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	// gets the amount of fuel value for a certain item

	@Override
	public int getSizeInventorySide(ForgeDirection side) {
		int meta = this.getBlockMetadata();
		if (side == side.DOWN) {
			return 1;
		} else if (side == side.NORTH || side == side.SOUTH) {
			if ((meta & 1) == 3)// meta & 1 == 3, is the same as checking:meta &
								// 3 == 3, and: meta & 3 == 1
				return 5;
		} else if (side == side.EAST || side == side.WEST) {
			if ((meta & 1) != 3)// meta & 1 != 3, is the same as checking: meta
								// & 3 != 3, and: meta & 3 != 1
				return 5;
		}
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return inventory[index];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int index) {
		ItemStack stack = getStackInSlot(index);
		if (stack != null) {
			setInventorySlotContents(index, null);
		}
		return stack;
	}

	@Override
	public int getStartInventorySide(ForgeDirection side) {
		int meta = this.getBlockMetadata();
		if (side == side.DOWN) {
			return 5;
		} else if (side == side.NORTH) {
			if ((meta & 3) == 3)
				return 0;
			if ((meta & 3) == 1)
				return 6;
		} else if (side == side.SOUTH) {
			if ((meta & 3) == 1)
				return 0;
			if ((meta & 3) == 3)
				return 6;
		} else if (side == side.EAST) {
			if ((meta & 3) == 0)
				return 0;
			if ((meta & 3) == 2)
				return 6;
		} else if (side == side.WEST) {
			if ((meta & 3) == 0)
				return 6;
			if ((meta & 3) == 2)
				return 0;
		}
		return 0;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getBlockTileEntity(xCoord, yCoord, zCoord) == this && player.getDistanceSq(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5) < 64;
	}

	private void onDone() {
		if (inventory[4] != null) {
			int yolo = SwagMod.getYoloValue(inventory[4]);
			if (inventory[4].stackSize == 1) {
				inventory[4] = null;
			} else {
				inventory[4].stackSize--;
			}
			sort(yolo);
		}
	}

	@Override
	public void onInventoryChanged() {
		super.onInventoryChanged();
		sort();
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
		burning = tagCompound.getBoolean("burning");
		fuel = tagCompound.getInteger("fuel");
		sort();
	}

	private void sendUpdatePackets() {

		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream(28);
		DataOutputStream dataOutputStream = new DataOutputStream(byteOutputStream);
		try {
			dataOutputStream.writeInt(SwagMod.packetType.SWAG_YOLO_CONVERTER_UPDATE.ordinal());
			dataOutputStream.writeInt(xCoord);
			dataOutputStream.writeInt(yCoord);
			dataOutputStream.writeInt(zCoord);
			dataOutputStream.writeInt(progress);
			dataOutputStream.writeInt(fuel);
			dataOutputStream.writeBoolean(burning);
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

	void sort() {
		sort(0);
	}

	void sort(int additionalYolo) {
		// sort input
		for (int i = 4; i > 0; i--) {
			if (inventory[i] == null || inventory[i].stackSize == 0) {
				for (int j = i - 1; j >= 0; j--) {
					if (inventory[j] != null && inventory[j].stackSize != 0) {
						inventory[i] = inventory[j];
						inventory[j] = null;
						break;
					}
				}
			} else {
				for (int j = i - 1; j >= 0; j--) {
					if (inventory[j] != null && inventory[i].getItem() == inventory[j].getItem() && inventory[i].getItemDamage() == inventory[j].getItemDamage()) {
						if (inventory[i].stackSize + inventory[j].stackSize <= inventory[i].getMaxStackSize()) {
							inventory[i].stackSize = inventory[i].stackSize + inventory[j].stackSize;
							inventory[j] = null;
						} else {
							inventory[j].stackSize = inventory[j].stackSize - (inventory[i].getMaxStackSize() - inventory[i].stackSize);
							inventory[i].stackSize = inventory[i].getMaxStackSize();
						}
					}
				}
			}
		}
		// regroup output
		int yolo = additionalYolo;
		for (int i = 6; i < 11; i++) {
			if (inventory[i] != null) {
				// eject invalid items in output
				if (inventory[i].getItem() != SwagMod.yoloEssenceItem && inventory[i].getItem() != SwagMod.yoloEssenceDenseItem && inventory[i].getItem() != SwagMod.yoloDropItem
						&& inventory[i].getItem() != SwagMod.yoloDropDenseItem && inventory[i].getItem() != SwagMod.yoloOrbItem && this.worldObj.isRemote == false) {
					ItemStack[] item = new ItemStack[1];
					item[0] = inventory[i];
					dropItemStack(worldObj, xCoord, yCoord, zCoord, item);
				}
				if (inventory[i].getItem() == SwagMod.yoloEssenceItem) {
					yolo = yolo + 1 * inventory[i].stackSize;
				}
				if (inventory[i].getItem() == SwagMod.yoloEssenceDenseItem) {
					yolo = yolo + 4 * inventory[i].stackSize;
				}
				if (inventory[i].getItem() == SwagMod.yoloDropItem) {
					yolo = yolo + 32 * inventory[i].stackSize;
				}
				if (inventory[i].getItem() == SwagMod.yoloDropDenseItem) {
					yolo = yolo + 128 * inventory[i].stackSize;
				}
				if (inventory[i].getItem() == SwagMod.yoloOrbItem) {
					yolo = yolo + 1024 * inventory[i].stackSize;
				}
			}
			inventory[i] = null;
		}
		if (yolo > 0) {
			// repopulate output
			int usedSlots = 0;
			if (yolo >= 1024) {
				int items = (int) Math.floor(yolo / 1024.0F);
				while (items > 0) {
					if (items > 64) {
						inventory[10 - usedSlots] = new ItemStack(SwagMod.yoloOrbItem, 64);
						usedSlots++;
						items = items - 64;
						yolo = yolo - 1024 * 64;
					} else {
						inventory[10 - usedSlots] = new ItemStack(SwagMod.yoloOrbItem, items);
						usedSlots++;
						yolo = yolo - 1024 * items;
						items = 0;
					}
				}
			}
			if (yolo >= 128) {
				int items = (int) Math.floor(yolo / 128.0F);
				inventory[10 - usedSlots] = new ItemStack(SwagMod.yoloDropDenseItem, items);
				usedSlots++;
				yolo = yolo - items * 128;
			}
			if (yolo >= 32) {
				int items = (int) Math.floor(yolo / 32.0F);
				inventory[10 - usedSlots] = new ItemStack(SwagMod.yoloDropItem, items);
				usedSlots++;
				yolo = yolo - items * 32;
			}
			if (yolo >= 4) {
				int items = (int) Math.floor(yolo / 4.0F);
				inventory[10 - usedSlots] = new ItemStack(SwagMod.yoloEssenceDenseItem, items);
				usedSlots++;
				yolo = yolo - items * 4;
			}
			if (yolo >= 1) {
				inventory[10 - usedSlots] = new ItemStack(SwagMod.yoloEssenceItem, yolo);
				usedSlots++;
				yolo = 0;
			}
		}
		this.yolo = yolo;
	}

	@Override
	public void updateEntity() {
		// make the progress go up every tick
		if (burning) {
			progress++;
			if (progress > maxProgress) {
				progress = 0;
				onDone();
			}
			if (!canBurn()) {
				burning = false;
				progress = 0;
			}
			fuel--;
		} else if (fuel > 0 && canBurn()) {
			burning = true;
		}
		if (fuel <= 0) {
			fuel = 0;
			burning = false;
			progress = 0;
			SwagYoloConverterBlock.changeState(worldObj, xCoord, yCoord, zCoord, false);
		} else {
			SwagYoloConverterBlock.changeState(worldObj, xCoord, yCoord, zCoord, true);
		}
		if (!this.worldObj.isRemote && updateTime <= 0) {
			sendUpdatePackets();
			updateTime = updateInterval;
		} else {
			updateTime--;
		}
		if (canBurn() && getStackInSlot(burnSlot) != null && getStackInSlot(burnSlot).stackSize != 0 && SwagMod.getSwagValue(getStackInSlot(burnSlot)) != 0) {
			burnItems();
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
		tagCompound.setInteger("fuel", fuel);
		tagCompound.setBoolean("burning", burning);
		tagCompound.setString("id", "swagYoloConverter");
		tagCompound.setInteger("x", this.xCoord);
		tagCompound.setInteger("y", this.yCoord);
		tagCompound.setInteger("z", this.zCoord);
	}
}
