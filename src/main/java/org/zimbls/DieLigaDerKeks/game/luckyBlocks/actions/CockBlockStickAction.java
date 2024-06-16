package org.zimbls.DieLigaDerKeks.game.luckyBlocks.actions;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.ActionRarity;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.LuckyBlockAction;

import java.util.Objects;

public class CockBlockStickAction extends LuckyBlockAction {
    @Override
    public void run() {
        Location location = super.playerLuckyBlockData.getBlockLocation();
        ItemStack itemStack = new ItemStack(org.bukkit.Material.STICK);
        ItemMeta itemMeta = itemStack.getItemMeta();

        assert itemMeta != null;
        itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Cock " + ChatColor.RED + "Block");
        itemMeta.addEnchant(Enchantment.KNOCKBACK, 10, true);
        itemMeta.addEnchant(Enchantment.FIRE_ASPECT, 1, true);
        itemStack.setItemMeta(itemMeta);

        Objects.requireNonNull(location.getWorld()).dropItemNaturally(location, itemStack);
    }

    @Override
    public ActionRarity getRarity() {
        return ActionRarity.RARE;
    }

    @Override
    public String getDisplayName() {
        return "Cock Block Stick";
    }
}
