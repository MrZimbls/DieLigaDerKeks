package org.zimbls.DieLigaDerKeks.game;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.zimbls.DieLigaDerKeks.util.Mob;

import java.util.*;

public class Participant {
   private UUID playerUUID;
   private Set<Mob> killedMobs = new HashSet<Mob>();
   private boolean isDead = false;
   private Location lastGameLocation;
   private int points = 0;
   private GameScoreboard scoreboard;
   private Player playerBackupInstance;
   private ArrayList<String> equippedCustomItems = new ArrayList<>();

   public Participant(Player player) {
      this.playerUUID = player.getUniqueId();
      this.scoreboard = new GameScoreboard();
      this.playerBackupInstance = player;
   }

   public UUID getPlayerUUID() {
      return playerUUID;
   }

   public boolean addIfNewMob(Mob mob) {
      if (killedMobs.contains(mob)) return false;
      killedMobs.add(mob);
      points = points + mob.getPoints();
      return true;
   }

   public Player getPlayer() {
      if (Bukkit.getPlayer(playerUUID) != null) {
         return Bukkit.getPlayer(playerUUID);
      } else {
         return playerBackupInstance;
      }
   }

   public void setPlayer(Player player) {
      this.playerBackupInstance = player;
   }

   public int getPoints() {
      return points;
   }

   public void addPoints(Integer amount) {
      points += amount;
   }

   public void removePoints(Integer amount) {
      points -= amount;
      if (points < 0) points = 0;
   }

   public void resetPlayer() {
      killedMobs.clear();
      points = 0;
      isDead = false;
      getPlayer().setGameMode(GameMode.SURVIVAL);
      lastGameLocation = null;
   }

   public void setPoints(int p) {
      points = p;
   }

   public Set<Mob> getKilledMobs() {
      return killedMobs;
   }

   public Location getLastGameLocation() {
      return lastGameLocation;
   }

   public void setLastGameLocation() {
      lastGameLocation =  getPlayer().getLocation();
   }

   public void setSpecificLocation(Location location) {
      lastGameLocation = location;
   }

   public boolean isDead() {
      return isDead;
   }

   public void setDead(boolean dead) {
      isDead = dead;
   }

   public GameScoreboard getScoreboard() {
      return scoreboard;
   }

   public void setScoreboard() {
      GameScoreboard gameScoreboard = this.getScoreboard();
      getPlayer().setScoreboard(gameScoreboard.getScoreboard());
   }

   public void teleportToGameMap(GameMap gameMap) {
      Location spawnLocation = gameMap.getGameWorld().getSpawnLocation(); // Get the spawn location of the world
      if (this.lastGameLocation != null) {
         spawnLocation = this.lastGameLocation;
      }
      getPlayer().teleport(spawnLocation);
   }

   public void teleportToLobbyMap(World lobbyMap) {
      Location spawnLocation = lobbyMap.getSpawnLocation(); // Get the spawn location of the world
      getPlayer().teleport(spawnLocation);
   }

   public ArrayList<String> getEquippedCustomItems() {
      return equippedCustomItems;
   }

   public void addEquippedCustomItem(String item) {
      this.equippedCustomItems.add(item);
   }

    public void removeEquippedCustomItem(String item) {
        this.equippedCustomItems.remove(item);
    }
}
