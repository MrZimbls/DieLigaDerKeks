package org.zimbls.DieLigaDerKeks.game.events;

import org.zimbls.DieLigaDerKeks.game.Participant;
import org.zimbls.DieLigaDerKeks.stateMachine.GameStateMachine;

import java.util.Iterator;
import java.util.Set;

public class SwapPointsEvent extends Event {
    public SwapPointsEvent(GameStateMachine state) {
        super(state);
        super.minPlayers = 2;
        super.maxPlayers = 2;
    }

    @Override
    public void runEvent() {
        System.out.println("Running SwapPoints Event!");

        // Get all online participants
        Set<Participant> participants = super.state.getGame().getParticipants();
        System.out.println("Participants set: " + participants.size());
        Iterator<Participant> iterator = participants.iterator();

        System.out.println("Participants iterator: " + participants.size());

        // Get the two players
        Participant player1 = iterator.next();
        Participant player2 = iterator.next();

        System.out.println("Swapping points between " + player1.getPlayer().getName() + " and " + player2.getPlayer().getName() + "!");

        // Get the points of the two players
        int pointsPlayer1 = player1.getPoints();
        int pointsPlayer2 = player2.getPoints();

        System.out.println("Points before swap: " + player1.getPlayer().getName() + ": " + pointsPlayer1 + ", " + player2.getPlayer().getName() + ": " + pointsPlayer2);

        // Swap the points
        player1.setPoints(pointsPlayer2);
        player2.setPoints(pointsPlayer1);
    }

    @Override
    public String getDescription() {
        return "When both players agree, their points will be swapped!";
    }

    @Override
    public String getTitle() {
        return "PointSwap";
    }
}