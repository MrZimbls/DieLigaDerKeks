package org.zimbls.DieLigaDerKeks.game.luckyBlocks;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.zimbls.DieLigaDerKeks.game.Game;
import org.zimbls.DieLigaDerKeks.game.Participant;

public abstract class CustomArmor{
    public ItemMeta getBaseItemMeta(Material material){
        ItemMeta meta = new ItemStack(material).getItemMeta();
        assert meta != null;
        meta.getPersistentDataContainer().set(Game.CUSTOM_ARMOR_KEY, PersistentDataType.STRING, this.getIdentifier());
        return meta;
    }
    public abstract String getIdentifier();
    public abstract ItemStack getItemStack();
    public abstract void wearEffect(Participant wearer);
    public abstract void onUnequipped(Participant wearer);
}
