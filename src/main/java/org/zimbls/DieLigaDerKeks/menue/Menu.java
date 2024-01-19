package org.zimbls.DieLigaDerKeks.menue;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public abstract class Menu implements InventoryHolder {
    protected PlayerGuiData playerGuiData;
    protected Inventory inventory;
    protected ItemStack FILLER_GLASS = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);

    public Menu(PlayerGuiData playerGuiData){
        this.playerGuiData = playerGuiData;
    }

    public abstract String getGuiName();

    public abstract int getRows();

    public abstract void handleGui(InventoryClickEvent e);

    public abstract void setGuiItems();

    public void open(){
        inventory = Bukkit.createInventory(this, getRows()*9, getGuiName());
        this.setGuiItems();
        playerGuiData.getGuiHolder().openInventory(inventory);
    }

    public void setFillerGlass(){
        for (int i = 0; i < getSlots(); i++) {
            if (inventory.getItem(i) == null){
                inventory.setItem(i, FILLER_GLASS);
            }
        }
    }


    public ItemStack makeItem(Material material, String displayName, String... lore) {

        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(displayName);

        itemMeta.setLore(Arrays.asList(lore));
        item.setItemMeta(itemMeta);

        return item;
    }

    public int getSlots(){
        return getRows()*9;
    }


}
