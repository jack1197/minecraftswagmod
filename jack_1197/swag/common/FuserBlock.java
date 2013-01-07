package jack_1197.swag.common;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class FuserBlock extends BlockContainer {

	protected FuserBlock(int par1, int par2, Material par3Material) {
		super(par1, par2, par3Material);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int i, int j) {
		dropItems(world, x, y, z);
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new FuserTileEntity();
	}

	// manage item drops
	public void dropItems(World world, int x, int y, int z) {
		Random rand = new Random();
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if (!(tileEntity instanceof FuserTileEntity)) {
			return;
		}
		FuserTileEntity typedTileEntity = (FuserTileEntity) tileEntity;
		for (int i = 0; i < typedTileEntity.getSizeInventory(); i++) {
			ItemStack item = typedTileEntity.getStackInSlot(i);
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
				entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
				entityItem.motionZ = rand.nextGaussian() * factor;
				world.spawnEntityInWorld(entityItem);
				item.stackSize = 0;
			}
		}

	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int side, int meta) {
		switch (side) {
		case 2:
			if (meta == 2)
				return 19;
			break;
		case 3:
			if (meta == 0)
				return 19;
			break;
		case 4:
			if (meta == 1)
				return 19;
			break;
		case 5:
			if (meta == 3)
				return 19;
			break;
		}
		return 18;
	}

	@Override
	public String getTextureFile() {
		return CommonProxy.BLOCKS;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f, float g, float t) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if (tileEntity == null || player.isSneaking()) {
			return false;
		}
		player.openGui(SwagMod.Instance, 1, world, x, y, z);
		return true;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entity) {
		world.setBlockMetadata(x, y, z, MathHelper.floor_double((entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3 ^ 2);
	}

}
