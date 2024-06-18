package org.zimbls.DieLigaDerKeks.game.luckyBlocks.actions;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.generator.structure.StructureType;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.ActionRarity;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.LuckyBlockAction;

import java.util.Objects;

public class RevealStrongholdAction extends LuckyBlockAction {
    @Override
    public void run() {
        Location blockLocation = super.playerLuckyBlockData.getBlockLocation();
        Location stronghold = Objects.requireNonNull(Objects.requireNonNull(blockLocation.getWorld()).locateNearestStructure(blockLocation, StructureType.STRONGHOLD, 1000, false)).getLocation();

        String message = ChatColor.GOLD + "Stronghold found at: " + ChatColor.BLUE + stronghold.getBlockX() + ", " + stronghold.getBlockY() + ", " + stronghold.getBlockZ();
        super.playerLuckyBlockData.getParticipant().getPlayer().sendMessage(message);
    }

    @Override
    public ActionRarity getRarity() {
        return ActionRarity.RARE;
    }

    @Override
    public String getDisplayName() {
        return "Reveal Stronghold";
    }
}
