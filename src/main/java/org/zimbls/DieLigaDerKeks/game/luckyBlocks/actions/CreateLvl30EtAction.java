package org.zimbls.DieLigaDerKeks.game.luckyBlocks.actions;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.ActionRarity;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.LuckyBlockAction;

import java.util.Objects;

public class CreateLvl30EtAction extends LuckyBlockAction {
    @Override
    public void run() {
        Location center = super.playerLuckyBlockData.getBlockLocation();
        new BukkitRunnable() {
            int blocksSpawned = 0;
            final Location[] locations = getEnchantmentSetupLocations(center);

            @Override
            public void run() {
                if (blocksSpawned < locations.length) {
                    Location loc = locations[blocksSpawned];
                    Block block = loc.getBlock();
                    if (blocksSpawned == 0) {
                        block.setType(Material.ENCHANTING_TABLE);
                    } else {
                        block.setType(Material.BOOKSHELF);
                    }
                    Objects.requireNonNull(loc.getWorld()).playSound(loc, Sound.BLOCK_AMETHYST_BLOCK_PLACE, 1.0f, 1.0f);
                    blocksSpawned++;
                } else {
                    this.cancel();
                }
            }
        }.runTaskTimer(super.playerLuckyBlockData.getPlugin(), 0L, 2L);
    }

    @Override
    public ActionRarity getRarity() {
        return ActionRarity.LEGENDARY;
    }

    @Override
    public String getDisplayName() {
        return "Create Lvl 30 enchantment table";
    }

    private Location[] getEnchantmentSetupLocations(Location center) {
        return new Location[] {
            center,
            center.clone().add(2, 0, 0),
            center.clone().add(2, 0, 1),
            center.clone().add(2, 0, -1),
            center.clone().add(-2, 0, 0),
            center.clone().add(-2, 0, 1),
            center.clone().add(-2, 0, -1),
            center.clone().add(0, 0, 2),
            center.clone().add(1, 0, 2),
            center.clone().add(-1, 0, 2),
            center.clone().add(0, 0, -2),
            center.clone().add(1, 0, -2),
            center.clone().add(-1, 0, -2),
            center.clone().add(2, 0, 2),
            center.clone().add(2, 0, -2),
            center.clone().add(-2, 0, 2),
            center.clone().add(-2, 0, -2),
        };
    }
}
