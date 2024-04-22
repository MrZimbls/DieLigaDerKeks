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

   public boolean isPossibleForPlayerCount(int p) {
      return p >= minPlayers && p <= maxPlayers;
   }

   public abstract void runEvent();
   public abstract String getDescription();
   public abstract String getTitle();
}
