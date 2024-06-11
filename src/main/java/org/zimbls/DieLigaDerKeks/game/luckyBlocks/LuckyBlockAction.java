package org.zimbls.DieLigaDerKeks.game.luckyBlocks;

public abstract class LuckyBlockAction {
    protected PlayerLuckyBlockData playerLuckyBlockData;
    public void setPlayerLuckyBlockData(PlayerLuckyBlockData playerLuckyBlockData) {
        this.playerLuckyBlockData = playerLuckyBlockData;
    }
    public abstract void run();
    public abstract ActionRarity getRarity();
    public abstract String getDisplayName();

}
