package jack_1197.swag.client;

import jack_1197.swag.common.CommonProxy;
import net.minecraftforge.client.MinecraftForgeClient;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerRenderers() {
		// preload textures
		MinecraftForgeClient.preloadTexture(BLOCKS);
		MinecraftForgeClient.preloadTexture(ITEMS);
		MinecraftForgeClient
				.preloadTexture("/jack_1197/swag/common/textures/SwagYoloConverter.png");
		MinecraftForgeClient
				.preloadTexture("/jack_1197/swag/common/textures/Fuser.png");
	}
}
