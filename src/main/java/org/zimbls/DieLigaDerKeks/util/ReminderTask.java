package org.zimbls.DieLigaDerKeks.util;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.zimbls.DieLigaDerKeks.game.Participant;

import java.util.Set;

public class ReminderTask extends BukkitRunnable {
    private JavaPlugin plugin;
    private String message;
    private Set<Participant> participants;

    public ReminderTask(JavaPlugin plugin, Set<Participant> participants, String message) {
        this.plugin = plugin;
        this.message = message;
        // Participants set when task is created
        this.participants = participants;
    }

    @Override
    public void run() {
        // FIXME: Participants don't update yet when a player enters /ready, so this message will be sent to all online players even if they have already entered /ready
        plugin.getServer().getOnlinePlayers()
            .stream()
            .filter(player -> !participants.contains(player))
            .forEach(player -> player.sendMessage(message));
    }
}
