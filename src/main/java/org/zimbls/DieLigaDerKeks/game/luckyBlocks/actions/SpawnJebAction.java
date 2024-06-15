package org.zimbls.DieLigaDerKeks.game.luckyBlocks.actions;

import org.bukkit.Location;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.ActionRarity;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.LuckyBlockAction;

import java.util.Objects;

public class SpawnJebAction extends LuckyBlockAction {
    @Override
    public void run() {
        Location location = super.playerLuckyBlockData.getBlockLocation();
        Objects.requireNonNull(location.getWorld()).spawnEntity(location, org.bukkit.entity.EntityType.SHEEP).setCustomName("jeb_");
    }

    @Override
    public ActionRarity getRarity() {
        return ActionRarity.COMMON;
    }

    @Override
    public String getDisplayName() {
        return "Spawn Jeb";
    }
}
