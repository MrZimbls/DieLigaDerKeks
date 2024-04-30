package org.zimbls.DieLigaDerKeks.game;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.zimbls.DieLigaDerKeks.game.events.Event;
import org.zimbls.DieLigaDerKeks.stateMachine.GameStateMachine;
import org.zimbls.DieLigaDerKeks.util.ImportMobCsv;
import org.zimbls.DieLigaDerKeks.util.LanguagePreferencesBasedProperties;
import org.zimbls.DieLigaDerKeks.util.Mob;
import org.zimbls.DieLigaDerKeks.util.TimerTask;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Game {
    private Map<String, Participant> participants = new HashMap<>();
    private Set<Participant> players = new HashSet<>();
    private ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
    private Map<String, Mob> mobMap;
    private Scoreboard scoreboard;
    private GameScoreboard gameScoreboard;
    private TimerTask timerTask;
    private Objective objective;
    private GameMap gameMap;
    private World lobbyMap;
    private Map<Player, Boolean> eventVotes = new HashMap<>();
    private Event activEvent;
    private boolean eventVotesClosed = false;
    private boolean pvpEnabled = false;

    public Game(World lobbyMap, JavaPlugin plugin, GameStateMachine state) {
        this.lobbyMap = lobbyMap;

        setupScoreboard();
        mobMap = ImportMobCsv.loadMobPoints("plugins/rewardTable.csv");
        gameScoreboard = new GameScoreboard();
        timerTask = new TimerTask(gameScoreboard, state);
        timerTask.setPaused(true);
        timerTask.runTaskTimer(plugin, 0L, 20L);
    }

    public void teleportAllPlayersToGameMap() {
        Location spawnLocation = gameMap.getGameWorld().getSpawnLocation(); // Get the spawn location of the world

        for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
            if (participants.containsKey(onlinePlayer.getName())) {
                if (participants.get(onlinePlayer.getName()).getLastGameLocation() != null) {
                    spawnLocation = participants.get(onlinePlayer.getName()).getLastGameLocation();
                }
                onlinePlayer.teleport(spawnLocation);
            }
        }
    }

    public void teleportAllPlayersToLobbyMap() {
        Location spawnLocation = lobbyMap.getSpawnLocation(); // Get the spawn location of the world

        for (Player onlinePlayer : Bukkit.getServer().getOnlinePlayers()) {
            if (participants.containsKey(onlinePlayer.getName())) {
                onlinePlayer.teleport(spawnLocation);
            }
        }
    }

    private void setupScoreboard() {
        scoreboard = scoreboardManager.getNewScoreboard();
        objective = scoreboard.registerNewObjective("readyPlayers", "dummy", "Ready Players");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    private void updateScoreboard() {
        objective.getScoreboard().getEntries().forEach(entry -> scoreboard.resetScores(entry));
        players.forEach(player -> objective.getScore(player.getPlayer().getName()).setScore(0));
        players.forEach(player -> player.getPlayer().setScoreboard(scoreboard));
    }

    public void clearScoreboards() {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }
    }

    public World getlobbyMap() {
        return lobbyMap;
    }

    public void setGameScoreboard() {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (participants.containsKey(player.getName())) {
                player.setScoreboard(gameScoreboard.getScoreboard());
            }
        });
    }

    public void setEventScoreboard(EventScoreboard eventScoreboard) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (participants.containsKey(player.getName())) {
                player.setScoreboard(eventScoreboard.getScoreboard());
            }
        });
    }

    public void addPlayerToGame(Player player) {
        players.forEach(participant -> {
            if (participant.getPlayer() == player) return;
        });
        Participant participant = new Participant(player);
        players.add(participant);
        participants.put(player.getName(), participant);
        updateScoreboard();
    }

    public Participant getParticipantByName(String name) {
        return participants.get(name);
    }

    public Mob getMobFormMobName(String name) {
        return mobMap.get(name);
    }

    public Set<Participant> getParticipants() {
        return players;
    }

    public void resetParticipants() {
        players.forEach(Participant::resetPlayer);
    }

    public Set<Player> getParticipatingPlayers() {
        Set<Player> participatingPlayers = new HashSet<>();
        players.forEach(participant -> participatingPlayers.add(participant.getPlayer()));
        return participatingPlayers;
    }

    public void createGameWorld() {
        this.gameMap = new GameMap();
        this.gameMap.getGameWorld().setPVP(false);
    }

    public GameMap getGameWorld() {
        return this.gameMap;
    }

    public Map<String, Mob> getMobMap() {
        return mobMap;
    }

    public void setAllLastGameLocations() {
        players.forEach(Participant::setLastGameLocation);
    }

    public void pauseTimer() {
        timerTask.setPaused(true);
    }

    public void cancelTimer() {
        timerTask.cancel();
    }

    public void continueTimer() {
        timerTask.setPaused(false);
    }

    public void setEventVote(Player player, boolean vote) {
        eventVotes.put(player, vote);
    }

    public int getNumberOfPlayersAlive() {
        AtomicInteger count = new AtomicInteger(0);
        players.forEach(participant -> {
            if (!participant.isDead()) {
                count.getAndIncrement();
            }
        });
        return count.get();
    }

    public int getYesVotes() {
        AtomicInteger counter = new AtomicInteger(0);
        eventVotes.values().forEach(vote -> {
            if (vote) {
                counter.getAndIncrement();
            }
        });
        return counter.get();
    }

    public int getNoVotes() {
        AtomicInteger counter = new AtomicInteger(0);
        eventVotes.values().forEach(vote -> {
            if (!vote) {
                counter.getAndIncrement();
            }
        });
        return counter.get();
    }

    public Event getActiveEvent() {
        return activEvent;
    }

    public void setActiveEvent(Event activEvent) {
        this.activEvent = activEvent;
    }

    public boolean isEventVotesClosed() {
        return eventVotesClosed;
    }

    public void setEventVotesClosed(boolean eventVotesClosed) {
        this.eventVotesClosed = eventVotesClosed;
    }

    public void enablePvP(boolean pvpEnabled) {
        if (!this.pvpEnabled && pvpEnabled) {
            Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(ChatColor.RED + ChatColor.BOLD.toString() + LanguagePreferencesBasedProperties.getProperty(player.getUniqueId(), "game.messages.enabledPvP")));
        } else if (this.pvpEnabled && !pvpEnabled) {
            Bukkit.getOnlinePlayers().forEach(player -> player.sendMessage(ChatColor.GREEN + ChatColor.BOLD.toString() + "PvP is now disabled!"));
        }
        this.pvpEnabled = pvpEnabled;
        gameMap.getGameWorld().setPVP(pvpEnabled);
    }
}
