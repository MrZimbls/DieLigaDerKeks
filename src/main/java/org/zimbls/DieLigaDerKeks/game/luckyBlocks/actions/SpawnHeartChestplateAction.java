package org.zimbls.DieLigaDerKeks.game.luckyBlocks.actions;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.ActionRarity;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.LuckyBlockAction;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.customArmor.ExtraHeartChestplate;

import java.util.Objects;

public class SpawnHeartChestplateAction extends LuckyBlockAction {
    @Override
    public void run() {
        Location blockLocation = super.playerLuckyBlockData.getBlockLocation();
        Objects.requireNonNull(blockLocation.getWorld()).dropItemNaturally(blockLocation, new ExtraHeartChestplate().getItemStack());
        Objects.requireNonNull(blockLocation.getWorld()).playSound(blockLocation, Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
    }

    @Override
    public ActionRarity getRarity() {
        return ActionRarity.UNCOMMON;
    }

    @Override
    public String getDisplayName() {
        return "Heart Chestplate";
    }
}
