package org.zimbls.DieLigaDerKeks.game;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;
import org.zimbls.DieLigaDerKeks.game.events.Event;
import org.zimbls.DieLigaDerKeks.stateMachine.GameStateMachine;
import org.zimbls.DieLigaDerKeks.util.ImportMobCsv;
import org.zimbls.DieLigaDerKeks.util.LanguagePreferencesBasedProperties;
import org.zimbls.DieLigaDerKeks.util.Mob;
import org.zimbls.DieLigaDerKeks.util.TimerTask;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Game {
    private Map<String, Participant> participants = new HashMap<>();
    private Set<Participant> players = new HashSet<>();
    private Set<Participant> unactivePlayers = new HashSet<>();
    private ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
    private Map<String, Mob> mobMap;
    private Scoreboard scoreboard;
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
        timerTask = new TimerTask(state);
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

    public void deleteGameWorld() {
        // Delete the game world
        Bukkit.getServer().unloadWorld(gameMap.getGameWorld(), false);

        String worldName = gameMap.getGameWorld().getName();
        System.out.println("Deleting game world: " + worldName);

        // Delete the world folder
        File worldFolder = new File(Bukkit.getWorldContainer(), worldName);
        try {
            deleteDirectory(worldFolder);
            System.out.println("Deleted game world: " + worldName);
        } catch (IOException e) {
            System.out.println("Failed to delete game world: " + worldName);
        }
    }

    private void deleteDirectory(File directory) throws IOException {
        if (directory.isDirectory()) {
            Files.walk(Paths.get(directory.getPath()))
                    .map(java.nio.file.Path::toFile)
                    .sorted((o1, o2) -> -o1.compareTo(o2)) // sort in reverse order to delete files before directories
                    .forEach(File::delete);
        } else {
            directory.delete();
        }
    }

    public List<Participant> sortParticipantsByPoints() {
        // Get all online participants
        Set<Participant> participants = this.getParticipants();

        // Loop through participants and collect their name and points, then sort them by points
        List<Participant> sortedParticipants = new ArrayList<>(participants);

        // Sort the participants by points
        sortedParticipants.sort((p1, p2) -> p2.getPoints() - p1.getPoints());

        return sortedParticipants;
    }

    public void logGameProgress(List<Participant> sortedParticipants) {
        // Send a message to each participant with the sorted list of participants
        sortedParticipants.forEach(participant -> {
            // Counter for the ranking list
            AtomicInteger counter = new AtomicInteger(0);
            participant.getPlayer().sendMessage("");
            participant.getPlayer().sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Player points:");
            participant.getPlayer().sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "---");
            participant.getPlayer().sendMessage("");

            sortedParticipants.forEach(p -> {
                counter.getAndIncrement();
                participant.getPlayer().sendMessage(ChatColor.GREEN + "#" + counter + " " + p.getPlayer().getName() + " ---> " + p.getPoints() + " Points");
            });

            participant.getPlayer().sendMessage("");
            participant.getPlayer().sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "---");
            participant.getPlayer().sendMessage("");
        });
    }

    public void logGameWinner(List<Participant> sortedParticipants) {
        // Get the winner of the game
        Participant winner = sortedParticipants.get(0);

        // Send a message to the winner
        winner.getPlayer().sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Congratulations! You won the game with " + winner.getPoints() + " points!");

        sortedParticipants.forEach(participant -> {
            if (participant != winner)
                participant.getPlayer().sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "You lost the game with " + participant.getPoints() + " points!");
            // Send a title to each participant with the winner
            participant.getPlayer().sendTitle(ChatColor.GOLD + "Game over!", winner.getPlayer().getName() + " has won the game!", 10, 70, 20);
        });
    }

    private void setupScoreboard() {
        scoreboard = scoreboardManager.getNewScoreboard();
        objective = scoreboard.registerNewObjective("readyPlayers", Criteria.DUMMY, "Ready Players");
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
                getParticipantByName(player.getName()).setScoreboard();
            } else {
                System.out.println("Player " + player.getName() + " is not a participant in the game! Can't set game scoreboard!");
            }
        });
    }

    public void setEventScoreboard(EventScoreboard eventScoreboard) {
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (participants.containsKey(player.getName())) {
                player.setScoreboard(eventScoreboard.getScoreboard());
            } else {
                System.out.println("Player " + player.getName() + " is not a participant in the game! Can't set event scoreboard!");
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

    public Participant getParticipantByUUID(UUID uuid) {
        for (Participant participant : players) {
            if (participant.getPlayer().getUniqueId().equals(uuid)) {
                return participant;
            }
        }
        return null;
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

    public World getLobbyMap() {
        return lobbyMap;
    }

    public void setParticipantInactive(Participant participant) {
        unactivePlayers.add(participant);
        players.remove(participant);
        participants.remove(participant.getPlayer().getName());
    }

    public void setParticipantActive(Participant participant) {
        players.add(participant);
        participants.put(participant.getPlayer().getName(), participant);
        unactivePlayers.remove(participant);
    }

    public Participant getUnactivePlayerByUUID(UUID uuid) {
        for (Participant participant : unactivePlayers) {
            if (participant.getPlayerUUID().equals(uuid)) {
                return participant;
            }
        }
        return null;
    }
}
