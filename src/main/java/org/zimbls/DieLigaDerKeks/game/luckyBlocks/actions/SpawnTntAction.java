package org.zimbls.DieLigaDerKeks.game.luckyBlocks.actions;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TNTPrimed;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.ActionRarity;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.LuckyBlockAction;

import java.util.Objects;

public class SpawnTntAction extends LuckyBlockAction {
    @Override
    public void run() {
        Location location = super.playerLuckyBlockData.getBlockLocation();
        TNTPrimed tnt = (TNTPrimed) Objects.requireNonNull(location.getWorld()).spawnEntity(location, EntityType.PRIMED_TNT);
        tnt.setFuseTicks(40);
    }

    @Override
    public ActionRarity getRarity() {
        return ActionRarity.UNCOMMON;
    }

    @Override
    public String getDisplayName() {
        return "Booom!";
    }
}
