package org.zimbls.DieLigaDerKeks.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Mob {
   private String name;
   private int points;
   private ItemStack reward;

   public Mob(String name,int points, String reward, int rewardAmount){
      this.name = name;
      this.points = points;

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
}
