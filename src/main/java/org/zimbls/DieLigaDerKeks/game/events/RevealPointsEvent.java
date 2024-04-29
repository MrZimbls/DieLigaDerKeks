package org.zimbls.DieLigaDerKeks.game.events;

import org.bukkit.ChatColor;
import org.zimbls.DieLigaDerKeks.game.Participant;
import org.zimbls.DieLigaDerKeks.stateMachine.GameStateMachine;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class RevealPointsEvent extends Event {
    public RevealPointsEvent(GameStateMachine state) {
        super(state);
        super.minPlayers = 2;
        super.maxPlayers = 2;
    }

    @Override
    public void runEvent() {
        System.out.println("Running RevealPointsEvent Event!");

        // Get all online participants
        Set<Participant> participants = super.state.getGame().getParticipants();

        // Loop through participants and collect their name and points, then sort them by points
        List<Participant> sortedParticipants = new ArrayList<>(participants);
        // Sort the participants by points
        sortedParticipants.sort((p1, p2) -> p2.getPoints() - p1.getPoints());

        // Send a message to each participant with the sorted list of participants
        sortedParticipants.forEach(participant -> {
            // Counter for the ranking list
            AtomicInteger counter = new AtomicInteger(0);
            participant.getPlayer().sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Player points revealed:");
            participant.getPlayer().sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "---");
            participant.getPlayer().sendMessage("");

            sortedParticipants.forEach(p -> {
                counter.getAndIncrement();
                participant.getPlayer().sendMessage(ChatColor.GREEN + "#" + counter + " " + p.getPlayer().getName() + " ---> " + p.getPoints() + " Points");
            });

            participant.getPlayer().sendMessage("");
            participant.getPlayer().sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "---");
        });
    }

    @Override
    public String getTitle() {
        return "RevealPoints";
    }
}
