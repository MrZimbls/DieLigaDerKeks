package org.zimbls.DieLigaDerKeks.menue;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.zimbls.DieLigaDerKeks.util.CustomSkulls;

import java.util.ArrayList;

public class FilterTemperItem {
   private String selectedFilter = "All";

   public ItemStack getFilterItem() {
      ItemStack filterItem;
      SkullMeta filterItemMeta;
      ArrayList<String> lore;

      switch (selectedFilter) {
         case "All":
            filterItem = CustomSkulls.GRAY_F.getSkull();
            filterItemMeta = (SkullMeta) filterItem.getItemMeta();
            filterItemMeta.setDisplayName(ChatColor.YELLOW + "All tempers");
            lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Filter by temper");
            filterItemMeta.setLore(lore);
            filterItem.setItemMeta(filterItemMeta);
            return filterItem;
         case "Passive":
            filterItem = CustomSkulls.GREEN_HEART.getSkull();
            filterItemMeta = (SkullMeta) filterItem.getItemMeta();
            filterItemMeta.setDisplayName(ChatColor.YELLOW + "Passive");
            lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Filter by temper");
            filterItemMeta.setLore(lore);
            filterItem.setItemMeta(filterItemMeta);
            return filterItem;
         case "Neutral":
            filterItem = CustomSkulls.GOLDEN_EXCLAMATION.getSkull();
            filterItemMeta = (SkullMeta) filterItem.getItemMeta();
            filterItemMeta.setDisplayName(ChatColor.YELLOW + "Neutral");
            lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Filter by temper");
            filterItemMeta.setLore(lore);
            filterItem.setItemMeta(filterItemMeta);
            return filterItem;
         case "Hostile":
            filterItem = CustomSkulls.SWORD.getSkull();
            filterItemMeta = (SkullMeta) filterItem.getItemMeta();
            filterItemMeta.setDisplayName(ChatColor.YELLOW + "Hostile");
            lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Filter by temper");
            filterItemMeta.setLore(lore);
            filterItem.setItemMeta(filterItemMeta);
            return filterItem;
         case "Boss":
            filterItem = CustomSkulls.SKULL.getSkull();
            filterItemMeta = (SkullMeta) filterItem.getItemMeta();
            filterItemMeta.setDisplayName(ChatColor.YELLOW + "Boss");
            lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Filter by temper");
            filterItemMeta.setLore(lore);
            filterItem.setItemMeta(filterItemMeta);
            return filterItem;
      }
      return null;
   }

   public void nextFilter() {
      switch (selectedFilter) {
         case "All":
            selectedFilter = "Passive";
            break;
         case "Passive":
            selectedFilter = "Neutral";
            break;
         case "Neutral":
            selectedFilter = "Hostile";
            break;
         case "Hostile":
            selectedFilter = "Boss";
            break;
         case "Boss":
            selectedFilter = "All";
            break;
      }
   }

   public String getSelectedFilter() {
      return selectedFilter;
   }
}
