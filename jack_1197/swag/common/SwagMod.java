package jack_1197.swag.common;

import javax.print.attribute.standard.Sides;

import jack_1197.swag.client.ClientPacketHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.EnumHelper;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

/* Swag mod by jack_1197
 * as this is one of my first mods some parts may be little modified tutorial code snippets
 */
@Mod(modid = "SwagMod", name = "Swag Mod", version = "0.0.6 Pre-Alpha")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, clientPacketHandlerSpec = @SidedPacketHandler(channels = { "SwagMod" }, packetHandler = ClientPacketHandler.class), serverPacketHandlerSpec = @SidedPacketHandler(channels = { "SwagMod" }, packetHandler = ServerPacketHandler.class))
public class SwagMod {

	static EnumToolMaterial swagToolMaterial = EnumHelper.addToolMaterial(
			"swag", 2, 1000, 10F, 8, 20);
	public static final Block swagOreBlock = new SwagModOreBlock(500, 0,
			Material.rock).setHardness(2.0f)
			.setStepSound(Block.soundStoneFootstep).setLightValue(0.3f)
			.setCreativeTab(CreativeTabs.tabBlock).setBlockName("SwagOre");
	public static final Block yoloOreBlock = new SwagModOreBlock(501, 1,
			Material.rock).setHardness(2.0f)
			.setStepSound(Block.soundStoneFootstep).setLightValue(0.4f)
			.setCreativeTab(CreativeTabs.tabBlock).setBlockName("YoloOre");
	public static final Block swagYoloConverterBlock = new SwagYoloConverterBlock(
			502, 4, Material.rock).setHardness(2.0f)
			.setStepSound(Block.soundStoneFootstep).setLightValue(0.4f)
			.setCreativeTab(CreativeTabs.tabDecorations)
			.setBlockName("SwagYoloConverter");
	private static final Item swagEssenceItem = new SwagModIngotItem(5006)
			.setIconIndex(0).setItemName("SwagEssence")
			.setCreativeTab(CreativeTabs.tabMaterials);
	private static final Item yoloIngotItem = new SwagModIngotItem(5001)
			.setIconIndex(1).setItemName("YoloIngot")
			.setCreativeTab(CreativeTabs.tabMaterials);
	private static final Item yoloSwagIngotItem = new SwagModIngotItem(5004)
			.setIconIndex(4).setItemName("YoloSwagIngot")
			.setCreativeTab(CreativeTabs.tabMaterials);
	private static final Item swagDropItem = new SwagModIngotItem(5005)
			.setIconIndex(0).setItemName("SwagDrop")
			.setCreativeTab(CreativeTabs.tabMaterials);
	public static SwagModOreGenerator swagModOreGenerator = new SwagModOreGenerator();
	private static final Item swagSwordItem = new SwagToolItem(5002,
			swagToolMaterial).setIconIndex(2).setItemName("SwagSword")
			.setCreativeTab(CreativeTabs.tabCombat);
	private GuiHandler guiHandler = new GuiHandler();

	@Instance("SwagMod")
	public static SwagMod Instance;

	@SidedProxy(clientSide = "jack_1197.swag.client.ClientProxy", serverSide = "jack_1197.swag.common.CommonProxy")
	public static CommonProxy proxy;

	@PreInit
	public void preInit(FMLPreInitializationEvent event) {

	}

	@Init
	public void preInit(FMLInitializationEvent event) {
		proxy.registerRenderers();
		GameRegistry.addSmelting(swagOreBlock.blockID, new ItemStack(
				swagEssenceItem), 0.6f);
		GameRegistry.addSmelting(yoloOreBlock.blockID, new ItemStack(
				yoloIngotItem), 1.0f);
		GameRegistry.addRecipe(new ItemStack(swagDropItem), " E ", "EEE",
				"EEE", 'E', new ItemStack(swagEssenceItem));
		GameRegistry.addRecipe(new ItemStack(swagSwordItem), "EDE", "ETE",
				"IYI", 'E', new ItemStack(swagEssenceItem), 'D', new ItemStack(
						swagDropItem), 'T', new ItemStack(Item.swordDiamond),
				'I', new ItemStack(Item.diamond), 'Y', new ItemStack(
						yoloIngotItem));
		GameRegistry.registerBlock(swagYoloConverterBlock,
				"swagYoloConverter");
		GameRegistry.registerTileEntity(SwagYoloConverterTileEntity.class,
				"swagYoloConverter");
		GameRegistry.registerBlock(swagOreBlock, "swagOre");
		GameRegistry.registerBlock(yoloOreBlock, "yoloOre");
		GameRegistry.registerWorldGenerator(swagModOreGenerator);
		LanguageRegistry.addName(swagOreBlock, "Swagite Ore");
		LanguageRegistry.addName(swagYoloConverterBlock, "Yolo-o-Matic");
		LanguageRegistry.addName(yoloOreBlock, "Yolo Ore");
		LanguageRegistry.addName(swagDropItem, "Swag Drop");
		LanguageRegistry.addName(swagEssenceItem, "Swag Essence");
		LanguageRegistry.addName(yoloSwagIngotItem, "YoloSwag Alloy");
		LanguageRegistry.addName(yoloIngotItem, "Yolo Ingot");
		LanguageRegistry.addName(swagSwordItem, "Swag Sword");
		NetworkRegistry.instance().registerGuiHandler(this, guiHandler);
	}

	@PreInit
	public void postInit(FMLPostInitializationEvent event) {

	}

}
