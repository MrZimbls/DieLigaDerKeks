package org.zimbls.DieLigaDerKeks.game.events;

import org.bukkit.ChatColor;
import org.bukkit.potion.PotionEffectType;
import org.zimbls.DieLigaDerKeks.game.Participant;
import org.zimbls.DieLigaDerKeks.stateMachine.GameStateMachine;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NerfTopTwoPlayersEvent extends Event {
    public NerfTopTwoPlayersEvent(GameStateMachine state) {
        super(state);
        super.minPlayers = 4;
        super.maxPlayers = 99;
    }

    @Override
    public void runEvent() {
        System.out.println("Running NerfTopTwoPlayers Event!");

        // Get all online participants
        Set<Participant> participants = super.state.getGame().getParticipants();

        // Loop through participants and collect their name and points, then sort them by points
        List<Participant> sortedParticipants = new ArrayList<>(participants);

        // Sort the participants by points
        sortedParticipants.sort((p1, p2) -> p2.getPoints() - p1.getPoints());

        // Get the two participants with the most points from the sorted list and store them in list
        List<Participant> lastTwoParticipants = new ArrayList<>();
        lastTwoParticipants.add(sortedParticipants.get(0));
        lastTwoParticipants.add(sortedParticipants.get(1));

        // Send a message to each participant with the sorted list of participants
        for (Participant participant : participants) {
            participant.getPlayer().sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "The two players with the most points are getting debuffed!");
        }

        // Give the last two players buffs
        lastTwoParticipants.forEach(participant -> {
            participant.getPlayer().sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "You got debuffed!");
            participant.getPlayer().addPotionEffect(new org.bukkit.potion.PotionEffect(PotionEffectType.SLOW_DIGGING, 20 * 60 * 10, 0));
            participant.getPlayer().addPotionEffect(new org.bukkit.potion.PotionEffect(PotionEffectType.HUNGER, 20 * 60 * 10, 0));
            participant.getPlayer().addPotionEffect(new org.bukkit.potion.PotionEffect(PotionEffectType.WEAKNESS, 20 * 60 * 10, 0));
        });
    }

    @Override
    public String getTitle() {
        return "NerfTopTwoPlayers";
    }
}