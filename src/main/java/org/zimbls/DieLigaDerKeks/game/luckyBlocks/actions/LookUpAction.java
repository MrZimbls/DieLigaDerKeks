package org.zimbls.DieLigaDerKeks.game.luckyBlocks.actions;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.util.Vector;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.ActionRarity;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.LuckyBlockAction;

import java.util.Objects;

public class LookUpAction extends LuckyBlockAction {

    @Override
    public void run() {
        Location luckyBlockLocation = super.playerLuckyBlockData.getBlockLocation();
        Location playerLocation = super.playerLuckyBlockData.getBlockBreaker().getLocation();
        Player player = super.playerLuckyBlockData.getBlockBreaker();

        Location displayLocation = luckyBlockLocation.clone().add(0, 1, 0);

        // Spawn the TextDisplay entity
        TextDisplay textDisplay = player.getWorld().spawn(displayLocation, TextDisplay.class);
        textDisplay.setText("LOOK UP!");
        this.removeText(textDisplay);

        // Set the direction of the TextDisplay to face the player
        Vector directionToPlayer = playerLocation.toVector().subtract(displayLocation.toVector()).normalize();
        float yaw = (float) Math.toDegrees(Math.atan2(directionToPlayer.getZ(), directionToPlayer.getX())) - 90;
        textDisplay.setRotation(yaw, 0);

        // Drop a 3x3 grid of anvils with the player's location in the middle
        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                playerLocation.clone().add(dx, 50, dz).getBlock().setType(Material.ANVIL);
            }
        }
    }

    @Override
    public ActionRarity getRarity() {
        return ActionRarity.RARE;
    }

    @Override
    public String getDisplayName() {
        return "Look up!";
    }

    private void removeText(TextDisplay textDisplay) {
        int initialDelay = 60; // Initial delay before flickering starts (3 seconds)
        int flickerDuration = 20; // Total flicker duration in ticks (1 second)
        int flickerInterval = 4; // Interval between flickers in ticks (0.2 seconds)

        // Delay the start of the flicker effect
        Bukkit.getServer().getScheduler().runTaskLater(super.playerLuckyBlockData.getPlugin(), () -> {
            for (int i = 0; i < flickerDuration / flickerInterval; i++) {
                int delay = i * flickerInterval;
                Bukkit.getServer().getScheduler().runTaskLater(super.playerLuckyBlockData.getPlugin(), () -> {
                    boolean isCurrentlyVisible = !Objects.requireNonNull(textDisplay.getText()).isEmpty();
                    textDisplay.setText(isCurrentlyVisible ? "" : "LOOK UP!");
                }, delay);
            }

            // Finally remove the text after flicker effect
            Bukkit.getServer().getScheduler().runTaskLater(super.playerLuckyBlockData.getPlugin(), () -> {
                textDisplay.setText("");
            }, flickerDuration);
        }, initialDelay);
    }
}
