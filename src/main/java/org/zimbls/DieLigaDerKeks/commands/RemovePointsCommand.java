package org.zimbls.DieLigaDerKeks.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.zimbls.DieLigaDerKeks.game.Participant;
import org.zimbls.DieLigaDerKeks.stateMachine.GameStateMachine;

public class RemovePointsCommand implements CommandExecutor {
    private GameStateMachine state;
    private JavaPlugin plugin;

    public RemovePointsCommand(JavaPlugin plugin, GameStateMachine state) {
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

        participant.removePoints(points);

        sender.sendMessage(ChatColor.GREEN + "Removed " + points + " points from " + playerName);
        participant.getPlayer().sendMessage(ChatColor.GREEN + "You have lost " + points + " points");

        return true;
    }
}
