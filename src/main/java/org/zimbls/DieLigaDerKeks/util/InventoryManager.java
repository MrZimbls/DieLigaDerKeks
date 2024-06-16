package org.zimbls.DieLigaDerKeks.util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InventoryManager {
    private final Map<UUID, ItemStack[]> savedInventories = new HashMap<>();
    private final Map<UUID, ItemStack[]> savedArmorContents = new HashMap<>();

    public void saveInventory(Player player) {
        savedInventories.put(player.getUniqueId(), player.getInventory().getContents());
        savedArmorContents.put(player.getUniqueId(), player.getInventory().getArmorContents());
    }

    public void clearInventory(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
    }

    public void restoreInventory(Player player) {
        UUID playerId = player.getUniqueId();
        if (savedInventories.containsKey(playerId) && savedArmorContents.containsKey(playerId)) {
            player.getInventory().setContents(savedInventories.get(playerId));
            player.getInventory().setArmorContents(savedArmorContents.get(playerId));
            savedInventories.remove(playerId);
            savedArmorContents.remove(playerId);
        }
    }
}
