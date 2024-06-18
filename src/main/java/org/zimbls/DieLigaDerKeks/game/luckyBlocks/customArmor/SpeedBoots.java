package org.zimbls.DieLigaDerKeks.game.luckyBlocks.customArmor;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.zimbls.DieLigaDerKeks.game.Participant;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.CustomArmor;

public class SpeedBoots extends CustomArmor {
    private PotionEffect speedEffekt = new PotionEffect(PotionEffectType.SPEED, -1, 3);

    @Override
    public String getIdentifier() {
        return "speed_boots";
    }

    @Override
    public ItemStack getItemStack() {
        ItemStack item = new ItemStack(Material.LEATHER_BOOTS);
        ItemMeta meta = super.getBaseItemMeta(item.getType());
        meta.setDisplayName(ChatColor.RED + ChatColor.BOLD.toString() + "Speeeeed Boots");
        meta.setLore(java.util.Arrays.asList(ChatColor.GRAY + "Gotta go fast!"));
        meta.addEnchant(Enchantment.FROST_WALKER, 2, true);
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) meta;
        leatherArmorMeta.setColor(Color.fromRGB(173, 216, 230));
        item.setItemMeta(leatherArmorMeta);
        return item;
    }

    @Override
    public void wearEffect(Participant wearer) {
        wearer.getPlayer().addPotionEffect(speedEffekt);
    }

    @Override
    public void onUnequipped(Participant wearer) {
        wearer.getPlayer().removePotionEffect(speedEffekt.getType());
    }
}
