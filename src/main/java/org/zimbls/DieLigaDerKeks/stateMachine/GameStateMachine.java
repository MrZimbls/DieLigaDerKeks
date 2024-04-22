package org.zimbls.DieLigaDerKeks.stateMachine;

import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.zimbls.DieLigaDerKeks.game.EventScoreboard;
import org.zimbls.DieLigaDerKeks.game.Game;
import org.zimbls.DieLigaDerKeks.game.GameScoreboard;
import org.zimbls.DieLigaDerKeks.game.events.Event;
import org.zimbls.DieLigaDerKeks.game.events.RandomPlayerTp;
import org.zimbls.DieLigaDerKeks.util.EventTimer;
import org.zimbls.DieLigaDerKeks.util.TimerTask;

import java.util.ArrayList;
import java.util.Collections;

public class GameStateMachine {
   private GameState currentState;
   private Game game;
   private ArrayList<Event> availableEvents = new ArrayList<Event>();
   private JavaPlugin plugin;

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
      game = new Game(lobbyMap, plugin, this);
      game.createGameWorld();
      availableEvents.add(new RandomPlayerTp(this));
      this.plugin = plugin;
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
      game.setAllLastGameLocations();
      game.clearScoreboards();
      game.teleportAllPlayersToLobbyMap();
      game.pauseTimer();
      game.setEventVotesClosed(false);

      ArrayList<Event> possibleEvents = new ArrayList<Event>();
      for (Event event:availableEvents) {
         if (event.isPossibleForPlayerCount(game.getNumberOfPlayersAlive())) {
            possibleEvents.add(event);
         }
         possibleEvents.add(event); //TODO remove only for testing
      }

      Collections.shuffle(possibleEvents);
      game.setActivEvent(possibleEvents.get(0));

      EventScoreboard eventScoreboard = new EventScoreboard();
      game.setEventScoreboard(eventScoreboard);
      EventTimer eventTask = new EventTimer(eventScoreboard, this);
      eventTask.runTaskTimer(plugin, 0L, 20L);
   }

   public void endEvent(boolean runEvent) {
      if (runEvent) {
         game.getActivEvent().runEvent();
      }
      runGame();
   }

   public void stopGame() {
      currentState = GameState.STOPPED;
   }
}
