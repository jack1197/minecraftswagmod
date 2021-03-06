package jack_1197.swag.common;

import jack_1197.swag.client.ClientPacketHandler;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.EnumHelper;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
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

/*
 * Swag mod by jack_1197 as this is one of my first mods some parts may be little modified tutorial code snippets
 */
// annotations, you dont say?
@Mod(modid = "SwagMod", name = "Swag Mod", version = "0.0.6 Pre-Alpha")
@NetworkMod(clientSideRequired = true, serverSideRequired = false, clientPacketHandlerSpec = @SidedPacketHandler(channels = { "SwagMod" }, packetHandler = ClientPacketHandler.class),
		serverPacketHandlerSpec = @SidedPacketHandler(channels = { "SwagMod" }, packetHandler = ServerPacketHandler.class))
public class SwagMod {
	// main declarations and stuffs

	public static enum packetType {
		SWAG_YOLO_CONVERTER_UPDATE, FUSER_UPDATE, FUSER_BUTTON_CLICK
	}

	// Tool Materials
	static EnumToolMaterial swagToolMaterial;
	static EnumToolMaterial yoloToolMaterial;
	static EnumToolMaterial swagYoloToolMaterial;

	// Block declarations
	public static Block swagModOreBlock;

	public static Block swagYoloConverterBlock;
	public static Block swagYoloConverterFueledBlock;

	public static Block fuserBlock;

	public static Block swagBlock;
	public static Block yoloBlock;

	public static Item swagPotionItem;

	// Item Declarations
	public static Item swagEssenceItem;
	public static Item swagDropItem;
	public static Item swagOrbItem;
	public static Item swagIngotItem;

	public static Item yoloEssenceItem;
	public static Item yoloEssenceDenseItem;
	public static Item yoloDropItem;
	public static Item yoloDropDenseItem;
	public static Item yoloOrbItem;
	public static Item yoloIngotItem;

	public static Item yoloSwagIngotItem;

	// Tool declarations
	public static Item swagSwordItem;
	public static Item swagShovelItem;
	public static Item swagPickaxeItem;
	public static Item swagAxeItem;
	public static Item swagHoeItem;

	public static Item yoloSwordItem;
	public static Item yoloShovelItem;
	public static Item yoloPickaxeItem;
	public static Item yoloAxeItem;
	public static Item yoloHoeItem;// oh well, YOLO!!

	// technical declarations
	private GuiHandler guiHandler = new GuiHandler();
	private ClientPacketHandler clientPacketHandler = new ClientPacketHandler();
	private ServerPacketHandler serverPacketHandler = new ServerPacketHandler();
	public static SwagModOreGenerator swagModOreGenerator = new SwagModOreGenerator();
	private Configuration config;
	protected static String[] oreNames = {
			"LV1 Swag Ore",
			"LV2 Swag Ore",
			"LV3 Swag Ore",
			"LV4 Swag Ore",
			"LV5 Swag Ore",
			"LV6 Swag Ore",
			"LV7 Swag Ore",
			"LV8 Swag Ore",
			"LV1 YOLO Ore",
			"LV2 YOLO Ore",
			"LV3 YOLO Ore",
			"LV4 YOLO Ore",
			"LV5 YOLO Ore",
			"LV6 YOLO Ore",
			"LV7 YOLO Ore",
			"LV8 YOLO Ore", };

	protected static boolean swagYoloConvCanExplode;

	@Instance("SwagMod")
	public static SwagMod Instance;

	@SidedProxy(clientSide = "jack_1197.swag.client.ClientProxy", serverSide = "jack_1197.swag.common.CommonProxy")
	public static CommonProxy proxy;

	static public int getSwagValue(ItemStack item) {
		if (item.getItem() == SwagMod.swagEssenceItem) {
			return 600;
		} else if (item.getItem() == SwagMod.swagDropItem) {
			return 4200;
		} else if (item.getItem() == SwagMod.swagSwordItem) {
			return 24000;
		} else if (item.getItem() == SwagMod.yoloSwagIngotItem) {
			return 24000;
		} else if (item.getItem() == SwagMod.swagOrbItem) {
			return 18000;
		} else if (item.getItem() == Item.diamond) {
			return 3000;
		}
		return 0;
	}

	static public int getYoloValue(ItemStack item) {
		if (item.getItem() == Item.cookie) {
			return 16;
		}
		if (item.getItem() == Item.ingotGold) {
			return 128;
		}
		if (item.getItem() == Item.hoeDiamond) {
			return 1024;
		}
		if (item.getItem() == Item.swordWood) {
			return 16;
		}
		if (item.getItem() == Item.enderPearl) {
			return 128;
		}
		if (item.getItem() == Item.pickaxeGold) {
			return 256;
		}
		if (item.getItem() == new ItemStack(Block.tnt).getItem()) {
			return 128;
		}
		if (item.getItem() == new ItemStack(Block.woodenButton).getItem()) {
			return 16;
		}
		return 1;
	}

	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		// you will be able to find the config file in .minecraft/config/ and it will be named Dummy.cfg
		// here our Configuration has been instantiated, and saved under the name "config"
		config = new Configuration(event.getSuggestedConfigurationFile());
		config.load();
		swagYoloConvCanExplode = config.get(Configuration.CATEGORY_GENERAL, "yoloOMaticCanExplode", true).getBoolean(true);
	}

	@Init
	public void load(FMLInitializationEvent event) {
		// initialise stuff with config options

		swagToolMaterial = EnumHelper.addToolMaterial("swag", 3, 3000, 12.5F, 16, 15);
		yoloToolMaterial = EnumHelper.addToolMaterial("yolo", 3, 1000, 18.0F, 22, 30);

		swagYoloToolMaterial = EnumHelper.addToolMaterial("swagYolo", 3, 2300, 15.0F, 19, 30);

		// Block declarations
		swagModOreBlock = new SwagModOreBlock(config.getBlock("swagModOreBlock", 500).getInt(), 0, Material.rock).setHardness(2.0f).setStepSound(Block.soundStoneFootstep)
				.setCreativeTab(CreativeTabs.tabBlock).setBlockName("SwagModOre").setTextureFile(CommonProxy.BLOCKS);

		swagYoloConverterBlock = new SwagYoloConverterBlock(config.getBlock("yoloOMaticBlock", 510).getInt(), 5, Material.rock).setHardness(2.0f).setStepSound(Block.soundStoneFootstep)
				.setCreativeTab(CreativeTabs.tabDecorations).setBlockName("SwagYoloConverter");

		swagYoloConverterFueledBlock = new SwagYoloConverterBlock(config.getBlock("yoloOMaticLitBlock", 511).getInt(), 7, Material.rock).setHardness(2.0f)
				.setStepSound(Block.soundStoneFootstep).setCreativeTab(CreativeTabs.tabDecorations).setBlockName("SwagYoloConverterLit").setLightValue(0.9F);

		fuserBlock = new FuserBlock(config.getBlock("fuserBlock", 520).getInt(), 7, Material.rock).setHardness(2.0f).setStepSound(Block.soundStoneFootstep)
				.setCreativeTab(CreativeTabs.tabDecorations).setBlockName("Fuser").setLightValue(0.9F);

		yoloBlock = new Block(config.getBlock("yoloBlock", 530).getInt(), 8 * 16 + 1, Material.rock).setHardness(2.0f).setStepSound(Block.soundStoneFootstep).setLightValue(0.4f)
				.setCreativeTab(CreativeTabs.tabBlock).setBlockName("YoloBlock").setTextureFile(CommonProxy.BLOCKS);
		swagBlock = new Block(config.getBlock("swagBlock", 531).getInt(), 8 * 16, Material.rock).setHardness(2.0f).setStepSound(Block.soundStoneFootstep).setLightValue(0.4f)
				.setCreativeTab(CreativeTabs.tabBlock).setBlockName("SwagBlock").setTextureFile(CommonProxy.BLOCKS);

		swagPotionItem = ((CustomFood) new CustomFood(config.getItem("swagPotionItem", 5200).getInt(), 0, true).setMaxStackSize(1).setIconCoord(3, 0).setItemName("SwagPotion")
				.setCreativeTab(CreativeTabs.tabBrewing)

				.setTextureFile(CommonProxy.ITEMS)).setHasNetherFX(true);
		((ItemFood) swagPotionItem).setAlwaysEdible();
		// Item Declarations
		swagEssenceItem = new Item(config.getItem("swagEssenceItem", 5000).getInt()).setMaxStackSize(32).setIconCoord(0, 0).setItemName("SwagEssence")
				.setCreativeTab(CreativeTabs.tabMaterials).setTextureFile(CommonProxy.ITEMS);
		swagDropItem = new Item(config.getItem("swagDropItem", 5001).getInt()).setMaxStackSize(32).setIconCoord(0, 1).setItemName("SwagDrop").setCreativeTab(CreativeTabs.tabMaterials)
				.setTextureFile(CommonProxy.ITEMS);

		swagOrbItem = new Item(config.getItem("swagOrbItem", 5002).getInt()).setMaxStackSize(16).setIconCoord(0, 2).setItemName("SwagOrb").setCreativeTab(CreativeTabs.tabMaterials)
				.setTextureFile(CommonProxy.ITEMS);
		yoloEssenceItem = new Item(config.getItem("yoloEssenceItem", 5010).getInt()).setMaxStackSize(32).setIconCoord(1, 0).setItemName("YoloEssence")
				.setCreativeTab(CreativeTabs.tabMaterials).setTextureFile(CommonProxy.ITEMS);
		yoloEssenceDenseItem = new Item(config.getItem("yoloEssenceDenseItem", 5011).getInt()).setMaxStackSize(16).setIconCoord(1, 3).setItemName("YoloEssenceDense")
				.setCreativeTab(CreativeTabs.tabMaterials).setTextureFile(CommonProxy.ITEMS);
		yoloDropItem = new Item(config.getItem("yoloDropItem", 5012).getInt()).setMaxStackSize(32).setIconCoord(1, 1).setItemName("YoloDrop").setCreativeTab(CreativeTabs.tabMaterials)
				.setTextureFile(CommonProxy.ITEMS);
		yoloDropDenseItem = new Item(config.getItem("yoloDropDenseItem", 5013).getInt()).setMaxStackSize(16).setIconCoord(1, 4).setItemName("YoloDropDense")
				.setCreativeTab(CreativeTabs.tabMaterials).setTextureFile(CommonProxy.ITEMS);
		yoloOrbItem = new Item(config.getItem("yoloOrbItem", 5014).getInt()).setMaxStackSize(32).setIconCoord(1, 2).setItemName("YoloOrb").setCreativeTab(CreativeTabs.tabMaterials)
				.setTextureFile(CommonProxy.ITEMS);

		yoloIngotItem = new Item(config.getItem("yoloIngotItem", 5020).getInt()).setIconCoord(1, 10).setItemName("YoloIngot").setCreativeTab(CreativeTabs.tabMaterials)
				.setTextureFile(CommonProxy.ITEMS);
		swagIngotItem = new Item(config.getItem("swagIngotItem", 5021).getInt()).setIconCoord(0, 10).setItemName("SwagIngot").setCreativeTab(CreativeTabs.tabMaterials)
				.setTextureFile(CommonProxy.ITEMS);

		yoloSwagIngotItem = new Item(config.getItem("yoloSwagIngotItem", 5022).getInt()).setIconCoord(2, 0).setItemName("YoloSwagIngot").setCreativeTab(CreativeTabs.tabMaterials)
				.setTextureFile(CommonProxy.ITEMS);

		// Tool declarations
		swagSwordItem = new ItemSword(config.getItem("swagSwordItem", 5100).getInt(), swagToolMaterial).setIconCoord(0, 5).setItemName("SwagSword").setCreativeTab(CreativeTabs.tabCombat)
				.setTextureFile(CommonProxy.ITEMS);
		swagShovelItem = new ItemSpade(config.getItem("swagShovelItem", 5101).getInt(), swagToolMaterial).setIconCoord(0, 6).setItemName("SwagSpade").setCreativeTab(CreativeTabs.tabTools)
				.setTextureFile(CommonProxy.ITEMS);
		swagPickaxeItem = new ItemPickaxe(config.getItem("swagPickaxeItem", 5102).getInt(), swagToolMaterial).setIconCoord(0, 7).setItemName("SwagPickaxe")
				.setCreativeTab(CreativeTabs.tabTools).setTextureFile(CommonProxy.ITEMS);
		swagAxeItem = new ItemAxe(config.getItem("swagAxeItem", 5103).getInt(), swagToolMaterial).setIconCoord(0, 8).setItemName("SwagAxe").setCreativeTab(CreativeTabs.tabTools)
				.setTextureFile(CommonProxy.ITEMS);
		swagHoeItem = new ItemHoe(config.getItem("swagHoeItem", 5104).getInt(), swagToolMaterial).setIconCoord(0, 9).setItemName("SwagHoe").setCreativeTab(CreativeTabs.tabTools)
				.setTextureFile(CommonProxy.ITEMS);// I have absolutely no
		// idea
		// why either...

		yoloSwordItem = new ItemSword(config.getItem("yoloSwordItem", 5110).getInt(), yoloToolMaterial).setIconCoord(1, 5).setItemName("YoloSword").setCreativeTab(CreativeTabs.tabCombat)
				.setTextureFile(CommonProxy.ITEMS);
		yoloShovelItem = new ItemSpade(config.getItem("yoloShovelItem", 5111).getInt(), yoloToolMaterial).setIconCoord(1, 6).setItemName("YoloSpade").setCreativeTab(CreativeTabs.tabTools)
				.setTextureFile(CommonProxy.ITEMS);
		yoloPickaxeItem = new ItemPickaxe(config.getItem("yoloPickaxeItem", 5112).getInt(), yoloToolMaterial).setIconCoord(1, 7).setItemName("YoloPickaxe")
				.setCreativeTab(CreativeTabs.tabTools).setTextureFile(CommonProxy.ITEMS);
		yoloAxeItem = new ItemAxe(config.getItem("yoloAxeItem", 5113).getInt(), yoloToolMaterial).setIconCoord(1, 8).setItemName("YoloAxe").setCreativeTab(CreativeTabs.tabTools)
				.setTextureFile(CommonProxy.ITEMS);
		yoloHoeItem = new ItemHoe(config.getItem("yoloHoeItem", 5114).getInt(), yoloToolMaterial).setIconCoord(1, 9).setItemName("YoloHoe").setCreativeTab(CreativeTabs.tabTools)
				.setTextureFile(CommonProxy.ITEMS);// oh well, YOLO!!

		// smelting TODO fix
		GameRegistry.addSmelting(swagModOreBlock.blockID, new ItemStack(swagEssenceItem), 0.6f);

		// recipies
		// swag crafting
		GameRegistry.addRecipe(new ItemStack(swagDropItem), " E ", "EEE", "EEE", 'E', new ItemStack(swagEssenceItem));
		GameRegistry.addRecipe(new ItemStack(swagOrbItem), "GDG", "DID", "GDG", 'G', new ItemStack(Block.glass), 'D', new ItemStack(swagDropItem), 'I', new ItemStack(Item.diamond));

		// yolo crafting
		GameRegistry.addShapelessRecipe(new ItemStack(yoloEssenceDenseItem), new ItemStack(yoloEssenceItem), new ItemStack(yoloEssenceItem), new ItemStack(yoloEssenceItem), new ItemStack(
				yoloEssenceItem));
		GameRegistry.addShapelessRecipe(new ItemStack(yoloDropDenseItem), new ItemStack(yoloDropItem), new ItemStack(yoloDropItem), new ItemStack(yoloDropItem), new ItemStack(yoloDropItem));
		GameRegistry.addShapelessRecipe(new ItemStack(yoloDropItem), new ItemStack(yoloEssenceDenseItem), new ItemStack(yoloEssenceDenseItem), new ItemStack(yoloEssenceDenseItem),
				new ItemStack(yoloEssenceDenseItem), new ItemStack(yoloEssenceDenseItem), new ItemStack(yoloEssenceDenseItem), new ItemStack(yoloEssenceDenseItem), new ItemStack(
						yoloEssenceDenseItem));
		GameRegistry.addShapelessRecipe(new ItemStack(yoloOrbItem), new ItemStack(yoloDropDenseItem), new ItemStack(yoloDropDenseItem), new ItemStack(yoloDropDenseItem), new ItemStack(
				yoloDropDenseItem), new ItemStack(yoloDropDenseItem), new ItemStack(yoloDropDenseItem), new ItemStack(yoloDropDenseItem), new ItemStack(yoloDropDenseItem));
		GameRegistry.addShapelessRecipe(new ItemStack(yoloDropDenseItem, 8), new ItemStack(yoloOrbItem));
		GameRegistry.addShapelessRecipe(new ItemStack(yoloDropItem, 4), new ItemStack(yoloDropDenseItem));
		GameRegistry.addShapelessRecipe(new ItemStack(yoloEssenceDenseItem, 8), new ItemStack(yoloDropItem));
		GameRegistry.addShapelessRecipe(new ItemStack(yoloEssenceItem, 4), new ItemStack(yoloEssenceDenseItem));

		// block crafting

		GameRegistry.addRecipe(new ItemStack(swagBlock), "III", "III", "III", 'I', new ItemStack(swagIngotItem));
		GameRegistry.addRecipe(new ItemStack(yoloBlock), "III", "III", "III", 'I', new ItemStack(yoloIngotItem));
		GameRegistry.addShapelessRecipe(new ItemStack(swagIngotItem, 9), new ItemStack(swagBlock));
		GameRegistry.addShapelessRecipe(new ItemStack(yoloIngotItem, 9), new ItemStack(yoloBlock));
		// brewing

		GameRegistry.addShapelessRecipe(new ItemStack(swagPotionItem, 4), new ItemStack(Item.glassBottle), new ItemStack(swagDropItem), new ItemStack(swagDropItem));

		// depreceated crafting recipies
		if (config.get(Configuration.CATEGORY_GENERAL, "allowToolCrafting", false).getBoolean(false)) {
			// swag tools
			GameRegistry.addRecipe(new ItemStack(swagSwordItem), "EDE", "ETE", "IOI", 'E', new ItemStack(swagEssenceItem), 'D', new ItemStack(swagDropItem), 'T', new ItemStack(
					Item.swordDiamond), 'I', new ItemStack(Item.diamond), 'O', new ItemStack(swagOrbItem));
			GameRegistry.addRecipe(new ItemStack(swagShovelItem), "EDE", "ETE", "IOI", 'E', new ItemStack(swagEssenceItem), 'D', new ItemStack(swagDropItem), 'T', new ItemStack(
					Item.shovelDiamond), 'I', new ItemStack(Item.diamond), 'O', new ItemStack(swagOrbItem));
			GameRegistry.addRecipe(new ItemStack(swagPickaxeItem), "EDE", "ETE", "IOI", 'E', new ItemStack(swagEssenceItem), 'D', new ItemStack(swagDropItem), 'T', new ItemStack(
					Item.pickaxeDiamond), 'I', new ItemStack(Item.diamond), 'O', new ItemStack(swagOrbItem));
			GameRegistry.addRecipe(new ItemStack(swagAxeItem), "EDE", "ETE", "IOI", 'E', new ItemStack(swagEssenceItem), 'D', new ItemStack(swagDropItem), 'T',
					new ItemStack(Item.axeDiamond), 'I', new ItemStack(Item.diamond), 'O', new ItemStack(swagOrbItem));
			GameRegistry.addRecipe(new ItemStack(swagHoeItem), "EDE", "ETE", "IOI", 'E', new ItemStack(swagEssenceItem), 'D', new ItemStack(swagDropItem), 'T',
					new ItemStack(Item.hoeDiamond), 'I', new ItemStack(Item.diamond), 'O', new ItemStack(swagOrbItem));

			// YOLO tools
			GameRegistry.addRecipe(new ItemStack(yoloSwordItem), "EDE", "ETE", "IOI", 'E', new ItemStack(yoloEssenceItem), 'D', new ItemStack(yoloDropItem), 'T', new ItemStack(
					Item.swordDiamond), 'I', new ItemStack(Item.diamond), 'O', new ItemStack(yoloOrbItem));
			GameRegistry.addRecipe(new ItemStack(yoloShovelItem), "EDE", "ETE", "IOI", 'E', new ItemStack(yoloEssenceItem), 'D', new ItemStack(yoloDropItem), 'T', new ItemStack(
					Item.shovelDiamond), 'I', new ItemStack(Item.diamond), 'O', new ItemStack(yoloOrbItem));
			GameRegistry.addRecipe(new ItemStack(yoloPickaxeItem), "EDE", "ETE", "IOI", 'E', new ItemStack(yoloEssenceItem), 'D', new ItemStack(yoloDropItem), 'T', new ItemStack(
					Item.pickaxeDiamond), 'I', new ItemStack(Item.diamond), 'O', new ItemStack(yoloOrbItem));
			GameRegistry.addRecipe(new ItemStack(yoloAxeItem), "EDE", "ETE", "IOI", 'E', new ItemStack(yoloEssenceItem), 'D', new ItemStack(yoloDropItem), 'T',
					new ItemStack(Item.axeDiamond), 'I', new ItemStack(Item.diamond), 'O', new ItemStack(yoloOrbItem));
			GameRegistry.addRecipe(new ItemStack(yoloHoeItem), "EDE", "ETE", "IOI", 'E', new ItemStack(yoloEssenceItem), 'D', new ItemStack(yoloDropItem), 'T',
					new ItemStack(Item.hoeDiamond), 'I', new ItemStack(Item.diamond), 'O', new ItemStack(yoloOrbItem));
		}
		// registration
		GameRegistry.registerBlock(swagModOreBlock, SwagModOreItemBlock.class, "SwagModOre");
		GameRegistry.registerBlock(swagBlock, "swag");
		GameRegistry.registerBlock(yoloBlock, "yolo");
		GameRegistry.registerBlock(swagYoloConverterBlock, "swagYoloConverter");
		GameRegistry.registerBlock(fuserBlock, "fuser");

		GameRegistry.registerTileEntity(SwagYoloConverterTileEntity.class, "swagYoloConverter");
		GameRegistry.registerTileEntity(FuserTileEntity.class, "fuser");

		GameRegistry.registerWorldGenerator(swagModOreGenerator);

		// launguage registry

		LanguageRegistry.addName(swagYoloConverterBlock, "YOLO-o-Matic");
		LanguageRegistry.addName(fuserBlock, "Fuser");

		LanguageRegistry.addName(swagBlock, "Swag Infused Block");
		LanguageRegistry.addName(yoloBlock, "YOLO Infused Block");

		LanguageRegistry.addName(swagEssenceItem, "Swag Essence");
		LanguageRegistry.addName(swagDropItem, "Swag Drop");
		LanguageRegistry.addName(swagOrbItem, "Swag Orb");

		LanguageRegistry.addName(yoloEssenceItem, "Teir 1 YOLO");
		LanguageRegistry.addName(yoloEssenceDenseItem, "Teir 1 YOLO X4");
		LanguageRegistry.addName(yoloDropItem, "Teir 2 YOLO");
		LanguageRegistry.addName(yoloDropDenseItem, "Teir 2 YOLO X4");
		LanguageRegistry.addName(yoloOrbItem, "Teir 3 YOLO");

		LanguageRegistry.addName(swagIngotItem, "Swag Infused Ingot");
		LanguageRegistry.addName(yoloIngotItem, "YOLO Infused Ingot");
		LanguageRegistry.addName(yoloSwagIngotItem, "Swag-YOLO Alloy");

		LanguageRegistry.addName(swagSwordItem, "Swag Sword");
		LanguageRegistry.addName(swagShovelItem, "Swag Spade");
		LanguageRegistry.addName(swagPickaxeItem, "Swag Pickaxe");
		LanguageRegistry.addName(swagAxeItem, "Swag Axe");
		LanguageRegistry.addName(swagHoeItem, "Swag Hoe");
		LanguageRegistry.addName(yoloSwordItem, "YOLO Sword");
		LanguageRegistry.addName(yoloShovelItem, "YOLO Spade");
		LanguageRegistry.addName(yoloPickaxeItem, "YOLO Pickaxe");
		LanguageRegistry.addName(yoloAxeItem, "YOLO Axe");
		LanguageRegistry.addName(yoloHoeItem, "YOLO Hoe");

		LanguageRegistry.addName(swagPotionItem, "Swag Potion");
		
		//looping stuff

		for (int i = 0; i < 16;  i++)
		{
			LanguageRegistry.addName(new ItemStack(swagModOreBlock, 1, i), oreNames[i]);
		}

		// misc
		proxy.registerRenderers();
		NetworkRegistry.instance().registerGuiHandler(this, guiHandler);
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent event) {
		config.save();
	}
}
