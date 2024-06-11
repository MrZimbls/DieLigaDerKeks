package org.zimbls.DieLigaDerKeks.game.luckyBlocks.actions;

import org.zimbls.DieLigaDerKeks.game.luckyBlocks.ActionRarity;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.LuckyBlockAction;

public class RemovePointsAction extends LuckyBlockAction {
    @Override
    public void run() {
        super.playerLuckyBlockData.getParticipant().removePoints(1);
    }

    @Override
    public ActionRarity getRarity() {
        return ActionRarity.RARE;
    }

    @Override
    public String getDisplayName() {
        return "Remove Points";
    }
}
