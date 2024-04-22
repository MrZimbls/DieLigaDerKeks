package org.zimbls.DieLigaDerKeks.menue.gui;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.zimbls.DieLigaDerKeks.menue.PagedMenu;
import org.zimbls.DieLigaDerKeks.menue.PlayerGuiData;
import org.zimbls.DieLigaDerKeks.util.Mob;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class UnCollectedList extends PagedMenu {

   private Mob[] unkilledMobs;

   public UnCollectedList(PlayerGuiData playerGuiData){
      super(playerGuiData);
   }

   @Override
   public String getGuiName() {
      return "Points";
   }

   @Override
   public int getRows() {
      return 6;
   }

   @Override
   public void handleGui(InventoryClickEvent e) {
      Player player = (Player) e.getWhoClicked();

      if (e.getCurrentItem().getType().equals(Material.PLAYER_HEAD)) {
         if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Previous")){
            if (page == 0){
               player.sendMessage(ChatColor.GRAY + "You are already on the first page.");
            }else{
               page = page - 1;
               super.open();
            }
         }else if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Next")){
            if (!((index + 1) >= unkilledMobs.length)){
               page = page + 1;
               super.open();
            }else{
               player.sendMessage(ChatColor.GRAY + "You are on the last page.");
            }
         }
      }else if (e.getCurrentItem().getType().equals(Material.BARRIER)) {
         //close inventory
         player.closeInventory();
      }
   }

   @Override
   public void setGuiItems() {
      addMenuBorder();

      Set<Mob> mobs = new HashSet<>(playerGuiData.getGame().getMobMap().values());
      Set<Mob> unkilledMobsSet = new HashSet<>();
      for (Mob mob:mobs) {
         if (!playerGuiData.getParticipant().getKilledMobs().contains(mob)){
            unkilledMobsSet.add(mob);
         }
      }


      this.unkilledMobs = unkilledMobsSet.toArray(Mob[]::new);

      for (int i = 0; i < getMaxItemsPerPage(); i++) {
         index = getMaxItemsPerPage() * page + i;

         if (index >= unkilledMobs.length) break;

         //MOB ITEM _____________________
         ItemStack mobItem = unkilledMobs[index].getHead();
         SkullMeta mobItemMeta = (SkullMeta) mobItem.getItemMeta();

         mobItemMeta.setDisplayName(ChatColor.GOLD + unkilledMobs[index].getName());

         ArrayList<String> lore = new ArrayList<>();
         lore.add(ChatColor.DARK_AQUA + String.valueOf(unkilledMobs[index].getPoints()) + " Points");
         mobItemMeta.setLore(lore);

         mobItem.setItemMeta(mobItemMeta);
         inventory.addItem(mobItem);
         index++;
         //MOB ITEM END _________________
      }
   }

   @Override
   public Inventory getInventory() {
      return null;
   }
}
