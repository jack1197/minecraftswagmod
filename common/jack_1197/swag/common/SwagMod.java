package jack_1197.swag.common;

import java.util.Random;

import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
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
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = "jack_1197_SwagMod", name = "Swag Mod", version = "0.0.3 Pre-Alpha")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class SwagMod {

	static EnumToolMaterial swagToolMaterial = EnumHelper.addToolMaterial("swag", 2, 1000, 10F, 8, 20);
	public static final Block swagOreBlock = new SwagModOreBlock(500, 0, Material.rock)
	.setHardness(2.0f).setStepSound(Block.soundStoneFootstep).setLightValue(0.3f).setCreativeTab(CreativeTabs.tabBlock).setBlockName("SwagOre");
	public static final Block yoloOreBlock = new SwagModOreBlock(501, 1, Material.rock)
	.setHardness(2.0f).setStepSound(Block.soundStoneFootstep).setLightValue(0.4f).setCreativeTab(CreativeTabs.tabBlock).setBlockName("YoloOre");
	private static final Item swagIngotItem = new ItemSwagModIngot(5000).setIconIndex(0).setItemName("SwagIngot").setCreativeTab(CreativeTabs.tabMaterials);
	private static final Item swagToolIngotItem = new ItemSwagModIngot(5005).setIconIndex(0).setItemName("SwagMidIngot").setCreativeTab(CreativeTabs.tabMaterials);
	private static final Item swagEssenceItem = new ItemSwagModIngot(5006).setIconIndex(0).setItemName("SwagEssence").setCreativeTab(CreativeTabs.tabMaterials);
	private static final Item yoloIngotItem = new ItemSwagModIngot(5001).setIconIndex(1).setItemName("YoloIngot").setCreativeTab(CreativeTabs.tabMaterials);
	private static final Item yoloSwagIngotItem = new ItemSwagModIngot(5004).setIconIndex(4).setItemName("YoloSwagIngot").setCreativeTab(CreativeTabs.tabMaterials);
	private static final Item swagDropItem = new ItemSwagModIngot(5000).setIconIndex(0).setItemName("SwagDrop").setCreativeTab(CreativeTabs.tabMaterials);
	public static SwagModOreGenerator swagModOreGenerator = new SwagModOreGenerator();
	private static final Item swagSwordItem = new SwagSwordItem(5002, swagToolMaterial).setIconIndex(2).setItemName("SwagSword").setCreativeTab(CreativeTabs.tabCombat);
	private static final Item swagWeaponIngotItem = new ItemSwagModIngot(5003).setIconIndex(3).setItemName("SwagWeaponIngot").setCreativeTab(CreativeTabs.tabMaterials);
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
		GameRegistry.addSmelting(swagOreBlock.blockID, new ItemStack(swagEssenceItem), 0.6f);
		GameRegistry.addSmelting(yoloOreBlock.blockID, new ItemStack(yoloIngotItem), 1.0f);
		GameRegistry.addRecipe(new ItemStack(swagDropItem), " E ", "EEE", "EEE", 'E', new ItemStack(swagEssenceItem));
		GameRegistry.registerBlock(swagOreBlock);
		GameRegistry.registerBlock(yoloOreBlock);
		GameRegistry.registerWorldGenerator(swagModOreGenerator);
		LanguageRegistry.addName(swagOreBlock, "Swagite Ore");
		LanguageRegistry.addName(yoloOreBlock, "Yolo Ore");
		LanguageRegistry.addName(swagIngotItem, "Swag");
		LanguageRegistry.addName(swagDropItem, "Swag Drop");
		LanguageRegistry.addName(swagEssenceItem, "Swag Essence");
		LanguageRegistry.addName(yoloSwagIngotItem, "YoloSwag Alloy");
		LanguageRegistry.addName(swagWeaponIngotItem, "Weapons Grade Swag");
		LanguageRegistry.addName(yoloIngotItem, "Yolo Ingot");
		LanguageRegistry.addName(swagSwordItem, "Swag Sword");
	}

	@PreInit
	public void postInit(FMLPostInitializationEvent event) {

	}

}
