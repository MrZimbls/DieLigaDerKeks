package org.zimbls.DieLigaDerKeks.game;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.*;

public class GameScoreboard {

    private Scoreboard scoreboard;
    private Objective objective;

    public GameScoreboard() {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        if (manager == null) throw new IllegalStateException("Scoreboard manager is not available.");

        this.scoreboard = manager.getNewScoreboard();
        this.objective = scoreboard.registerNewObjective("timer", Criteria.DUMMY, "Time left");

        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public Scoreboard getScoreboard() {
        System.out.println("GameScoreboard.getScoreboard() called");
        System.out.println(this.scoreboard);
        return this.scoreboard;
    }

    public Objective getObjective() {
        return objective;
    }
}
