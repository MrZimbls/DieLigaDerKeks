package org.zimbls.DieLigaDerKeks.stateMachine;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.zimbls.DieLigaDerKeks.game.EventScoreboard;
import org.zimbls.DieLigaDerKeks.game.Game;
import org.zimbls.DieLigaDerKeks.game.events.*;
import org.zimbls.DieLigaDerKeks.util.EventTimer;
import org.zimbls.DieLigaDerKeks.util.ReminderTask;

import java.util.ArrayList;
import java.util.Collections;

public class GameStateMachine {
    private GameState currentState;
    private Game game;
    private final ArrayList<Event> availableEvents = new ArrayList<>();
    private JavaPlugin plugin;
    BukkitTask readyReminderTask;

    public GameStateMachine() {
        currentState = GameState.STOPPED; // Initial state
    }

    public Game getGame() {
        return game;
    }

    public GameState getState() {
        return currentState;
    }

    public void startGame(World lobbyMap, JavaPlugin plugin) {
        currentState = GameState.STARTING;
        game = new Game(lobbyMap, plugin, this);
        game.createGameWorld();
        availableEvents.add(new RandomPlayerTpEvent(this));
        availableEvents.add(new SwapPointsEvent(this));
        availableEvents.add(new RevealPointsEvent(this));
        availableEvents.add(new HalfWorldBorderEvent(this));
        availableEvents.add(new RevealLocationEvent(this));
        availableEvents.add(new BuffLastTwoPlayersEvent(this));
        availableEvents.add(new NerfTopTwoPlayersEvent(this));
        availableEvents.add(new FirstToSleepInBedEvent(this));
        this.plugin = plugin;

        // Triggered when the game is first started
        plugin.getServer().getOnlinePlayers().forEach(player -> {
            player.sendTitle(ChatColor.GREEN + "Game starting soon!", "Please enter /ready in the chat to join the game!", 10, 70, 20);
            player.sendMessage("Please enter " + ChatColor.GREEN + "/ready" + ChatColor.RESET + " to join the game!");
        });

        // Send a reminder message to all online players who haven't entered /ready yet
        this.readyReminderTask = new ReminderTask(plugin, game, "Please enter " + ChatColor.GREEN + "/ready" + ChatColor.RESET + " to join the game if you haven't already and would like to participate!")
                .runTaskTimer(plugin, 15 * 20L, 20L * 30);
    }

    public void runGame(GameState previousState) {
        currentState = GameState.RUNNING;
        game.clearScoreboards();
        game.teleportAllPlayersToGameMap();
        game.continueTimer();
        game.setGameScoreboard();
        this.readyReminderTask.cancel();

        // Triggered when the game continues after a pause
        if (previousState == GameState.PAUSED) {
            game.getParticipants().forEach(participant -> {
                participant.getPlayer().sendTitle(ChatColor.GREEN + "Game continuing!", "The next event starts in 30 minutes!", 10, 70, 20);
            });
        } else if (previousState == GameState.STARTING) {
            game.getParticipants().forEach(participant -> {
                participant.getPlayer().sendTitle(ChatColor.GREEN + "Game started!", "The first event starts in 30 minutes!", 10, 70, 20);
            });
        }
    }

    public void pauseGame() {
        currentState = GameState.PAUSED;
        game.setAllLastGameLocations();
        game.clearScoreboards();
        game.teleportAllPlayersToLobbyMap();
        game.pauseTimer();
    }

    public void triggerEvent() {
        currentState = GameState.EVENT;
        game.setAllLastGameLocations();
        game.clearScoreboards();
        game.teleportAllPlayersToLobbyMap();
        game.pauseTimer();
        game.setEventVotesClosed(false);

        // Triggered when the event is event voting starts
        game.getParticipants().forEach(participant -> {
            participant.getPlayer().sendTitle(ChatColor.GREEN + "Vote for the next event!", "To vote, please enter /vote in the chat!", 10, 70, 20);
            participant.getPlayer().sendMessage("Please enter " + ChatColor.GREEN + "/vote" + ChatColor.RESET + " to vote for the the event!");
            participant.getPlayer().sendMessage("When the majority of players voted YES for it, the event will happen!");
        });

        // TODO: Send a reminder message to all online players who haven't entered /vote yet

        ArrayList<Event> possibleEvents = new ArrayList<Event>();
        for (Event event : availableEvents) {
            if (event.isPossibleForPlayerCount(game.getNumberOfPlayersAlive())) {
                possibleEvents.add(event);
            }
            possibleEvents.add(event); //TODO remove, this is only for testing
        }

        Collections.shuffle(possibleEvents);
        game.setActivEvent(possibleEvents.get(0));

        EventScoreboard eventScoreboard = new EventScoreboard();
        game.setEventScoreboard(eventScoreboard);
        EventTimer eventTask = new EventTimer(eventScoreboard, this, plugin);
        eventTask.runTaskTimer(plugin, 0L, 20L);
    }

    public void endEvent(boolean runEvent) {
        if (runEvent) {
            game.getActivEvent().runEvent();
        } else {
            game.getParticipants().forEach(participant -> {
                participant.getPlayer().sendTitle(ChatColor.GREEN + "No event!", "The next event starts in 30 minutes!", 10, 70, 20);
                participant.getPlayer().sendMessage(ChatColor.GREEN + "The game will continue without an event! Not enough players voted YES for the event!");
            });
        }
        game.enablePvP(true);
        runGame(GameState.EVENT);
    }

    public void stopGame() {
        currentState = GameState.STOPPED;
    }
}
