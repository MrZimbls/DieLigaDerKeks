package org.zimbls.DieLigaDerKeks.util;

import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Score;
import org.zimbls.DieLigaDerKeks.game.GameScoreboard;
import org.zimbls.DieLigaDerKeks.stateMachine.GameStateMachine;

public class TimerTask extends BukkitRunnable {
    private int remainingTime = 6 * 60 * 60; // 6 hours in seconds
    private boolean isPaused = false;
    private int thirtyMinutesCountdown = 20; // 30 minutes in seconds (should be 30 * 60)y
    private GameStateMachine state;

    public TimerTask(GameStateMachine state) {
        this.state = state;
    }

    @Override
    public void run() {
        if (isPaused || remainingTime <= 0) {
            if (remainingTime <= 0) {
                this.cancel(); // Stop the task if the time is up
                state.stopGame();
                return;
            }
            return;
        }

        // Update the timer every second
        updateTimer();

        // Trigger an event every 30 minutes
        if (thirtyMinutesCountdown <= 0) {
            triggerThirtyMinuteEvent();
            thirtyMinutesCountdown = 30 * 60;
        }

        remainingTime--;
        thirtyMinutesCountdown--;
    }

    private void updateTimer() {
        int hours = remainingTime / 3600;
        int minutes = (remainingTime % 3600) / 60;
        int seconds = remainingTime % 60;
        String timeFormatted = String.format("%02d:%02d:%02d", hours, minutes, seconds);

        state.getGame().getParticipants().forEach(participant -> {
            GameScoreboard gameScoreboard = participant.getScoreboard();
            gameScoreboard.getObjective().setDisplayName(ChatColor.DARK_GRAY + "Time Left: " + ChatColor.GOLD + timeFormatted);
            Score timeScore = gameScoreboard.getObjective().getScore(ChatColor.DARK_GRAY + "/points");
            timeScore.setScore(participant.getPoints());
        });
    }

    public void setPaused(boolean paused) {
        this.isPaused = paused;
    }

    private void triggerThirtyMinuteEvent() {
        state.triggerEvent();
    }
}
