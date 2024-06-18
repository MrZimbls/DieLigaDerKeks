package org.zimbls.DieLigaDerKeks.game.luckyBlocks.actions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.ActionRarity;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.LuckyBlockAction;

public class RevealLocationToAllAction extends LuckyBlockAction {
    @Override
    public void run() {
        Location location = super.playerLuckyBlockData.getBlockLocation();
        String playerName = super.playerLuckyBlockData.getParticipant().getPlayer().getName();
        String message = "The player " +
            ChatColor.GOLD +
            playerName +
            ChatColor.RESET +
            " is currently at " +
            ChatColor.BLUE +
            "X: " +
            location.getBlockX() +
            " Z: " +
            location.getBlockZ();
        Bukkit.broadcastMessage(message);
    }

    @Override
    public ActionRarity getRarity() {
        return ActionRarity.UNCOMMON;
    }

    @Override
    public String getDisplayName() {
        return "Reveal Location To All";
    }
}
