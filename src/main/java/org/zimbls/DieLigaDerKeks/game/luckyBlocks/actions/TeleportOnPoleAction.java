package org.zimbls.DieLigaDerKeks.game.luckyBlocks.actions;

import org.bukkit.Location;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.ActionRarity;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.LuckyBlockAction;

public class TeleportOnPoleAction extends LuckyBlockAction {
    @Override
    public void run() {
        Location location = super.playerLuckyBlockData.getBlockLocation();
        location.add(0, 10, 0);
        location.getBlock().setType(org.bukkit.Material.OAK_FENCE);
        location.add(0.5, 2, 0.5);
        super.playerLuckyBlockData.getParticipant().getPlayer().teleport(location);
    }

    @Override
    public ActionRarity getRarity() {
        return ActionRarity.COMMON;
    }

    @Override
    public String getDisplayName() {
        return "Pole in the air!";
    }
}
