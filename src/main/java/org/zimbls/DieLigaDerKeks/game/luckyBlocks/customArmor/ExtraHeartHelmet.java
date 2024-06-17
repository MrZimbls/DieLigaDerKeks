package org.zimbls.DieLigaDerKeks.game.luckyBlocks.customArmor;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.zimbls.DieLigaDerKeks.game.Participant;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.CustomArmor;

public class ExtraHeartHelmet extends CustomArmor {

    @Override
    public String getIdentifier() {
        return "extra_heart_helmet";
    }

    @Override
    public ItemStack getItemStack() {
        ItemStack item = new ItemStack(Material.IRON_HELMET);
        ItemMeta meta = super.getBaseItemMeta(item.getType());
        meta.setDisplayName(ChatColor.RED + ChatColor.BOLD.toString() + "Extra Heart Helmet");
        meta.setLore(java.util.Arrays.asList(ChatColor.GRAY + "Gives you 3 extra heart!"));
        item.setItemMeta(meta);
        return item;
    }

    @Override
    public void wearEffect(Participant wearer) {
        AttributeInstance maxHealth = wearer.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (maxHealth != null) {
            maxHealth.setBaseValue(maxHealth.getValue() + 6);
        }
    }

    @Override
    public void onUnequipped(Participant wearer) {
        AttributeInstance maxHealth = wearer.getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (maxHealth != null) {
            maxHealth.setBaseValue(maxHealth.getValue() - 6);
        }
    }
}
