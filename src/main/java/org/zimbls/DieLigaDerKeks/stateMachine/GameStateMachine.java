package org.zimbls.DieLigaDerKeks.stateMachine;

import org.bukkit.World;
import org.zimbls.DieLigaDerKeks.game.Game;

public class GameStateMachine {
   private GameState currentState;
   private Game game;

   public GameStateMachine() {
      currentState = GameState.STOPPED; // Initial state
   }

   public Game getGame() {
      return game;
   }

   public GameState getState() {
      return currentState;
   }

   public void startGame(World lobbyMap) {
      currentState = GameState.STARTING;
      game = new Game(lobbyMap);
      game.createGameWorld();
   }

   public void runGame() {
      currentState = GameState.RUNNING;
      game.teleportAllPlayersToGameMap();
   }

   public void pauseGame() {
      currentState = GameState.PAUSED;
      game.setAllLastGameLocations();
      game.teleportAllPlayersToLobbyMap();
   }

   public void triggerEvent() {
      currentState = GameState.EVENT;
   }

   public void stopGame() {
      currentState = GameState.STOPPED;
   }
}
