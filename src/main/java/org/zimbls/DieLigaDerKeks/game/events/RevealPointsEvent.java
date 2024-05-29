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

        // Log the player points to the console
        List<Participant> sortedParticipants = this.state.getGame().sortParticipantsByPoints();
        this.state.getGame().logGameProgress(sortedParticipants);
    }

    @Override
    public String getTitle() {
        return "RevealPoints";
    }
}
