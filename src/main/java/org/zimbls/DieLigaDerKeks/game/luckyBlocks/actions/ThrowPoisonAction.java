package org.zimbls.DieLigaDerKeks.game.luckyBlocks.actions;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.ActionRarity;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.LuckyBlockAction;

import java.util.Random;

public class ThrowPoisonAction extends LuckyBlockAction {

    private final Random random = new Random();

    @Override
    public void run() {
        Location location = super.playerLuckyBlockData.getBlockLocation();
        World world = location.getWorld();

        ItemStack potionItem = new ItemStack(Material.LINGERING_POTION);
        PotionMeta potionMeta = (PotionMeta) potionItem.getItemMeta();

        if (potionMeta != null) {
            potionMeta.addCustomEffect(new PotionEffect(PotionEffectType.POISON, 20 * 30, 1), true);
            potionItem.setItemMeta(potionMeta);
        }

        assert world != null;
        ThrownPotion thrownPotion = world.spawn(location, ThrownPotion.class);
        thrownPotion.setItem(potionItem);
        Vector velocity = new Vector(0, 1, 0);
        thrownPotion.setVelocity(velocity);
    }

    @Override
    public ActionRarity getRarity() {
        return ActionRarity.COMMON;
    }

    @Override
    public String getDisplayName() {
        return "Poison";
    }
}
