package org.zimbls.DieLigaDerKeks.game;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.zimbls.DieLigaDerKeks.util.Mob;

import java.util.HashSet;
import java.util.Set;

public class Participant {
   private Player player;
   private Set<Mob> killedMobs = new HashSet<Mob>();

   private Location lastGameLocation;

   public Participant(Player player) {
      this.player = player;
   }

   public boolean addIfNewMob(Mob mob) {
      if (killedMobs.contains(mob)) {
         return false;
      }
      killedMobs.add(mob);
      return true;
   }

   public Player getPlayer() {
      return player;
   }

   public int getPoints() {
      int points = 0;
      for (Mob mob:killedMobs) {
         points = points + mob.getPoints();
      }
      return points;
   }

   public Set<Mob> getKilledMobs() {
      return killedMobs;
   }

   public Location getLastGameLocation() {
      return lastGameLocation;
   }

   public void setLastGameLocation(Location lastGameLocation) {
      this.lastGameLocation = lastGameLocation;
   }
}
