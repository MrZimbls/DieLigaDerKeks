package org.zimbls.DieLigaDerKeks.util;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.plugin.java.JavaPlugin;

public class Countdown {

   private JavaPlugin plugin;
   private int seconds;
   private BukkitTask task;
   private CountdownAction action;

   public Countdown(JavaPlugin plugin, int seconds, CountdownAction action) {
      this.plugin = plugin;
      this.seconds = seconds;
      this.action = action;
   }

   public void start() {
      task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
         if (seconds <= 0) {
            Bukkit.broadcastMessage("Countdown finished!");
            task.cancel(); // Cancel the task
            if (action != null) {
               action.onCountdownComplete();
            }
            return;
         }
         Bukkit.broadcastMessage("Starts in: " + seconds);
         seconds--;
      }, 0L, 20L); // Schedule the task with 0 ticks initial delay, 20 ticks (1 second) period
   }
}
