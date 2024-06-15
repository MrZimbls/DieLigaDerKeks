package org.zimbls.DieLigaDerKeks.game.luckyBlocks.actions;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.ActionRarity;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.LuckyBlockAction;

import java.util.Objects;

public class SpawnWardenAction extends LuckyBlockAction {
    @Override
    public void run() {
        Location location = super.playerLuckyBlockData.getBlockLocation();
        Objects.requireNonNull(location.getWorld()).spawnEntity(location, EntityType.WARDEN);
    }

    @Override
    public ActionRarity getRarity() {
        return ActionRarity.LEGENDARY;
    }

    @Override
    public String getDisplayName() {
        return "Spawn Warden";
    }
}
