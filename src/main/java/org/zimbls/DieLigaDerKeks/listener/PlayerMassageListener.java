package org.zimbls.DieLigaDerKeks.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.zimbls.DieLigaDerKeks.util.SetupProccessHandler;

public class PlayerMassageListener implements Listener {
   private Player setupPlayer;
   private SetupProccessHandler setupProcessHandler;

   public PlayerMassageListener(Player player, SetupProccessHandler setupProcessHandler) {
      this.setupPlayer = player;
      this.setupProcessHandler = setupProcessHandler;
   }

   @EventHandler
   public void onPlayerMassage(AsyncPlayerChatEvent event) {
      if (event.getPlayer() == setupPlayer) {
         setupProcessHandler.executeSetupStep(event.getMessage());
         event.setCancelled(true);
      }
   }
}
