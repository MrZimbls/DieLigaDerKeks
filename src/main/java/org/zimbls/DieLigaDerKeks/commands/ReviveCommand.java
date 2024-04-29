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

import java.util.Arrays;

public class ReviveCommand implements CommandExecutor {
    private GameStateMachine state;
    private JavaPlugin plugin;

    public ReviveCommand(JavaPlugin plugin, GameStateMachine state) {
        this.plugin = plugin;
        this.state = state;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        System.out.println("Reviving dead participant");

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        if (state.getState() != GameState.RUNNING) {
            sender.sendMessage(ChatColor.RED + "Game is not running");
            return false;
        }

        // Check if the command has the correct number of arguments
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Invalid number of arguments");
            return false;
        }

        String playerName = args[0];
        Participant participantToRevive = state.getGame().getParticipantByName(playerName);

        if (participantToRevive == null) {
            sender.sendMessage(ChatColor.RED + "Player not found");
            return false;
        }

        System.out.println("Player to revive: " + participantToRevive.getPlayer().getName());

        if (!participantToRevive.isDead()) {
            sender.sendMessage(ChatColor.RED + "Player is not dead");
            return false;
        }

        // Final variable to be used in the lambda expression
        Participant finalPlayerToRevive = participantToRevive;
        finalPlayerToRevive.getPlayer().sendMessage(ChatColor.GREEN + "You have been revived! You are now invulnerable for 5 seconds.");

        // Make the player invulnerable for 5 seconds
        finalPlayerToRevive.getPlayer().setInvulnerable(true);
        finalPlayerToRevive.setDead(false);
        finalPlayerToRevive.getPlayer().setGameMode(GameMode.SURVIVAL);
        finalPlayerToRevive.getPlayer().teleport(finalPlayerToRevive.getLastGameLocation());

        // Remove invulnerability after 5 seconds
        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.runTaskLater(plugin, () -> {
            finalPlayerToRevive.getPlayer().setInvulnerable(false);
        }, 20L * 5);

        sender.sendMessage(ChatColor.GREEN + "Successfully revived player '" + playerName + "'.");

        return true;
    }
}
