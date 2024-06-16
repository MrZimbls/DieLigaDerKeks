package org.zimbls.DieLigaDerKeks.game.luckyBlocks.actions;

import org.bukkit.Location;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.ActionRarity;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.LuckyBlockAction;

public class EncasePlayerInGlassAction extends LuckyBlockAction {
    @Override
    public void run() {
        Location location = super.playerLuckyBlockData.getParticipant().getPlayer().getLocation();
        Location[] glassCageLocations = getGlassCageLocations(location);
        for (Location glassCageLocation : glassCageLocations) {
            glassCageLocation.getBlock().setType(org.bukkit.Material.GLASS);
        }
    }

    @Override
    public ActionRarity getRarity() {
        return ActionRarity.COMMON;
    }

    @Override
    public String getDisplayName() {
        return "Glass House";
    }

    private Location[] getGlassCageLocations(Location center) {
        Location base = center.clone().add(0, 1, 0); // Start one block above the player's head
        return new Location[] {
            base.clone().add(1, 0, 0),
            base.clone().add(-1, 0, 0),
            base.clone().add(0, 0, 1),
            base.clone().add(0, 0, -1),
            base.clone().add(1, 0, 1),
            base.clone().add(1, 0, -1),
            base.clone().add(-1, 0, 1),
            base.clone().add(-1, 0, -1),
            base.clone().add(1, 1, 0),
            base.clone().add(-1, 1, 0),
            base.clone().add(0, 1, 1),
            base.clone().add(0, 1, -1),
            base.clone().add(1, 1, 1),
            base.clone().add(1, 1, -1),
            base.clone().add(-1, 1, 1),
            base.clone().add(-1, 1, -1),
            base.clone().add(1, -1, 0),
            base.clone().add(-1, -1, 0),
            base.clone().add(0, -1, 1),
            base.clone().add(0, -1, -1),
            base.clone().add(1, -1, 1),
            base.clone().add(1, -1, -1),
            base.clone().add(-1, -1, 1),
            base.clone().add(-1, -1, -1),
            base.clone().add(1, 2, 0),
            base.clone().add(-1, 2, 0),
            base.clone().add(0, 2, 1),
            base.clone().add(0, 2, -1),
            base.clone().add(1, 2, 1),
            base.clone().add(1, 2, -1),
            base.clone().add(-1, 2, 1),
            base.clone().add(-1, 2, -1),
            base.clone().add(0, 3, 0) // Top block above the player's head
        };
    }
}
