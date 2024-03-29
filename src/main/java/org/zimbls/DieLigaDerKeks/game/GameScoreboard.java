package org.zimbls.DieLigaDerKeks.game;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class GameScoreboard {

   private Scoreboard scoreboard;
   private Objective objective;

   public GameScoreboard() {
      this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
      this.objective = scoreboard.registerNewObjective("timer", "dummy", "Time Left");
      this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
   }

   public Scoreboard getScoreboard() {
      return scoreboard;
   }

   public Objective getObjective() {
      return objective;
   }
}
