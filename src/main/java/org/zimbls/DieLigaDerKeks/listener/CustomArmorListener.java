package org.zimbls.DieLigaDerKeks.listener;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;
import org.zimbls.DieLigaDerKeks.game.Game;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.CustomArmorEffectsHandler;
import org.zimbls.DieLigaDerKeks.stateMachine.GameStateMachine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class CustomArmorListener extends BukkitRunnable {
    private final GameStateMachine state;
    private final CustomArmorEffectsHandler handler;
    public CustomArmorListener(GameStateMachine state, CustomArmorEffectsHandler handler) {
        this.state = state;
        this.handler = handler;
    }

    @Override
    public void run() {
        if (state.getGame() == null) return;
        if (state.getGame().getParticipants() == null) return;
        state.getGame().getParticipants().forEach(participant -> {
            ArrayList<String> equippedItems = new ArrayList<>(participant.getEquippedCustomItems());
            ArrayList<String> actualEquippedItems = new ArrayList<>();
            for (ItemStack item : participant.getPlayer().getInventory().getArmorContents()) {
                if (item != null && item.hasItemMeta()) {
                    PersistentDataContainer data = Objects.requireNonNull(item.getItemMeta()).getPersistentDataContainer();
                    if (data.has(Game.CUSTOM_ARMOR_KEY)) {
                        String equippedItem = data.get(Game.CUSTOM_ARMOR_KEY, PersistentDataType.STRING);
                        actualEquippedItems.add(equippedItem);
                        if (!equippedItems.contains(equippedItem)) {
                            Bukkit.getLogger().info("Player " + participant.getPlayer().getName() + " has equipped " + equippedItem);
                            handler.onPlayerEquipArmor(participant, equippedItem);
                            participant.addEquippedCustomItem(equippedItem);
                        }
                    }
                }
            }
            for (String item : equippedItems) {
                if (!actualEquippedItems.contains(item)) {
                    Bukkit.getLogger().info("Player " + participant.getPlayer().getName() + " has unequipped " + item);
                    handler.onPlayerUnequipArmor(participant, item);
                    participant.removeEquippedCustomItem(item);
                }
            }
        });
    }
}
