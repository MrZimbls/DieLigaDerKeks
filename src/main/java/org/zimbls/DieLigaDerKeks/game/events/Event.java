package org.zimbls.DieLigaDerKeks.game.events;

import org.bukkit.entity.Player;
import org.zimbls.DieLigaDerKeks.stateMachine.GameStateMachine;

public abstract class Event {

   protected GameStateMachine state;
   protected int minPlayers;
   protected int maxPlayers;

   public Event(GameStateMachine state) {
      this.state = state;
   }

   public boolean isPossibleForPlayercount(int p) {
      if (p >= minPlayers && p <= maxPlayers) {
         return true;
      }
      return false;
   }

   public abstract void runEvent();
   public abstract String getDescription();
}
