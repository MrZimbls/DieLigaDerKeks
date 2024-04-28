package org.zimbls.DieLigaDerKeks.listener;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.zimbls.DieLigaDerKeks.game.Participant;
import org.zimbls.DieLigaDerKeks.stateMachine.GameState;
import org.zimbls.DieLigaDerKeks.stateMachine.GameStateMachine;
import org.zimbls.DieLigaDerKeks.util.Mob;

public class PlayerDeathListener implements Listener {
   private GameStateMachine state;

   public PlayerDeathListener(GameStateMachine state) {
      this.state = state;
   }

   @EventHandler
   public void onPlayerDeath(PlayerDeathEvent event) {
      Player player = event.getEntity();
      if (state.getState() != GameState.RUNNING) {
         return;
      }
      Participant participant = state.getGame().getParticipantByName(player.getName());
      if (participant != null) { // Check if the game is running
         player.setGameMode(GameMode.SPECTATOR); // Set the player's game mode to Spectator
         participant.setDead(true);
         if (player.getKiller() != null) {
            Participant killer = state.getGame().getParticipantByName(player.getKiller().getName());
            if (killer != null) {
               Mob mob = state.getGame().getMobFormMobName("Player");
               killer.addIfNewMob(mob);
               player.getWorld().dropItemNaturally(player.getLocation(), mob.getReward());
               String message = "+" + mob.getPoints() + " for " + mob.getName();
               killer.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
            }
         }
      }
   }
}
