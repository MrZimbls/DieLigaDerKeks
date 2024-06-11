package org.zimbls.DieLigaDerKeks.game.luckyBlocks;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.zimbls.DieLigaDerKeks.game.Game;
import org.zimbls.DieLigaDerKeks.game.Participant;

public class PlayerLuckyBlockData {
    private Player blockBreaker;
    private Game game;
    private Location blockLocation;

    public PlayerLuckyBlockData(Player blockBreaker, Location blockLocation, Game game) {
        this.blockBreaker = blockBreaker;
        this.game = game;
        this.blockLocation = blockLocation;
    }

    public Player getBlockBreaker() {
        return blockBreaker;
    }

    public Game getGame() {
        return game;
    }

    public Participant getParticipant() {
        return game.getParticipantByUUID(blockBreaker.getUniqueId());
    }

    public World getWorld() {
        return blockBreaker.getWorld();
    }

    public Location getBlockLocation() {
        return blockLocation;
    }
}
