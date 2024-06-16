package org.zimbls.DieLigaDerKeks.game.luckyBlocks;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.zimbls.DieLigaDerKeks.game.Game;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.actions.AddPointsAction;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.actions.LookUpAction;
import org.zimbls.DieLigaDerKeks.stateMachine.GameStateMachine;

import java.util.*;
import java.util.stream.Collectors;

public class LuckyBlockHandler {
    List<LuckyBlockAction> luckyBlockActions = new ArrayList<>();
    private final Random random;
    public LuckyBlockHandler() {
        this.random = new Random();

        Bukkit.getLogger().info("Loading lucky block actions");

        Reflections reflections = new Reflections(new ConfigurationBuilder()
            .setUrls(ClasspathHelper.forPackage("org.zimbls.DieLigaDerKeks.game.luckyBlocks.actions"))
            .setScanners(Scanners.SubTypes));

        Set<Class<? extends LuckyBlockAction>> subTypes = reflections.getSubTypesOf(LuckyBlockAction.class);
        for (Class<? extends LuckyBlockAction> subType : subTypes) {
            try {
                Bukkit.getLogger().info("Found lucky block action: " + subType.getSimpleName());
                luckyBlockActions.add(subType.getDeclaredConstructor().newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void handleLuckyBlock(PlayerLuckyBlockData playerLuckyBlockData) {

        ActionRarity randomRarity = ActionRarity.getRandomRarity();

        // Filter luckyBlockActions by randomRarity and get a random action out of the filtered list
        List<LuckyBlockAction> filteredActions = luckyBlockActions.stream()
            .filter(action -> action.getRarity() == randomRarity)
            .toList();

        if (filteredActions.isEmpty()) {
            throw new IllegalStateException("No actions available for rarity: " + randomRarity);
        }

        LuckyBlockAction luckyBlockAction = filteredActions.get(random.nextInt(filteredActions.size()));

        Bukkit.getLogger().info(playerLuckyBlockData.getParticipant().getPlayer().getName() + " rolled " + randomRarity.getDisplayName() + " and got " + luckyBlockAction.getDisplayName());
        playerLuckyBlockData.getParticipant().getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("You rolled " + randomRarity.getDisplayName() + " and got " + luckyBlockAction.getDisplayName()));
        luckyBlockAction.setPlayerLuckyBlockData(playerLuckyBlockData);
        luckyBlockAction.run();
    }
}
