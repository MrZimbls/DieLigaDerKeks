package org.zimbls.DieLigaDerKeks.game.luckyBlocks.actions;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.ActionRarity;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.LuckyBlockAction;

import java.util.Objects;

public class CreateNetherPortalAction extends LuckyBlockAction {
    @Override
    public void run() {
        Location base = super.playerLuckyBlockData.getBlockLocation();
        new BukkitRunnable() {
            int blocksSpawned = 0;
            final Location[] obsidianLocations = getObsidianLocations(base);

            @Override
            public void run() {
                if (blocksSpawned < obsidianLocations.length) {
                    Location loc = obsidianLocations[blocksSpawned];
                    Block block = loc.getBlock();
                    block.setType(Material.OBSIDIAN);
                    Objects.requireNonNull(loc.getWorld()).playSound(loc, Sound.BLOCK_STONE_PLACE, 1.0f, 1.0f);
                    blocksSpawned++;
                } else if (blocksSpawned == obsidianLocations.length) {
                    setPortal(base);
                    this.cancel();
                }
            }
        }.runTaskTimer(super.playerLuckyBlockData.getPlugin(), 0L, 5L);
    }

    @Override
    public ActionRarity getRarity() {
        return ActionRarity.UNCOMMON;
    }

    @Override
    public String getDisplayName() {
        return "Create Nether Portal";
    }

    private Location[] getObsidianLocations(Location base) {
        return new Location[]{
            base.clone().add(0, 0, 0),
            base.clone().add(1, 0, 0),
            base.clone().add(-1, 0, 0),
            base.clone().add(-2, 0, 0),
            base.clone().add(-2, 1, 0),
            base.clone().add(1, 1, 0),
            base.clone().add(-2, 2, 0),
            base.clone().add(1, 2, 0),
            base.clone().add(-2, 3, 0),
            base.clone().add(1, 3, 0),
            base.clone().add(-2, 4, 0),
            base.clone().add(1, 4, 0),
            base.clone().add(0, 4, 0),
            base.clone().add(-1, 4, 0),
            base.clone().add(-2, 4, 0)
        };
    }

    private void setPortal(Location base) {
        Location[] portalLocations = new Location[]{
            base.clone().add(0, 1, 0),
            base.clone().add(-1, 1, 0),
            base.clone().add(0, 2, 0),
            base.clone().add(-1, 2, 0),
            base.clone().add(0, 3, 0),
            base.clone().add(-1, 3, 0)
        };

        for (Location loc : portalLocations) {
            Block block = loc.getBlock();
            block.setType(Material.NETHER_PORTAL);
        }
    }
}
