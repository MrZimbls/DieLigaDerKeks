package org.zimbls.DieLigaDerKeks.game.luckyBlocks;

import org.bukkit.Location;
import org.bukkit.Sound;

import java.util.Objects;

public abstract class LuckyBlockAction {
    protected PlayerLuckyBlockData playerLuckyBlockData;
    public void setPlayerLuckyBlockData(PlayerLuckyBlockData playerLuckyBlockData) {
        this.playerLuckyBlockData = playerLuckyBlockData;
    }
    public void playSound(Sound sound) {
        Location blockLocation = this.playerLuckyBlockData.getBlockLocation();
        Objects.requireNonNull(blockLocation.getWorld()).playSound(blockLocation, sound, 1.0f, 1.0f);
    }
    public abstract void run();
    public abstract ActionRarity getRarity();
    public abstract String getDisplayName();
}
