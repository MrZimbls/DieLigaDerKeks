package org.zimbls.DieLigaDerKeks.game.luckyBlocks.actions;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.ActionRarity;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.LuckyBlockAction;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.customArmor.ExtraHeartChestplate;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.customArmor.ExtraHeartHelmet;

import java.util.Objects;

public class SpawnHeartHelmetAction extends LuckyBlockAction {
    @Override
    public void run() {
        Location blockLocation = super.playerLuckyBlockData.getBlockLocation();
        Objects.requireNonNull(blockLocation.getWorld()).dropItemNaturally(blockLocation, new ExtraHeartHelmet().getItemStack());
        super.playSound(Sound.ENTITY_PLAYER_LEVELUP);
    }

    @Override
    public ActionRarity getRarity() {
        return ActionRarity.UNCOMMON;
    }

    @Override
    public String getDisplayName() {
        return "Heart Helmet";
    }
}