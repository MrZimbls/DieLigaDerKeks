package org.zimbls.DieLigaDerKeks.game.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.zimbls.DieLigaDerKeks.game.Participant;
import org.zimbls.DieLigaDerKeks.stateMachine.GameState;
import org.zimbls.DieLigaDerKeks.stateMachine.GameStateMachine;
import org.zimbls.DieLigaDerKeks.util.Countdown;
import org.zimbls.DieLigaDerKeks.util.CountdownAction;

import java.util.Set;

public class RevealLocationEvent extends Event {
    public RevealLocationEvent(GameStateMachine state) {
        super(state);
        super.minPlayers = 2;
        super.maxPlayers = 99;
    }

    @Override
    public void runEvent() {
        System.out.println("Running RevealLocation Event!");

        // Get all online participants
        Set<Participant> participants = super.state.getGame().getParticipants();

        participants.forEach(participant -> {
            // Last game location of the player before the event
            Location lastGameLocationOfParticipant = participant.getLastGameLocation();

            // Send a message to all participants
            participant.getPlayer().sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "Revealing all player locations! You are also glowing for 5 minutes!");

            // Give the player a glowing effect for 5 minutes
            participant.getPlayer().addPotionEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.GLOWING, 20 * 60 * 5, 1));

            // Send the location of all participants to the player
            participants.stream().filter(p -> !p.equals(participant)).forEach(p -> {
                // Last game location of the player before the event
                Location lastGameLocationOfEnemy = p.getLastGameLocation();

                // Location of the player
                String locationString = lastGameLocationOfEnemy.getBlockX() + ", " + lastGameLocationOfEnemy.getBlockY() + ", " + lastGameLocationOfEnemy.getBlockZ();

                // Distance of the player to the participant in blocks (rounded)
                int distance = (int) lastGameLocationOfParticipant.distance(lastGameLocationOfEnemy);

                // Send the location and the distance of the player to the participant
                participant.getPlayer().sendMessage(ChatColor.GREEN + p.getPlayer().getName() + ChatColor.RESET + " is at " + locationString + " and " + ChatColor.RED + distance + ChatColor.RESET + " blocks away from you!");
            });
        });

    }

    @Override
    public String getTitle() {
        return "RevealLocation";
    }
}
