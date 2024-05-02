package org.zimbls.DieLigaDerKeks.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.zimbls.DieLigaDerKeks.game.Participant;
import org.zimbls.DieLigaDerKeks.stateMachine.GameState;
import org.zimbls.DieLigaDerKeks.stateMachine.GameStateMachine;

public class PlayerEventListener implements Listener {
   private GameStateMachine state;

   public PlayerEventListener(GameStateMachine state) {
      this.state = state;
   }

   @EventHandler
   public void onPlayerJoin(PlayerJoinEvent event) {
      Player player = event.getPlayer();
      if (state.getState() != GameState.STOPPED) {
         Participant participant = state.getGame().getParticipantByName(player.getName());
         if (participant != null) {
            player.sendMessage(ChatColor.GOLD + "Welcome back!");
            if (state.getState() == GameState.RUNNING) {
               participant.teleportToGameMap(state.getGame().getGameWorld());
               participant.setScoreboard();
            } else if (state.getState() == GameState.EVENT || state.getState() == GameState.PAUSED) {
               state.getGame().getParticipantByName(player.getName()).teleportToLobbyMap(state.getGame().getLobbyMap());
            }
         }
      }
   }

   @EventHandler
   public void onPlayerQuit(PlayerQuitEvent event) {
      System.out.println("PlayerQuitEvent triggered!");
      Player player = event.getPlayer();
      if (state.getState() == GameState.RUNNING) {
         if (state.getGame().getParticipantByName(player.getName()) != null) {
            state.getGame().getParticipantByName(player.getName()).setLastGameLocation();
         }
      }
   }
}
