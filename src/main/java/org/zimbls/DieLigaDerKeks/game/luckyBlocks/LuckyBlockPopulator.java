package org.zimbls.DieLigaDerKeks.game.luckyBlocks;

import org.bukkit.Material;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.LimitedRegion;
import org.bukkit.generator.WorldInfo;

import java.util.Random;

public class LuckyBlockPopulator extends BlockPopulator {
    private static final double SPAWN_PROBABILITY = 0.05; // 5% chance to spawn a block

    @Override
    public void populate(WorldInfo worldInfo, Random random, int chunkX, int chunkZ, LimitedRegion limitedRegion) {
        // Only proceed if the random chance is met
        if (random.nextDouble() < SPAWN_PROBABILITY) {
            int x = chunkX * 16 + random.nextInt(16); // World x-coordinate
            int z = chunkZ * 16 + random.nextInt(16); // World z-coordinate

            // Get highest Y-coordinate using LimitedRegion
            int y = limitedRegion.getHighestBlockYAt(x, z);

            // Set block at the highest Y-coordinate to a random block type
            limitedRegion.setType(x, y, z, Material.SPONGE);
        }
    }
}
