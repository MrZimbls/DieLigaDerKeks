package org.zimbls.DieLigaDerKeks.game.luckyBlocks.actions;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.ActionRarity;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.LuckyBlockAction;

import java.util.Objects;

public class AddPointsAction extends LuckyBlockAction {
    @Override
    public void run() {
        super.playerLuckyBlockData.getParticipant().addPoints(1);
        Location blockLocation = super.playerLuckyBlockData.getBlockLocation();
        Objects.requireNonNull(blockLocation.getWorld()).playSound(blockLocation, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
    }

    @Override
    public ActionRarity getRarity() {
        return ActionRarity.COMMON;
    }

    @Override
    public String getDisplayName() {
        return "Add Points";
    }
}
