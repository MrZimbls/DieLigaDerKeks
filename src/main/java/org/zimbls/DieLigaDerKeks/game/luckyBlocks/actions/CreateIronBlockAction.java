package org.zimbls.DieLigaDerKeks.game.luckyBlocks.actions;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.ActionRarity;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.LuckyBlockAction;

import java.util.Objects;

public class CreateIronBlockAction extends LuckyBlockAction {
    @Override
    public void run() {
        Location blockLocation = super.playerLuckyBlockData.getBlockLocation();
        blockLocation.getBlock().setType(Material.IRON_BLOCK);
        Objects.requireNonNull(blockLocation.getWorld()).playSound(blockLocation, Sound.BLOCK_DEEPSLATE_BRICKS_PLACE, 1.0f, 1.0f);
    }

    @Override
    public ActionRarity getRarity() {
        return ActionRarity.UNCOMMON;
    }

    @Override
    public String getDisplayName() {
        return "Create Iron Block";
    }
}
