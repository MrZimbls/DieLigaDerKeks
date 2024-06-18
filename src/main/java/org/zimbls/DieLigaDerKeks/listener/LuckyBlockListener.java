package org.zimbls.DieLigaDerKeks.listener;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.zimbls.DieLigaDerKeks.game.Game;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.LuckyBlockHandler;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.PlayerLuckyBlockData;
import org.zimbls.DieLigaDerKeks.stateMachine.GameState;
import org.zimbls.DieLigaDerKeks.stateMachine.GameStateMachine;

import java.util.Objects;

public class LuckyBlockListener implements Listener {
    private GameStateMachine state;
    private LuckyBlockHandler luckyBlockHandler;
    private Plugin plugin;

    public LuckyBlockListener(GameStateMachine state, LuckyBlockHandler luckyBlockHandler, Plugin plugin) {
        this.state = state;
        this.luckyBlockHandler = luckyBlockHandler;
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (block.getType() == Material.SPONGE) {
            if (state.getState() == GameState.RUNNING) {
                block.setType(Material.AIR);
                event.setCancelled(true);

                PlayerLuckyBlockData luckyBlockData = new PlayerLuckyBlockData(event.getPlayer(), block.getLocation(), state.getGame(),plugin );
                luckyBlockHandler.handleLuckyBlock(luckyBlockData);
            }
        }
    }

    @EventHandler
    public void onItemInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item != null && item.getType() == Material.PAPER) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                if (meta.getPersistentDataContainer().has(Game.CUSTOM_ITEM_KEY, PersistentDataType.STRING)) {
                    String customItemIdentifier = meta.getPersistentDataContainer().get(Game.CUSTOM_ITEM_KEY, PersistentDataType.STRING);
                    assert customItemIdentifier != null;
                    if (customItemIdentifier.equals("reveal_nearest_player_voucher")) {
                        handleNearestPlayerVoucher(player, item);
                    }
                }
            }
        }
    }

    private void handleNearestPlayerVoucher(Player player, ItemStack item) {
        Player nearestPlayer = null;
        double nearestDistance = Double.MAX_VALUE;

        for (Player otherPlayer : Bukkit.getOnlinePlayers()) {
            if (otherPlayer != player) {
                double distance = player.getLocation().distance(otherPlayer.getLocation());
                if (distance < nearestDistance) {
                    nearestDistance = distance;
                    nearestPlayer = otherPlayer;
                }
            }
        }

        if (nearestPlayer != null) {
            item.setAmount(item.getAmount() - 1);

            player.sendMessage(ChatColor.AQUA + "The nearest player is " + ChatColor.DARK_PURPLE + nearestPlayer.getName());
            player.sendMessage(ChatColor.AQUA + "They are " + ChatColor.BLUE + (int) nearestDistance + ChatColor.AQUA + " blocks away");
            player.sendMessage(ChatColor.AQUA + "Coordinates: " + ChatColor.BLUE + "X: " + nearestPlayer.getLocation().getBlockX() + " Z: " + nearestPlayer.getLocation().getBlockZ());

            Player finalNearestPlayer = nearestPlayer;
            new BukkitRunnable() {
                final int effektDuration = 7 * 10; // 7 seconds
                int iterations = 0;
                final Location locationOfNearestPlayer = finalNearestPlayer.getLocation();
                final double particleStepDistance = 0.2;
                final double lineLength = 2.0;
                double currentParticleDistance = 0.0;
                final Location particleSourceLocation = player.getLocation().clone().add(0, 1, 0);
                final Vector direction = locationOfNearestPlayer.toVector().subtract(particleSourceLocation.toVector()).normalize();
                @Override
                public void run() {
                    if (iterations < effektDuration) {
                        if (currentParticleDistance < lineLength) {
                            Location particleLocation = particleSourceLocation.clone().add(direction.clone().multiply(currentParticleDistance));
                            Objects.requireNonNull(particleSourceLocation.getWorld()).spawnParticle(Particle.END_ROD, particleLocation, 1, 0, 0, 0, 0);
                            currentParticleDistance += particleStepDistance;
                        } else {
                            currentParticleDistance = 0.0;
                        }
                        iterations++;
                    } else {
                        this.cancel();
                    }
                }
            }.runTaskTimer(plugin, 0L, 2L);
        } else {
            player.sendMessage(ChatColor.RED + "No other players found!");
        }
    }
}
