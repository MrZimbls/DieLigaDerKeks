package org.zimbls.DieLigaDerKeks.game.events;

import org.bukkit.Location;
import org.zimbls.DieLigaDerKeks.game.Participant;
import org.zimbls.DieLigaDerKeks.stateMachine.GameStateMachine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

public class RandomPlayerTpEvent extends Event {
    public RandomPlayerTpEvent(GameStateMachine state) {
        super(state);
        super.minPlayers = 2;
        super.maxPlayers = 99;
    }

    @Override
    public void runEvent() {
        System.out.println("Running RandomPlayerTp Event!");

        Set<Participant> participants = super.state.getGame().getParticipants();
        ArrayList<Location> locations = new ArrayList<Location>();

        participants.forEach(participant -> {
            if (!participant.isDead()) {
                locations.add(participant.getLastGameLocation());
                System.out.println("Player Location" + participant.getLastGameLocation().toString());
            }
        });
        Collections.shuffle(locations);
        AtomicInteger i = new AtomicInteger(0);
        participants.forEach(participant -> {
            if (!participant.isDead()) {
                participant.setSpecificLocation((Location) locations.get(i.getAndIncrement()));
            }
        });
    }

    @Override
    public String getTitle() {
        return "PlayerSwap";
    }
}
