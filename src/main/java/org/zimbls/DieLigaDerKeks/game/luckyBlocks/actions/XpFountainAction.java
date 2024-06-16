package org.zimbls.DieLigaDerKeks.game.luckyBlocks.actions;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.scheduler.BukkitRunnable;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.ActionRarity;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.LuckyBlockAction;

import java.util.Random;

public class XpFountainAction extends LuckyBlockAction {

    private final Random random = new Random();

    @Override
    public void run() {
        Location location = super.playerLuckyBlockData.getBlockLocation();
        World world = location.getWorld();

        if (world == null) return;

        new BukkitRunnable() {
            int count = 0;
            final Random random = new Random();

            @Override
            public void run() {
                if (count >= 60) {
                    this.cancel();
                    return;
                }

                ThrownExpBottle xpBottle = world.spawn(location, ThrownExpBottle.class);
                double variance = 0.1;
                double xVelocity = variance * (random.nextDouble() - 0.5);
                double zVelocity = variance * (random.nextDouble() - 0.5);
                xpBottle.setVelocity(location.getDirection().setY(1).setX(xVelocity).setZ(zVelocity));

                count++;
            }
        }.runTaskTimer(super.playerLuckyBlockData.getPlugin(), 0L, 2L);
    }

    @Override
    public ActionRarity getRarity() {
        return ActionRarity.UNCOMMON;
    }

    @Override
    public String getDisplayName() {
        return "XP Fountain";
    }
}
