package jack_1197.swag.common;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import cpw.mods.fml.common.IWorldGenerator;

public class SwagModOreGenerator implements IWorldGenerator {
	// generates apropriate ores
	private final int[] oreRarity = {
			4,
			3,
			2,
			2,
			1,
			1,
			1,
			1,
			3,
			2,
			2,
			1,
			1,
			1,
			1,
			1 };

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < oreRarity[i]; j++) {
				int x = chunkX * 16 + random.nextInt(16);
				int y = random.nextInt(64);
				int z = chunkZ * 16 + random.nextInt(16);
				(new WorldGenMinable(SwagMod.swagModOreBlock.blockID, i, 6)).generate(world, random, x, y, z);
			}
		}
	}

}
