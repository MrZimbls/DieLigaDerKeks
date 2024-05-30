package org.zimbls.DieLigaDerKeks.game;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.*;

public class EventScoreboard {

    private Scoreboard scoreboard;
    private Objective objective;

    public EventScoreboard() {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        if (manager == null) throw new IllegalStateException("Scoreboard manager is not available.");

        this.scoreboard = manager.getNewScoreboard();
        this.objective = scoreboard.registerNewObjective("timer", Criteria.DUMMY, "Event Time");

        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

    public Objective getObjective() {
        return objective;
    }
}
