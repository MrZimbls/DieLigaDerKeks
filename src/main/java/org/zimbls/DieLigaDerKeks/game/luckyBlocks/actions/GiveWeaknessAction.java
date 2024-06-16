package org.zimbls.DieLigaDerKeks.game.luckyBlocks.actions;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.ActionRarity;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.LuckyBlockAction;

public class GiveWeaknessAction extends LuckyBlockAction {
    @Override
    public void run() {
        super.playerLuckyBlockData.getParticipant().getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 3000, 1));
        super.playSound(org.bukkit.Sound.ENTITY_SPLASH_POTION_BREAK);
    }

    @Override
    public ActionRarity getRarity() {
        return ActionRarity.COMMON;
    }

    @Override
    public String getDisplayName() {
        return "Weakness";
    }
}
