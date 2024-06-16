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
        String message = new StringBuilder()
                .append("The player ")
                .append(ChatColor.GOLD)
                .append(playerName)
                .append(ChatColor.RESET)
                .append(" is currently at ")
                .append(ChatColor.BLUE)
                .append("X: ")
                .append(location.getBlockX())
                .append(" Z: ")
                .append(location.getBlockZ())
                .toString();
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
