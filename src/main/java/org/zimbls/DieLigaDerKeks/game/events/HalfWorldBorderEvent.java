package org.zimbls.DieLigaDerKeks.game.events;

import org.zimbls.DieLigaDerKeks.stateMachine.GameStateMachine;

public class HalfWorldBorderEvent extends Event{

   public HalfWorldBorderEvent(GameStateMachine state) {
      super(state);
      super.minPlayers = 2;
      super.maxPlayers = 99;
   }
   @Override
   public void runEvent() {
      System.out.println("Running HalfWorldBorder Event!");

      super.state.getGame().getGameWorld().halfWorldBorder(600);
   }

   @Override
   public String getTitle() {
      return "HalfWorldBorder";
   }
}
