package jack_1197.swag.common;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Material;
import net.minecraft.src.Block;
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

@Mod(modid = "jack_1197_SwagMod", name = "Swag Mod", version = "0.0.0 Pre-Alpha")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class SwagMod {
	
	public static final Block swagOreBlock = new SwagOreBlock(500, 0, Material.rock)
	.setHardness(2.0f).setStepSound(Block.soundStoneFootstep).setLightValue(0.3f).setCreativeTab(CreativeTabs.tabBlock).setBlockName("SwagOre");
	public static final Block yoloOreBlock = new YoloOreBlock(501, 1, Material.rock)
	.setHardness(2.0f).setStepSound(Block.soundStoneFootstep).setLightValue(0.4f).setCreativeTab(CreativeTabs.tabBlock).setBlockName("YoloOre");
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
		GameRegistry.registerBlock(swagOreBlock);
		GameRegistry.registerBlock(yoloOreBlock);
		LanguageRegistry.addName(swagOreBlock, "Swag Ore");
		LanguageRegistry.addName(yoloOreBlock, "Yolo Ore");
	}

	@PreInit
	public void postInit(FMLPostInitializationEvent event) {

	}

}
