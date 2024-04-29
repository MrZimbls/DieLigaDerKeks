package org.zimbls.DieLigaDerKeks.game;

import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.WorldCreator;

public class GameMap {

   private World gameWorld;
   private int worldBorderSize = 6000;

   public GameMap(){
      String worldName = "GameWorld"; // Choose a unique name for your world
      WorldCreator worldCreator = new WorldCreator(worldName);
      this.gameWorld = worldCreator.createWorld();
      setWorldBorder(worldBorderSize);
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

   public void halfWorldBorder(int delay){
      WorldBorder border = gameWorld.getWorldBorder();
      worldBorderSize = worldBorderSize / 2;
      border.setSize(worldBorderSize, delay);
   }
}
