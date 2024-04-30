package org.zimbls.DieLigaDerKeks.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Mob {
   private String name;
   private int points;
   private ItemStack reward;
   private ItemStack head;
   private String spawnWorld;
   private String temper;

   public Mob(String name,int points, String reward, int rewardAmount, ItemStack head, String spawnWorld, String temper){
      this.name = name;
      this.points = points;
      this.head = head;
      this.spawnWorld = spawnWorld;
      this.temper = temper;

      String itemName = reward.substring("minecraft:".length()).toUpperCase();
      Material material = Material.matchMaterial(itemName);
      if (material != null) {
         this.reward = new ItemStack(material);
         this.reward.setAmount(rewardAmount);
      }
   }

   public String getName() {
      return name;
   }

   public int getPoints() {
      return points;
   }

   public ItemStack getReward() {
      return reward;
   }

   public ItemStack getHead() {
      return head;
   }

   public String getSpawnWorld() {
      return spawnWorld;
   }

   public String getTemper() {
      return temper;
   }
}
