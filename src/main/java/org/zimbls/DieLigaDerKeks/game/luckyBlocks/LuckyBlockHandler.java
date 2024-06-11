package org.zimbls.DieLigaDerKeks.game.luckyBlocks;

import org.reflections.Reflections;
import org.zimbls.DieLigaDerKeks.game.Game;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.actions.AddPointsAction;
import org.zimbls.DieLigaDerKeks.stateMachine.GameStateMachine;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

public class LuckyBlockHandler {
    List<LuckyBlockAction> luckyBlockActions;
    private final Random random;
    public LuckyBlockHandler() {
        this.random = new Random();
        Reflections reflections = new Reflections("org.zimbls.DieLigaDerKeks.game.luckyBlocks.actions");

        Set<Class<? extends LuckyBlockAction>> actionClasses = reflections.getSubTypesOf(LuckyBlockAction.class);
        for (Class<? extends LuckyBlockAction> actionClass : actionClasses) {
            try {
                luckyBlockActions.add(actionClass.getDeclaredConstructor().newInstance());
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

        luckyBlockAction.setPlayerLuckyBlockData(playerLuckyBlockData);
        luckyBlockAction.run();
    }
}
