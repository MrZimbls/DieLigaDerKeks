package org.zimbls.DieLigaDerKeks.game.luckyBlocks.actions;

import org.zimbls.DieLigaDerKeks.game.luckyBlocks.ActionRarity;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.LuckyBlockAction;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.PlayerLuckyBlockData;

public class AddPointsAction extends LuckyBlockAction {
    @Override
    public void run() {
        super.playerLuckyBlockData.getParticipant().addPoints(1);
    }

    @Override
    public ActionRarity getRarity() {
        return ActionRarity.COMMON;
    }

    @Override
    public String getDisplayName() {
        return "Add Points";
    }
}
