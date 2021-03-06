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

public class SwagYoloConverterBlock extends BlockContainer {

	private static boolean keepInventory = false;

	public static void changeState(World world, int x, int y, int z, boolean lit) {
		int meta = world.getBlockMetadata(x, y, z);
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		keepInventory = true;

		if (lit) {
			world.setBlockWithNotify(x, y, z, SwagMod.swagYoloConverterFueledBlock.blockID);
		} else {
			world.setBlockWithNotify(x, y, z, SwagMod.swagYoloConverterBlock.blockID);
		}

		keepInventory = false;
		world.setBlockMetadataWithNotify(x, y, z, meta);

		if (tileEntity != null) {
			tileEntity.validate();
			world.setBlockTileEntity(x, y, z, tileEntity);
		}
	}

	protected SwagYoloConverterBlock(int par1, int par2, Material par3Material) {
		super(par1, par2, par3Material);
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int i, int j) {
		if (!keepInventory)
			dropItems(world, x, y, z);
		super.breakBlock(world, x, y, z, i, j);
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new SwagYoloConverterTileEntity();
	}

	// manage item drops
	public void dropItems(World world, int x, int y, int z) {
		Random rand = new Random();
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if (!(tileEntity instanceof SwagYoloConverterTileEntity)) {
			return;
		}
		SwagYoloConverterTileEntity typedTileEntity = (SwagYoloConverterTileEntity) tileEntity;
		if (SwagMod.swagYoloConvCanExplode && typedTileEntity.fuel > 12000) {
			int power = (typedTileEntity.fuel - 12000) / 3000;
			world.createExplosion(null, x, y, z, power, true);
		}
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
		case 0:
			return 6;
		case 1:
			return this.blockIndexInTexture;
		case 2:
			if (meta == 2)
				return 8;
			break;
		case 3:
			if (meta == 0)
				return 8;
			break;
		case 4:
			if (meta == 1)
				return 8;
			break;
		case 5:
			if (meta == 3)
				return 8;
			break;
		}
		return 4;
	}

	@Override
	public String getTextureFile() {
		return CommonProxy.BLOCKS;
	}

	// open gui when activated
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int i, float f, float g, float t) {
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if (tileEntity == null || player.isSneaking()) {
			return false;
		}
		player.openGui(SwagMod.Instance, 0, world, x, y, z);
		return true;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entity) {
		world.setBlockMetadata(x, y, z, MathHelper.floor_double((entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3 ^ 2);
	}
}
