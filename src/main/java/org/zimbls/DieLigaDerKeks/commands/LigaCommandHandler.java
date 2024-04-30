package org.zimbls.DieLigaDerKeks.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.zimbls.DieLigaDerKeks.game.Participant;
import org.zimbls.DieLigaDerKeks.menue.PlayerGuiData;
import org.zimbls.DieLigaDerKeks.menue.gui.UnCollectedList;
import org.zimbls.DieLigaDerKeks.menue.gui.voteYesOrNo;
import org.zimbls.DieLigaDerKeks.stateMachine.GameState;
import org.zimbls.DieLigaDerKeks.stateMachine.GameStateMachine;
import org.zimbls.DieLigaDerKeks.util.Countdown;
import org.zimbls.DieLigaDerKeks.util.CountdownAction;
import org.zimbls.DieLigaDerKeks.util.LanguagePreferencesBasedProperties;


public class LigaCommandHandler implements CommandExecutor {
    private GameStateMachine state;
    private JavaPlugin plugin;

    public LigaCommandHandler(JavaPlugin plugin, GameStateMachine state) {
        this.plugin = plugin;
        this.state = state;
        this.plugin.getCommand("liga").setExecutor(this);
        this.plugin.getCommand("ready").setExecutor(this);
        this.plugin.getCommand("points").setExecutor(this);
        this.plugin.getCommand("vote").setExecutor(this);
        this.plugin.getCommand("language").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("liga")) {
            if (args.length == 0) {
                // TODO: Add help message
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("start")) {
                    if (!(sender instanceof Player)) {
                        sender.sendMessage("Only players can use this command!");
                        return true;
                    }
                    if (state.getState() == GameState.STOPPED) {
                        Player player = (Player) sender;
                        state.startGame(player.getWorld(), plugin);
                        return true;
                    } else if (state.getState() == GameState.STARTING) {
                        Countdown countdown = new Countdown(plugin, 10, new CountdownAction() {
                            @Override
                            public void onCountdownComplete() {
                                state.runGame(GameState.STARTING);
                            }
                        });
                        countdown.start();
                        return true;
                    }
                }
                if (args[0].equalsIgnoreCase("restart")) {
                    if (!(sender instanceof Player player)) {
                        sender.sendMessage("Only players can use this command!");
                        return true;
                    }
                    state.restartGame(plugin);
                    return true;
                }
                if (args[0].equalsIgnoreCase("pause")) {
                    if (state.getState() == GameState.RUNNING) {
                        Bukkit.broadcastMessage("Game is pausing!");
                        state.pauseGame();
                        return true;
                    }
                }
                if (args[0].equalsIgnoreCase("continue")) {
                    if (state.getState() == GameState.PAUSED) {
                        Bukkit.broadcastMessage("Game is continuing!");
                        state.runGame(GameState.PAUSED);
                        return true;
                    }
                }
                if (args[0].equalsIgnoreCase("stop")) {
                    if (state.getState() != GameState.STOPPED) {
                        Bukkit.broadcastMessage("Game is stopping!");
                        state.stopGame();
                        return true;
                    }
                }
            }
        }

        if (label.equalsIgnoreCase("ready")) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("Only players can use this command!");
                return true;
            }

            if (state.getState() != GameState.STARTING) {
                sender.sendMessage("The game is not in the starting state!");
                return true;
            }

            state.getGame().addPlayerToGame(player);
            player.sendMessage("You are ready! Waiting for other players to be ready!");
            return true;
        }

        if (label.equalsIgnoreCase("points")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Only players can use this command!");
                return true;
            }
            if (state.getState() == GameState.STOPPED) {
                sender.sendMessage("No Game is running!");
                return true;
            }
            Participant participant = state.getGame().getParticipantByName(sender.getName());
            if (participant == null) {
                sender.sendMessage("You are not participating in this game!");
                return true;
            }
            PlayerGuiData playerGuiData = new PlayerGuiData((Player) sender);
            playerGuiData.setGame(state.getGame());
            playerGuiData.setParticipant(state.getGame().getParticipantByName(sender.getName()));
            new UnCollectedList(playerGuiData).open();
            sender.sendMessage("You have " + participant.getPoints() + " points!");
            return true;
        }

        if (label.equalsIgnoreCase("vote")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Only players can use this command!");
                return true;
            }
            if (state.getState() != GameState.EVENT) {
                sender.sendMessage("No Event is running!");
                return true;
            }
            Participant participant = state.getGame().getParticipantByName(sender.getName());
            if (participant == null) {
                sender.sendMessage("You are not participating in this game!");
                return true;
            }
            if (state.getGame().isEventVotesClosed()) {
                sender.sendMessage("Event votes are closed");
                return true;
            }
            PlayerGuiData playerGuiData = new PlayerGuiData((Player) sender);
            playerGuiData.setGame(state.getGame());
            playerGuiData.setParticipant(state.getGame().getParticipantByName(sender.getName()));
            new voteYesOrNo(playerGuiData).open();
        }

        if (label.equalsIgnoreCase("language")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage("Only players can use this command!");
                return true;
            }
            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Please specify a language! (en, de)");
                return true;
            }
            try {
                System.out.println(sender.getName() + " set language to " + args[0]);
                LanguagePreferencesBasedProperties.setLanguagePreference(((Player) sender).getUniqueId(), args[0]);
                sender.sendMessage(ChatColor.GREEN + "Language set to " + args[0]);
                return true;
            } catch (Exception e) {
                sender.sendMessage(ChatColor.RED + e.getMessage());
                return true;
            }
        }

        return false;
    }
}
