package org.zimbls.DieLigaDerKeks.game.luckyBlocks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.zimbls.DieLigaDerKeks.game.Participant;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.customArmor.*;
import org.zimbls.DieLigaDerKeks.listener.CustomArmorListener;
import org.zimbls.DieLigaDerKeks.stateMachine.GameStateMachine;

import java.util.HashMap;
import java.util.Set;

public class CustomArmorEffectsHandler {
    private GameStateMachine state;
    private CustomArmorListener listener;
    private HashMap<String, CustomArmor> allCustomArmor = new HashMap<>();

    public CustomArmorEffectsHandler(GameStateMachine state, Plugin plugin) {
        this.state = state;
        listener = new CustomArmorListener(state, this);
        listener.runTaskTimer(plugin, 0L, 20L);
        registerCustomArmor(new ExtraHeartChestplate());
        registerCustomArmor(new ExtraHeartHelmet());
        registerCustomArmor(new ExtraHeartLeggins());
        registerCustomArmor(new ExtraHeartBoots());
        registerCustomArmor(new SpeedBoots());
    }

    public void onPlayerEquipArmor(Participant player, String identifier) {
        if (allCustomArmor.containsKey(identifier)) {
            allCustomArmor.get(identifier).wearEffect(player);
        }
    }

    public void onPlayerUnequipArmor(Participant player, String identifier) {
        if (allCustomArmor.containsKey(identifier)) {
            allCustomArmor.get(identifier).onUnequipped(player);
        }
    }

    private void registerCustomArmor(CustomArmor customArmor) {
        allCustomArmor.put(customArmor.getIdentifier(), customArmor);
    }
}
