package org.zimbls.DieLigaDerKeks.listener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.zimbls.DieLigaDerKeks.game.Participant;
import org.zimbls.DieLigaDerKeks.stateMachine.GameState;
import org.zimbls.DieLigaDerKeks.stateMachine.GameStateMachine;

public class PlayerEventListener implements Listener {
    private GameStateMachine state;

    public PlayerEventListener(GameStateMachine state) {
        this.state = state;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        System.out.println("PlayerJoinEvent triggered!");

        Player player = event.getPlayer();

        if (state.getState() != GameState.STOPPED) {
            Participant participant = state.getGame().getUnactivePlayerByUUID(player.getUniqueId());

            if (participant != null) {
                participant.setPlayer(player);
                Bukkit.broadcastMessage(ChatColor.GOLD + "Welcome back!");
                state.getGame().setParticipantActive(participant);

                if (state.getState() == GameState.RUNNING) {
                    participant.teleportToGameMap(state.getGame().getGameWorld());
                    participant.setScoreboard();
                } else if (state.getState() == GameState.EVENT || state.getState() == GameState.PAUSED) {
                    participant.teleportToLobbyMap(state.getGame().getLobbyMap());
                    state.setEventScoreboard(participant);
                }
            } else {
                Bukkit.getLogger().warning("Player " + player.getName() + " tried to join the game but is not in the participant list!");
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        System.out.println("PlayerQuitEvent triggered!");
        Player player = event.getPlayer();
        Participant participant = state.getGame().getParticipantByUUID(player.getUniqueId());

        if (participant != null) {
            if (state.getState() == GameState.RUNNING) {
                participant.setLastGameLocation();
                state.getGame().setParticipantInactive(participant);
                Bukkit.getLogger().info("Player " + player.getName() + " is set to inactive!");
                Bukkit.getLogger().info("Last game Location " + participant.getLastGameLocation().toString());
            } else if (state.getState() == GameState.EVENT || state.getState() == GameState.PAUSED) {
                state.getGame().setParticipantInactive(participant);
                Bukkit.getLogger().info("Player " + player.getName() + " is set to inactive!");
            }
        }
    }
}
