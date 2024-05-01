package org.zimbls.DieLigaDerKeks.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
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
         if (state.getGame().getParticipatingPlayers().contains(player)) {
            player.sendMessage(ChatColor.GOLD + "Welcome back!");
            if (state.getState() == GameState.RUNNING) {
               state.getGame().getParticipantByName(player.getName()).teleportToGameMap(state.getGame().getGameWorld());
            } else if (state.getState() == GameState.EVENT || state.getState() == GameState.PAUSED) {
               state.getGame().getParticipantByName(player.getName()).teleportToLobbyMap(state.getGame().getLobbyMap());
            }
         }
      }
   }

   @EventHandler
   public void onPlayerQuit(PlayerQuitEvent event) {
      Player player = event.getPlayer();
      if (state.getState() == GameState.RUNNING) {
         if (state.getGame().getParticipatingPlayers().contains(player)) {
            state.getGame().getParticipantByName(player.getName()).setLastGameLocation();
         }
      }
   }
}
