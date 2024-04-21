package org.zimbls.DieLigaDerKeks.menue.gui;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.zimbls.DieLigaDerKeks.menue.Menu;
import org.zimbls.DieLigaDerKeks.menue.PlayerGuiData;
import org.zimbls.DieLigaDerKeks.util.CustomSkulls;

import java.util.ArrayList;

public class voteYesOrNo extends Menu {

   public voteYesOrNo(PlayerGuiData playerGuiData){
      super(playerGuiData);
   }

   @Override
   public String getGuiName() {
      return "Vote";
   }

   @Override
   public int getRows() {
      return 2;
   }

   @Override
   public void handleGui(InventoryClickEvent e) {
      String itemName = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).toUpperCase();
      Player p = (Player) e.getWhoClicked();

      switch (itemName) {
         case "YES" -> {
            playerGuiData.getGame().setEventVote(p, true);
            p.closeInventory();
         }
         case "NO" -> {
            playerGuiData.getGame().setEventVote(p, false);
            p.closeInventory();
         }
      }
   }

   @Override
   public void setGuiItems() {
      setFillerGlass();

      ItemStack info = CustomSkulls.YELLOW_INFO.getSkull();
      ItemMeta infoMeta = info.getItemMeta();
      infoMeta.setDisplayName(ChatColor.YELLOW + "Info:");
      ArrayList<String> lore = new ArrayList<>();
      lore.add(ChatColor.GRAY + playerGuiData.getGame().getActivEvent().getDescription());
      infoMeta.setLore(lore);
      info.setItemMeta(infoMeta);
      inventory.setItem(12, info);

      ItemStack yes = CustomSkulls.GREEN_CHECK.getSkull();
      ItemMeta yesMeta = yes.getItemMeta();
      yesMeta.setDisplayName(ChatColor.GREEN + ChatColor.BOLD.toString() + "YES");
      yes.setItemMeta(yesMeta);
      inventory.setItem(12, yes);

      ItemStack no = CustomSkulls.RED_CROSS.getSkull();
      ItemMeta noMeta = no.getItemMeta();
      noMeta.setDisplayName(ChatColor.RED + ChatColor.BOLD.toString() + "NO");
      no.setItemMeta(noMeta);
      inventory.setItem(14, no);
   }

   @Override
   public Inventory getInventory() {
      return null;
   }
}
