package org.zimbls.DieLigaDerKeks.util;

import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Score;
import org.zimbls.DieLigaDerKeks.game.EventScoreboard;
import org.zimbls.DieLigaDerKeks.game.GameScoreboard;
import org.zimbls.DieLigaDerKeks.stateMachine.GameStateMachine;

public class EventTimer extends BukkitRunnable {
   private int remainingTime = 1 * 60 + 20; // 5 min in seconds
   private final EventScoreboard eventScoreboard;
   private GameStateMachine state;
   boolean revealVotes = false;

   public EventTimer(EventScoreboard eventScoreboard, GameStateMachine state) {
      this.eventScoreboard = eventScoreboard;
      this.state = state;
   }

   @Override
   public void run() {
      if (remainingTime <= 0) {
         if (state.getGame().getYesVotes() > state.getGame().getNoVotes()) {
            state.endEvent(true);
         } else {
            state.endEvent(false);
         }

         this.cancel();
      }

      updateTimer();

      if (remainingTime == 20) {
         state.getGame().setEventVotesClosed(true);
         revealVotes = true;
      }

      remainingTime--;
   }

   private void updateTimer() {
      int displayTime = remainingTime - 20;
      if (displayTime >= 0) {
         int minutes = (displayTime % 3600) / 60;
         int seconds = displayTime % 60;
         String timeFormatted = String.format("%02d:%02d", minutes, seconds);

         Score timeScore = eventScoreboard.getObjective().getScore(ChatColor.DARK_GRAY + "/vote");
         eventScoreboard.getObjective().setDisplayName(ChatColor.DARK_GRAY + "Event Time: " + ChatColor.GOLD + timeFormatted);

         timeScore.setScore(0); // The actual score is irrelevant in this context
      } else {

      }
      if (revealVotes) {
         Score yesVotes = eventScoreboard.getObjective().getScore("Yes Votes:");
         Score noVotes = eventScoreboard.getObjective().getScore("No Votes:");
         yesVotes.setScore(state.getGame().getYesVotes());
         noVotes.setScore(state.getGame().getNoVotes());
      }
   }
}
