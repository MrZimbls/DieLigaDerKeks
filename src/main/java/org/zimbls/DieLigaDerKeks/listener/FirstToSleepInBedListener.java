package org.zimbls.DieLigaDerKeks.listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.potion.PotionEffectType;
import org.zimbls.DieLigaDerKeks.game.Participant;
import org.zimbls.DieLigaDerKeks.game.events.Event;
import org.zimbls.DieLigaDerKeks.stateMachine.GameState;
import org.zimbls.DieLigaDerKeks.stateMachine.GameStateMachine;

import java.util.Set;

public class FirstToSleepInBedListener implements Listener {
    private final GameStateMachine state;
    private boolean listenerActive = true;

    public FirstToSleepInBedListener(GameStateMachine state) {
        this.state = state;
    }

    @EventHandler
    public void onPlayerEnterBed(PlayerBedEnterEvent event) {
        // Check if the game is running and the challenge is active
        if (state.getState() != GameState.RUNNING) return;

        // Check if the listener is active
        if (!listenerActive) return;

        Event activeEvent = state.getGame().getActiveEvent();

        // Check if the active event is the SleepInBedChallenge
        if (activeEvent == null || !activeEvent.getTitle().equals("FirstToSleepInBedChallenge")) return;

        // Get all online participants
        Set<Participant> participants = state.getGame().getParticipants();

        Player player = event.getPlayer();
        System.out.println("First player to trigger PlayerBedEnterEvent: " + player.getName());
        Participant participant = state.getGame().getParticipantByName(player.getName());

        if (participant == null) return;

        // Let the player know that the event ended
        participants.stream().filter(p -> !p.equals(participant)).forEach(p -> {
            p.getPlayer().sendMessage(ChatColor.RED + player.getName() + " is sleeping in a bed and won the event challenge!");
        });

        // Let the player know that he won the event and give him points
        participant.getPlayer().sendMessage(ChatColor.GOLD + "You were the first to sleep in bed! +10 points!");
        participant.addPoints(10);

        // Give the player a few buff
        participant.getPlayer().addPotionEffect(new org.bukkit.potion.PotionEffect(PotionEffectType.FAST_DIGGING, 20 * 60 * 5, 0));
        participant.getPlayer().addPotionEffect(new org.bukkit.potion.PotionEffect(PotionEffectType.SATURATION, 20 * 60 * 5, 0));
        participant.getPlayer().addPotionEffect(new org.bukkit.potion.PotionEffect(PotionEffectType.REGENERATION, 20 * 60 * 5, 0));

        // Set the challenge to inactive
        if (listenerActive) listenerActive = false;
    }
}
