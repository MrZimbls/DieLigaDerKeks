package org.zimbls.DieLigaDerKeks.game;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.zimbls.DieLigaDerKeks.game.Participant;
import org.zimbls.DieLigaDerKeks.stateMachine.GameStateMachine;

public class GameMap {

   private World gameWorld;

   public GameMap(){
      String worldName = "GameWorld"; // Choose a unique name for your world
      WorldCreator worldCreator = new WorldCreator(worldName);
      this.gameWorld = worldCreator.createWorld();
      setWorldBorder(6000);
   }

   public World getGameWorld() {
      return gameWorld;
   }

   public void setWorldBorder(int size){
      WorldBorder border = gameWorld.getWorldBorder();
      border.setCenter(gameWorld.getSpawnLocation());
      border.setSize(size);

      border.setDamageAmount(0.1); // Damage per second per block outside border
      border.setDamageBuffer(5); // Distance a player can safely be outside the border
      border.setWarningDistance(10); // Distance a player will be warned when near the border
      border.setWarningTime(15); // Time in seconds a player will be warned when near the border

   }
}
