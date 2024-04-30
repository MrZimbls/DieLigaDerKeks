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
import org.zimbls.DieLigaDerKeks.util.LanguagePreferencesBasedProperties;

import java.util.ArrayList;

public class voteYesOrNo extends Menu {

    public voteYesOrNo(PlayerGuiData playerGuiData) {
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
        String eventTitle = playerGuiData.getGame().getActiveEvent().getTitle();

        switch (itemName) {
            case "YES" -> {
                playerGuiData.getGame().setEventVote(p, true);
                p.closeInventory();
                p.sendMessage("You voted " + ChatColor.GREEN + "YES" + ChatColor.RESET + " for the event " + ChatColor.GOLD + eventTitle + ChatColor.RESET + "!");
            }
            case "NO" -> {
                playerGuiData.getGame().setEventVote(p, false);
                p.closeInventory();
                p.sendMessage("You voted " + ChatColor.RED + "NO" + ChatColor.RESET + " for the event " + ChatColor.GOLD + eventTitle + ChatColor.RESET + "!");
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
        String propertyName = "event.description." + playerGuiData.getGame().getActiveEvent().getTitle();
        String description;

        try {
            description = LanguagePreferencesBasedProperties.getProperty(playerGuiData.getGuiHolder().getUniqueId(), propertyName);
        } catch (Exception e) {
            description = "Could not find description for event.";
        }
        lore.add(ChatColor.GRAY + description);
        infoMeta.setLore(lore);
        info.setItemMeta(infoMeta);
        inventory.setItem(4, info);

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
