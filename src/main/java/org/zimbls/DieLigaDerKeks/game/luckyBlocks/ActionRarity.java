package org.zimbls.DieLigaDerKeks.game.luckyBlocks;

import org.bukkit.ChatColor;

import java.util.Random;

public enum ActionRarity {
    COMMON(49.5, ChatColor.GREEN + "Common" + ChatColor.RESET),
    UNCOMMON(40.0, ChatColor.BLUE + "Uncommon" + ChatColor.RESET),
    RARE(10.0, ChatColor.DARK_PURPLE + "Rare" + ChatColor.RESET),
    LEGENDARY(0.5, ChatColor.GOLD + "Legendary" + ChatColor.RESET);

    private final double chance;
    private final String displayName;
    private static final Random random = new Random();

    ActionRarity(double chance, String displayName) {
        this.chance = chance;
        this.displayName = displayName;
    }

    public double getChance() {
        return chance;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static ActionRarity getRandomRarity() {
        double roll = random.nextDouble() * 100.0;
        double cumulativeProbability = 0.0;

        for (ActionRarity rarity : ActionRarity.values()) {
            cumulativeProbability += rarity.getChance();
            if (roll < cumulativeProbability) {
                return rarity;
            }
        }

        // This should never happen if the probabilities are set correctly
        throw new IllegalStateException("Rarity probabilities do not sum to 100");
    }
}
