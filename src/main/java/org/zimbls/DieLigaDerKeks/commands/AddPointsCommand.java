package org.zimbls.DieLigaDerKeks.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.zimbls.DieLigaDerKeks.game.Participant;
import org.zimbls.DieLigaDerKeks.stateMachine.GameState;
import org.zimbls.DieLigaDerKeks.stateMachine.GameStateMachine;

public class AddPointsCommand implements CommandExecutor {
    private GameStateMachine state;
    private JavaPlugin plugin;

    public AddPointsCommand(JavaPlugin plugin, GameStateMachine state) {
        this.plugin = plugin;
        this.state = state;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String playerName = args[0];
        Integer points = Integer.parseInt(args[1]);

        Participant participant = state.getGame().getParticipantByName(playerName);

        if (participant == null) {
            sender.sendMessage(ChatColor.RED + "Player not found");
            return false;
        }

        participant.addPoints(points);

        sender.sendMessage(ChatColor.GREEN + "Added " + points + " points to " + playerName);
        participant.getPlayer().sendMessage(ChatColor.GREEN + "You have received " + points + " points");

        return true;
    }
}
