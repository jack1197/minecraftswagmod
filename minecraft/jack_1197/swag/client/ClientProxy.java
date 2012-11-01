package jack_1197.swag.client;

import net.minecraftforge.client.MinecraftForgeClient;
import jack_1197.swag.common.CommonProxy;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerRenderers(){
		MinecraftForgeClient.preloadTexture(BLOCKS);
		MinecraftForgeClient.preloadTexture(ITEMS);
	}
}
