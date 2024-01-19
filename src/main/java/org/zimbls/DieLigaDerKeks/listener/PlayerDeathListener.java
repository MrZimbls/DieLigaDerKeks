package org.zimbls.DieLigaDerKeks.listener;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.zimbls.DieLigaDerKeks.game.Participant;
import org.zimbls.DieLigaDerKeks.stateMachine.GameState;
import org.zimbls.DieLigaDerKeks.stateMachine.GameStateMachine;

public class PlayerDeathListener implements Listener {
   private GameStateMachine state;

   public PlayerDeathListener(GameStateMachine state) {
      this.state = state;
   }

   @EventHandler
   public void onPlayerDeath(PlayerDeathEvent event) {
      Player player = event.getEntity();
      Participant participant = state.getGame().getParticipantByName(player.getName());
      if (state.getState() == GameState.RUNNING && participant != null) { // Check if the game is running
         player.setGameMode(GameMode.SPECTATOR); // Set the player's game mode to Spectator
         participant.setDead(true);
      }
   }
}
