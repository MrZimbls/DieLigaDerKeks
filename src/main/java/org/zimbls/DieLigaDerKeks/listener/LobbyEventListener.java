package org.zimbls.DieLigaDerKeks.listener;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.zimbls.DieLigaDerKeks.stateMachine.GameStateMachine;

public class LobbyEventListener implements Listener {
    private GameStateMachine state;
    public String lobbyWorldName = "PlaceHolderWorldName";
    public Boolean disableDamage = false;
    public Boolean disableBlockPhysics = false;
    public Boolean disableBlockBreakAndPlace = false;
    public int teleportBackToSpawnOnY = -64;

    public LobbyEventListener(GameStateMachine state) {
        this.state = state;
    }

    @EventHandler
    public void disablePlayerDamage(EntityDamageEvent event) {
        if (!disableDamage) {return;}
        if (event.getEntity() instanceof Player player) {
            if (player.getWorld().getName().equals(lobbyWorldName)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPhysic(BlockPhysicsEvent event) {
        if (!disableBlockPhysics) {return;}
        World world = event.getBlock().getLocation().getWorld();
        if (world.getName().equalsIgnoreCase(lobbyWorldName)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityChangeBlock(EntityChangeBlockEvent event) {
        if (!disableBlockPhysics) {return;}
        World world = event.getBlock().getWorld();
        if (world.getName().equals(lobbyWorldName) && event.getEntityType() == EntityType.FALLING_BLOCK && event.getTo() == Material.AIR) {
            event.setCancelled(true);
            event.getBlock().getState().update(false);
        }
    }

    @EventHandler
    public void onBlockBrake(BlockBreakEvent e){
        if (!disableBlockBreakAndPlace) {return;}
        Player p = e.getPlayer();
        World w = p.getWorld();
        if (w.getName().equals(lobbyWorldName)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){
         if (!disableBlockBreakAndPlace) {return;}
         Player p = e.getPlayer();
         World w = p.getWorld();
         if (w.getName().equals(lobbyWorldName)) {
               e.setCancelled(true);
         }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (teleportBackToSpawnOnY == -64) {return;}
        Player player = event.getPlayer();
        Location location = player.getLocation();
        if (!location.getWorld().getName().equals(lobbyWorldName)) {return;}

        if (location.getY() < teleportBackToSpawnOnY) {
            World world = player.getWorld();
            Location spawnLocation = world.getSpawnLocation();
            player.teleport(spawnLocation);
        }
    }
}
