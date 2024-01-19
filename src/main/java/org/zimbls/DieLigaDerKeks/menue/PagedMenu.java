package org.zimbls.DieLigaDerKeks.menue;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.zimbls.DieLigaDerKeks.util.CustomSkulls;

import java.util.ArrayList;

public abstract class PagedMenu extends Menu{

    protected int page = 0;
    protected int maxItemsPerPage = 28;
    protected int index = 0;

    public PagedMenu(PlayerGuiData playerGuiData) {
        super(playerGuiData);
    }

    public void addMenuBorder(){

        ItemStack left = CustomSkulls.BLUE_LEFT.getSkull();
        ItemMeta leftMeta = left.getItemMeta();
        leftMeta.setDisplayName(ChatColor.BLUE + "Previous");
        left.setItemMeta(leftMeta);
        inventory.setItem(48, left);

        inventory.setItem(49, makeItem(Material.BARRIER, ChatColor.DARK_RED + "Close"));

        ItemStack right = CustomSkulls.BLUE_RIGHT.getSkull();
        ItemMeta rightMeta = right.getItemMeta();
        rightMeta.setDisplayName(ChatColor.BLUE + "Next");
        right.setItemMeta(rightMeta);
        inventory.setItem(50, right);

        if (playerGuiData.getParticipant() != null) {
            ItemStack points = CustomSkulls.REDSTONE_P.getSkull();
            ItemMeta pointsMeta = points.getItemMeta();
            pointsMeta.setDisplayName(ChatColor.GREEN + String.valueOf(playerGuiData.getParticipant().getPoints()));
            ArrayList<String> lore = new ArrayList<>();
            pointsMeta.setLore(lore);
            lore.add(ChatColor.DARK_GRAY + "Points");
            points.setItemMeta(pointsMeta);
            inventory.setItem(4, points);
        }

        for (int i = 0; i < 10; i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, super.FILLER_GLASS);
            }
        }

        inventory.setItem(17, super.FILLER_GLASS);
        inventory.setItem(18, super.FILLER_GLASS);
        inventory.setItem(26, super.FILLER_GLASS);
        inventory.setItem(27, super.FILLER_GLASS);
        inventory.setItem(35, super.FILLER_GLASS);
        inventory.setItem(36, super.FILLER_GLASS);

        for (int i = 44; i < 54; i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, super.FILLER_GLASS);
            }
        }
    }

    public int getMaxItemsPerPage() {
        return maxItemsPerPage;
    }
}
