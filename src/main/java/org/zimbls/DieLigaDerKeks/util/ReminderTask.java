package org.zimbls.DieLigaDerKeks.util;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.zimbls.DieLigaDerKeks.game.Game;
import org.zimbls.DieLigaDerKeks.game.Participant;

import java.util.Set;

public class ReminderTask extends BukkitRunnable {
    private JavaPlugin plugin;
    private String message;
    private Game game;

    public ReminderTask(JavaPlugin plugin, Game game, String message) {
        this.plugin = plugin;
        this.message = message;
        this.game = game;
    }

    @Override
    public void run() {
        plugin.getServer().getOnlinePlayers()
            .stream()
            .filter(player -> !game.getParticipatingPlayers().contains(player))
            .forEach(player -> player.sendMessage(message));
    }
}
