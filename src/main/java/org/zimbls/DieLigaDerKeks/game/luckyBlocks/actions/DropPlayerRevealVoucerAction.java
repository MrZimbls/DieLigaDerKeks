package org.zimbls.DieLigaDerKeks.game.luckyBlocks.actions;

import org.bukkit.Location;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.ActionRarity;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.LuckyBlockAction;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.customItems.RevealNearestPlayerVoucher;

import java.util.Objects;

public class DropPlayerRevealVoucerAction extends LuckyBlockAction {

    @Override
    public void run() {
        Location blockLocation = super.playerLuckyBlockData.getBlockLocation();
        Objects.requireNonNull(blockLocation.getWorld()).dropItemNaturally(blockLocation, new RevealNearestPlayerVoucher().getItemStack(5));
    }

    @Override
    public ActionRarity getRarity() {
        return ActionRarity.LEGENDARY;
    }

    @Override
    public String getDisplayName() {
        return "Drop Player Reveal Voucher";
    }
}
