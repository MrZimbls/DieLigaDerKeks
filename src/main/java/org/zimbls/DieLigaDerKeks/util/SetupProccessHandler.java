package org.zimbls.DieLigaDerKeks.util;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.zimbls.DieLigaDerKeks.listener.LobbyEventListener;
import org.zimbls.DieLigaDerKeks.listener.PlayerMassageListener;
import org.zimbls.DieLigaDerKeks.stateMachine.GameStateMachine;

public class SetupProccessHandler {

   private Plugin plugin;
   private GameStateMachine state;
   private Boolean isSetupRunning = false;
   private Player player;
   private LobbyEventListener lobbyEventListener;
   private PlayerMassageListener chatListener;
   private int currentSetupStep = 0;
   private BukkitTask currentDisplayTask;
   private World lobbyWorld;

   public SetupProccessHandler(Plugin plugin, GameStateMachine state) {
      this.plugin = plugin;
      this.state = state;
   }

   public void startSetup(Player player) {
      if (isSetupRunning) {
         plugin.getLogger().warning("Setup is already running!");
         return;
      }
      player.sendMessage("");
      player.sendMessage(ChatColor.GOLD + "Setup started!");
      player.sendMessage("");

      this.player = player;
      isSetupRunning = true;
      chatListener = new PlayerMassageListener(player, this);
      lobbyEventListener = new LobbyEventListener(state);
      currentSetupStep = 0;

      plugin.getServer().getPluginManager().registerEvents(chatListener, plugin);
      plugin.getServer().getPluginManager().registerEvents(lobbyEventListener, plugin);
      nextSetupStepMessage();
   }

   public void nextSetupStepMessage() {
      if (!isSetupRunning) {
         plugin.getLogger().warning("Setup is not running!");
         return;
      }

      switch (currentSetupStep) {
         case 0 -> {
            player.sendMessage("");
            player.sendMessage(ChatColor.AQUA + "Set this world to the Lobby/Event World? (Y/N)");
            player.sendMessage("");
         }
         case 1 -> {
            player.sendMessage("");
            player.sendMessage(ChatColor.AQUA + "Set world Spawn to current location? (Y/N)");
            player.sendMessage("");
            currentDisplayTask = displayCurrentPosition("Current Location: ", false);
         }
         case 2 -> {
            player.sendMessage("");
            player.sendMessage(ChatColor.AQUA + "Disable day night cycle? (Y/N)");
            player.sendMessage("");
         }
         case 3 -> {
            player.sendMessage("");
            player.sendMessage(ChatColor.AQUA + "Disable weather? (Y/N)");
            player.sendMessage("");
         }
         case 4 -> {
            player.sendMessage("");
            player.sendMessage(ChatColor.AQUA + "Disable mob spawn? (Y/N)");
            player.sendMessage("");
         }
         case 5 -> {
            player.sendMessage("");
            player.sendMessage(ChatColor.AQUA + "Disable damage? (Y/N)");
            player.sendMessage("");
         }
         case 6 -> {
            player.sendMessage("");
            player.sendMessage(ChatColor.AQUA + "Disable block physics? (Y/N)");
            player.sendMessage("");
         }
         case 7 -> {
            player.sendMessage("");
            player.sendMessage(ChatColor.AQUA + "Disable block break and place? (Y/N)");
            player.sendMessage("");
         }
         case 8 -> {
            player.sendMessage("");
            player.sendMessage(ChatColor.AQUA + "Teleport back to spawn on current height? (Y/N)");
            player.sendMessage("");
            currentDisplayTask = displayCurrentPosition("Current height: ", true);
         }
         case 9 -> {
            player.sendMessage("");
            player.sendMessage(ChatColor.AQUA + "Setup done!");
            player.sendMessage("");
            stopSetup();
         }
         default -> {
            stopSetup();
         }
      }
   }

   public void executeSetupStep(String message) {
      if (!isSetupRunning) {
         plugin.getLogger().warning("Setup is not running!");
         return;
      }

      if (currentDisplayTask != null && !currentDisplayTask.isCancelled()) {
         currentDisplayTask.cancel();
      }

      switch (currentSetupStep) {
         case 0 -> {
            if (message.equalsIgnoreCase("Y")) {
               lobbyWorld = player.getWorld();
               plugin.getLogger().info("Lobby/Event World set to: " + lobbyWorld.getName());
               state.setLobbyMap(lobbyWorld);
               lobbyEventListener.lobbyWorldName = lobbyWorld.getName();
               player.sendMessage(ChatColor.GREEN + "World set to Lobby/Event World!");
               player.sendMessage("");
            } else {
               player.sendMessage(ChatColor.RED + "Setup canceled!");
               stopSetup();
            }
         }
         case 1 -> {
            if (message.equalsIgnoreCase("Y")) {
               lobbyWorld.setSpawnLocation(player.getLocation());
               player.sendMessage(ChatColor.GREEN + "World spawn set to current location!");
               player.sendMessage("");
            } else {
               player.sendMessage(ChatColor.RED + "World spawn not set to current location!");
               player.sendMessage("");
            }
         }
         case 2 -> {
            if (message.equalsIgnoreCase("Y")) {
               plugin.getServer().getScheduler().runTask(plugin, () -> lobbyWorld.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false));
               player.sendMessage(ChatColor.GREEN + "Day night cycle disabled!");
               player.sendMessage("");
            } else {
               plugin.getServer().getScheduler().runTask(plugin, () -> lobbyWorld.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, true));
               player.sendMessage(ChatColor.RED + "Day night cycle not disabled!");
               player.sendMessage("");
            }
         }
         case 3 -> {
            if (message.equalsIgnoreCase("Y")) {
               plugin.getServer().getScheduler().runTask(plugin, () -> lobbyWorld.setGameRule(GameRule.DO_WEATHER_CYCLE, false));
               player.sendMessage(ChatColor.GREEN + "Weather disabled!");
               player.sendMessage("");
            } else {
               plugin.getServer().getScheduler().runTask(plugin, () -> lobbyWorld.setGameRule(GameRule.DO_WEATHER_CYCLE, true));
               player.sendMessage(ChatColor.RED + "Weather not disabled!");
               player.sendMessage("");
            }
         }
         case 4 -> {
            if (message.equalsIgnoreCase("Y")) {
               plugin.getServer().getScheduler().runTask(plugin, () -> lobbyWorld.setGameRule(GameRule.DO_MOB_SPAWNING, false));
               player.sendMessage(ChatColor.GREEN + "MobSpawn disabled!");
               player.sendMessage("");
            } else {
               plugin.getServer().getScheduler().runTask(plugin, () -> lobbyWorld.setGameRule(GameRule.DO_MOB_SPAWNING, true));
               player.sendMessage(ChatColor.RED + "MobSpawn not disabled!");
               player.sendMessage("");
            }
         }
         case 5 -> {
            if (message.equalsIgnoreCase("Y")) {
               lobbyEventListener.disableDamage = true;
               player.sendMessage(ChatColor.GREEN + "Damage disabled!");
               player.sendMessage("");
            } else {
               lobbyEventListener.disableDamage = false;
               player.sendMessage(ChatColor.RED + "Damage not disabled!");
               player.sendMessage("");
            }
         }
         case 6 -> {
            if (message.equalsIgnoreCase("Y")) {
               lobbyEventListener.disableBlockPhysics = true;
               player.sendMessage(ChatColor.GREEN + "BlockPhysics disabled!");
               player.sendMessage("");
            } else {
               lobbyEventListener.disableBlockPhysics = false;
               player.sendMessage(ChatColor.RED + "BlockPhysics not disabled!");
               player.sendMessage("");
            }
         }
         case 7 -> {
            if (message.equalsIgnoreCase("Y")) {
               lobbyEventListener.disableBlockBreakAndPlace = true;
               player.sendMessage(ChatColor.GREEN + "BlockBreakAndPlace disabled!");
               player.sendMessage("");
            } else {
               lobbyEventListener.disableBlockBreakAndPlace = false;
               player.sendMessage(ChatColor.RED + "BlockBreakAndPlace not disabled!");
               player.sendMessage("");
            }
         }
         case 8 -> {
            int y = player.getLocation().getBlockY();
            if (message.equalsIgnoreCase("Y")) {
               lobbyEventListener.teleportBackToSpawnOnY = y;
               player.sendMessage(ChatColor.GREEN + "TeleportBackToSpawnOnY set to " + y + "!");
               player.sendMessage("");
            } else {
               player.sendMessage(ChatColor.RED + "TeleportBackToSpawnOnY not set");
               player.sendMessage("");
            }
         }
      }

      currentSetupStep++;
      nextSetupStepMessage();
   }

   public void stopSetup() {
      if (!isSetupRunning) {
         plugin.getLogger().warning("Setup is not running!");
         return;
      }
      currentSetupStep = 0;

      isSetupRunning = false;
      HandlerList.unregisterAll(chatListener);
   }

   private BukkitTask displayCurrentPosition(String message, Boolean onlyHeight) {
      return new BukkitRunnable() {
         @Override
         public void run() {
            if (onlyHeight) {
               player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GOLD + message + ChatColor.BOLD + ChatColor.BLUE + player.getLocation().getBlockY()));
            } else {
               player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GOLD + message + ChatColor.BOLD + ChatColor.BLUE + " " + player.getLocation().getBlockX() + " " + player.getLocation().getBlockY() + " " + player.getLocation().getBlockZ()));
            }
         }
      }.runTaskTimer(plugin, 0, 5);
   }
}
