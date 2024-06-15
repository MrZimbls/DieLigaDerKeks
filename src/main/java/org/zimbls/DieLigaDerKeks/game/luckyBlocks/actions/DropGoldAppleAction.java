package org.zimbls.DieLigaDerKeks.game.luckyBlocks.actions;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.ActionRarity;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.LuckyBlockAction;

import java.util.Objects;

public class DropGoldAppleAction extends LuckyBlockAction {
    @Override
    public void run() {
        super.playerLuckyBlockData.getWorld().dropItemNaturally(super.playerLuckyBlockData.getBlockLocation(), new org.bukkit.inventory.ItemStack(Material.GOLDEN_APPLE, 5));
        Location blockLocation = super.playerLuckyBlockData.getBlockLocation();
        Objects.requireNonNull(blockLocation.getWorld()).playSound(blockLocation, Sound.BLOCK_DEEPSLATE_BREAK, 1.0f, 1.0f);
    }

    @Override
    public ActionRarity getRarity() {
        return ActionRarity.UNCOMMON;
    }

    @Override
    public String getDisplayName() {
        return "Drop Gold Apple";
    }
}
