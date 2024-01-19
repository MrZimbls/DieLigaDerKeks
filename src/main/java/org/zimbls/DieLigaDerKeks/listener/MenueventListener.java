package org.zimbls.DieLigaDerKeks.listener;

import org.zimbls.DieLigaDerKeks.menue.Menu;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;

public class MenueventListener implements Listener {

    @EventHandler
    public void onMenuclick(InventoryClickEvent e){

        InventoryHolder inventoryHolder = e.getInventory().getHolder();
        if (inventoryHolder instanceof Menu) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null){
                return;
            }
            Menu menu = (Menu) inventoryHolder;
            menu.handleGui(e);
        }
    }
}
