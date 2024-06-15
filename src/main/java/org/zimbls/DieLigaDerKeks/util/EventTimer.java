package org.zimbls.DieLigaDerKeks.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Score;
import org.zimbls.DieLigaDerKeks.game.EventScoreboard;
import org.zimbls.DieLigaDerKeks.game.GameScoreboard;
import org.zimbls.DieLigaDerKeks.stateMachine.GameState;
import org.zimbls.DieLigaDerKeks.stateMachine.GameStateMachine;

public class EventTimer extends BukkitRunnable {
   // TODO: FIX TIMER (1 * 60 + 10)
   private int remainingTime = 5 * 60 + 10; // 5 min in seconds
   private final EventScoreboard eventScoreboard;
   private GameStateMachine state;
   boolean revealVotes = false;
   private JavaPlugin plugin;

   public EventTimer(EventScoreboard eventScoreboard, GameStateMachine state, JavaPlugin plugin) {
      this.eventScoreboard = eventScoreboard;
      this.state = state;
      this.plugin = plugin;
   }

   @Override
   public void run() {
      if (remainingTime <= 0) {
         state.endEvent(state.getGame().getYesVotes() > state.getGame().getNoVotes());

         this.cancel();
      }

      updateTimer();

      if (remainingTime == 10) {
         state.getGame().setEventVotesClosed(true);
         revealVotes = true;

         // Triggered when the event is about to start
         state.getGame().getParticipants().forEach(participant -> {
            participant.getPlayer().sendMessage(ChatColor.GOLD + "The votes are in! The game will continue in 10 seconds!");
         });

         Countdown countdown = new Countdown(plugin, 10, null);
         countdown.start();
      }

      remainingTime--;
   }

   private void updateTimer() {
      int displayTime = remainingTime - 10;
      if (displayTime >= 0) {
         int minutes = (displayTime % 3600) / 60;
         int seconds = displayTime % 60;
         String timeFormatted = String.format("%02d:%02d", minutes, seconds);

         Score timeScore = eventScoreboard.getObjective().getScore(ChatColor.DARK_GRAY + "/vote");
         eventScoreboard.getObjective().setDisplayName(ChatColor.DARK_GRAY + "Event Time: " + ChatColor.GOLD + timeFormatted);

         timeScore.setScore(0); // The actual score is irrelevant in this context
      }

      if (revealVotes) {
         Score yesVotes = eventScoreboard.getObjective().getScore("Yes Votes:");
         Score noVotes = eventScoreboard.getObjective().getScore("No Votes:");
         yesVotes.setScore(state.getGame().getYesVotes());
         noVotes.setScore(state.getGame().getNoVotes());
      }
   }
}
