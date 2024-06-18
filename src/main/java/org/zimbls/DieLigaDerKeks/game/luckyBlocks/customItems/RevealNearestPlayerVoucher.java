package org.zimbls.DieLigaDerKeks.game.luckyBlocks.customItems;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.zimbls.DieLigaDerKeks.game.Game;

import java.util.Collections;

public class RevealNearestPlayerVoucher {
    ItemStack itemStack;

    public RevealNearestPlayerVoucher () {
        itemStack = new ItemStack(Material.PAPER);
        ItemMeta meta = itemStack.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(ChatColor.AQUA + "Reveal Nearest Player Location Voucher");
            meta.setLore(Collections.singletonList(ChatColor.GRAY + "Right click to reveal the nearest player"));
            meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.getPersistentDataContainer().set(Game.CUSTOM_ITEM_KEY, PersistentDataType.STRING, "reveal_nearest_player_voucher");
            itemStack.setItemMeta(meta);
        }
    }

    public ItemStack getItemStack (int amount) {
        ItemStack stack = itemStack.clone();
        stack.setAmount(amount);
        return stack;
    }
}
