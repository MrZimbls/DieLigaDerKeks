package org.zimbls.DieLigaDerKeks.game;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.zimbls.DieLigaDerKeks.util.Mob;

import java.util.HashSet;
import java.util.Set;

public class Participant {
   private Player player;
   private Set<Mob> killedMobs = new HashSet<Mob>();
   private boolean isDead = false;
   private Location lastGameLocation;
   private int points = 0;
   private GameScoreboard scoreboard;

   public Participant(Player player) {
      this.player = player;
      this.scoreboard = new GameScoreboard();
   }

   public boolean addIfNewMob(Mob mob) {
      if (killedMobs.contains(mob)) {
         return false;
      }
      killedMobs.add(mob);
      points = points + mob.getPoints();
      return true;
   }

   public Player getPlayer() {
      return player;
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
      lastGameLocation =  player.getLocation();
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
      player.setScoreboard(scoreboard.getScoreboard());
   }
}
