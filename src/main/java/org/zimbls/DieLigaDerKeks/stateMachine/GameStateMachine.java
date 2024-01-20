package org.zimbls.DieLigaDerKeks.stateMachine;

import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.zimbls.DieLigaDerKeks.game.Game;
import org.zimbls.DieLigaDerKeks.game.GameScoreboard;

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

   public void startGame(World lobbyMap, JavaPlugin plugin) {
      currentState = GameState.STARTING;
      game = new Game(lobbyMap, plugin);
      game.createGameWorld();
   }

   public void runGame() {
      currentState = GameState.RUNNING;
      game.clearScoreboards();
      game.teleportAllPlayersToGameMap();
      game.continueTimer();
      game.setGameScoreboard();
   }

   public void pauseGame() {
      currentState = GameState.PAUSED;
      game.setAllLastGameLocations();
      game.clearScoreboards();
      game.teleportAllPlayersToLobbyMap();
      game.pauseTimer();
   }

   public void triggerEvent() {
      currentState = GameState.EVENT;
   }

   public void stopGame() {
      currentState = GameState.STOPPED;
   }
}
