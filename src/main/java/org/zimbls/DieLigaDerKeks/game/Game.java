package org.zimbls.DieLigaDerKeks.game;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.zimbls.DieLigaDerKeks.util.ImportMobCsv;
import org.zimbls.DieLigaDerKeks.util.Mob;
import org.zimbls.DieLigaDerKeks.util.TimerTask;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

   public Game(World lobbyMap, JavaPlugin plugin) {
      this.lobbyMap = lobbyMap;
      setupScoreboard();
      mobMap = ImportMobCsv.loadMobPoints("plugins/rewardTable.csv");
      gameScoreboard = new GameScoreboard();
      timerTask = new TimerTask(gameScoreboard);
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

   public void setGameScoreboard() {
      Bukkit.getOnlinePlayers().forEach(player -> {
         if (participants.containsKey(player.getName())) {
            player.setScoreboard(gameScoreboard.getScoreboard());
         }
      });
   }

   public void addPlayerToGame(Player player){
      players.forEach(participant -> {
         if (participant.getPlayer() == player) {
            return;
         }
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

   public void createGameWorld() {
      this.gameMap = new GameMap();
   }

   public Map<String, Mob> getMobMap() {
      return mobMap;
   }

   public void setAllLastGameLocations() {
      players.forEach(Participant::setLastGameLocation);
   }

   public void pauseTimer(){
      timerTask.setPaused(true);
   }

   public void continueTimer(){
      timerTask.setPaused(false);
   }
}
