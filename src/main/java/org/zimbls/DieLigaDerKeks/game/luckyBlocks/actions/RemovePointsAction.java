package org.zimbls.DieLigaDerKeks.game.luckyBlocks.actions;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.ActionRarity;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.LuckyBlockAction;

import java.util.Objects;

public class RemovePointsAction extends LuckyBlockAction {
    @Override
    public void run() {
        super.playerLuckyBlockData.getParticipant().removePoints(1);
        Location blockLocation = super.playerLuckyBlockData.getBlockLocation();
        Objects.requireNonNull(blockLocation.getWorld()).playSound(blockLocation, Sound.BLOCK_ANVIL_PLACE, 1.0f, 1.0f);
    }

    @Override
    public ActionRarity getRarity() {
        return ActionRarity.UNCOMMON;
    }

    @Override
    public String getDisplayName() {
        return "Remove Points";
    }
}
