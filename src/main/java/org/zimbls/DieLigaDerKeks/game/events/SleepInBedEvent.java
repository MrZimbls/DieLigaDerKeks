package org.zimbls.DieLigaDerKeks.game.events;

import org.bukkit.ChatColor;
import org.zimbls.DieLigaDerKeks.game.Participant;
import org.zimbls.DieLigaDerKeks.stateMachine.GameStateMachine;

import java.util.Set;

public class SleepInBedEvent extends Event {

    public SleepInBedEvent(GameStateMachine state) {
        super(state);
        super.minPlayers = 2;
        super.maxPlayers = 2;
    }

    @Override
    public void runEvent() {
        System.out.println("Running SleepInBed Challenge!");

        // Get all online participants
        Set<Participant> participants = super.state.getGame().getParticipants();

        participants.forEach(participant -> {
            // Send a message to all participants
            participant.getPlayer().sendTitle(ChatColor.GREEN + "Challenge started!", "First player to sleep in a bed will receive a reward!", 10, 70, 20);
            participant.getPlayer().sendMessage(ChatColor.GREEN + "First player to sleep in a bed will receive a reward!");
        });

        // Rest will be handled by the SleepInBed listener
    }

    @Override
    public String getTitle() {
        return "SleepInBedChallenge";
    }
}
