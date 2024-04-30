package org.zimbls.DieLigaDerKeks.menue;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.zimbls.DieLigaDerKeks.util.CustomSkulls;

import java.util.ArrayList;

public class FilterWorldItem {
   private String selectedFilter = "All";

   public ItemStack getFilterItem() {
      ItemStack filterItem;
      SkullMeta filterItemMeta;
      ArrayList<String> lore;

      switch (selectedFilter) {
         case "All":
            filterItem = CustomSkulls.GRAY_F.getSkull();
            filterItemMeta = (SkullMeta) filterItem.getItemMeta();
            filterItemMeta.setDisplayName(ChatColor.YELLOW + "All Mobs");
            lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Filter by spawn World");
            filterItemMeta.setLore(lore);
            filterItem.setItemMeta(filterItemMeta);
            return filterItem;
         case "Overworld":
            filterItem = CustomSkulls.GRASS_BLOCK.getSkull();
            filterItemMeta = (SkullMeta) filterItem.getItemMeta();
            filterItemMeta.setDisplayName(ChatColor.YELLOW + "Overworld");
            lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Filter by spawn World");
            filterItemMeta.setLore(lore);
            filterItem.setItemMeta(filterItemMeta);
            return filterItem;
         case "Nether":
            filterItem = CustomSkulls.NETHERRACK.getSkull();
            filterItemMeta = (SkullMeta) filterItem.getItemMeta();
            filterItemMeta.setDisplayName(ChatColor.YELLOW + "Nether");
            lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Filter by spawn World");
            filterItemMeta.setLore(lore);
            filterItem.setItemMeta(filterItemMeta);
            return filterItem;
         case "End":
            filterItem = CustomSkulls.ENDSTONE.getSkull();
            filterItemMeta = (SkullMeta) filterItem.getItemMeta();
            filterItemMeta.setDisplayName(ChatColor.YELLOW + "End");
            lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Filter by spawn World");
            filterItemMeta.setLore(lore);
            filterItem.setItemMeta(filterItemMeta);
            return filterItem;
      }
      return null;
   }

   public void nextFilter() {
      switch (selectedFilter) {
         case "All":
            selectedFilter = "Overworld";
            break;
         case "Overworld":
            selectedFilter = "Nether";
            break;
         case "Nether":
            selectedFilter = "End";
            break;
         case "End":
            selectedFilter = "All";
            break;
      }
   }

   public String getSelectedFilter() {
      return selectedFilter;
   }
}
