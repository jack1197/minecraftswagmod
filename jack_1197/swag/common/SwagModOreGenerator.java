package jack_1197.swag.common;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

public class SwagModOreGenerator implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world,
			IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		for (int i = 0; i < 4; i++) {
			int x = chunkX * 16 + random.nextInt(16);
			int y = random.nextInt(48);
			int z = chunkZ * 16 + random.nextInt(16);
			(new WorldGenMinable(SwagMod.swagOreBlock.blockID, 8)).generate(
					world, random, x, y, z);
		}
		for (int i = 0; i < 3; i++) {
			int x = chunkX * 16 + random.nextInt(16);
			int y = random.nextInt(32);
			int z = chunkZ * 16 + random.nextInt(16);
			(new WorldGenMinable(SwagMod.yoloOreBlock.blockID, 6)).generate(
					world, random, x, y, z);
		}
	}

}
