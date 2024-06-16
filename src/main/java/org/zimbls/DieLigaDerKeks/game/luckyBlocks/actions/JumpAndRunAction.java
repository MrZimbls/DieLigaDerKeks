package org.zimbls.DieLigaDerKeks.game.luckyBlocks.actions;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.entity.TextDisplay;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.ActionRarity;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.LuckyBlockAction;
import org.zimbls.DieLigaDerKeks.util.InventoryManager;

import java.util.Objects;
import java.util.Random;

public class JumpAndRunAction extends LuckyBlockAction {
    @Override
    public void run() {
        Location blockLocation = super.playerLuckyBlockData.getBlockLocation();
        Player player = super.playerLuckyBlockData.getParticipant().getPlayer();
        clearArea(blockLocation);
        super.playSound(Sound.ENTITY_GENERIC_EXPLODE);
        blockLocation.getBlock().setType(Material.OBSIDIAN);
        InventoryManager inventoryManager = new InventoryManager();

        new BukkitRunnable() {
            Location currentBlock = blockLocation;
            Location nextBlock = null;
            int jumpCounter = 0;
            boolean isJumping = false;
            final Location displayLocation = blockLocation.clone().add(0.5,1.5,0.5);
            final TextDisplay textDisplay = Objects.requireNonNull(currentBlock.getWorld()).spawn(displayLocation, TextDisplay.class);
            int failHeight = -100;

            public void run() {
                Location playerLocation = player.getLocation();
                if (playerLocation.getBlockY() < failHeight && isJumping) {
                    endJumpAndRun(false);
                    currentBlock.getBlock().setType(Material.AIR);
                    nextBlock.getBlock().setType(Material.AIR);
                    inventoryManager.restoreInventory(player);
                    this.cancel();
                }
                else if (jumpCounter == 8 && isJumping) {
                    endJumpAndRun(true);
                    currentBlock.getBlock().setType(Material.AIR);
                    nextBlock.getBlock().setType(Material.AIR);
                    inventoryManager.restoreInventory(player);
                    this.cancel();
                }
                else if (jumpCounter == 0 && !isJumping) {
                    Bukkit.getLogger().info("Setup Jump and Run");
                    textDisplay.setText(ChatColor.GOLD + "Jump here!");
                    Vector directionToPlayer = player.getLocation().toVector().subtract(displayLocation.toVector()).normalize();
                    float yaw = (float) Math.toDegrees(Math.atan2(directionToPlayer.getZ(), directionToPlayer.getX())) - 90;
                    textDisplay.setRotation(yaw, 0);
                    jumpCounter++;
                }
                else if (isStandingOnBlock(player, currentBlock) && !isJumping) {
                    Bukkit.getLogger().info("starting jump and run");
                    isJumping = true;
                    inventoryManager.saveInventory(player);
                    inventoryManager.clearInventory(player);
                    failHeight = currentBlock.getBlockY();
                    textDisplay.remove();
                    nextBlock = randomNextJumpBlock(currentBlock);
                    nextBlock.getBlock().setType(randomConcreateColor());
                }
                else if (isStandingOnBlock(player, nextBlock) && isJumping) {
                    Bukkit.getLogger().info("Jumping to next block");
                    currentBlock.getBlock().setType(Material.AIR);
                    currentBlock = nextBlock;
                    nextBlock = randomNextJumpBlock(currentBlock);
                    nextBlock.getBlock().setType(randomConcreateColor());
                    failHeight = currentBlock.getBlockY();
                    jumpCounter++;
                }
            }
        }.runTaskTimer(super.playerLuckyBlockData.getPlugin(), 0L, 1L);
    }

    @Override
    public ActionRarity getRarity() {
        return ActionRarity.RARE;
    }

    @Override
    public String getDisplayName() {
        return "Jump & Run";
    }

    private void clearArea(Location center) {
        int startD = -8;
        int stopD = 8;
        int height = 16;

        for (int x = startD; x <= stopD; x++) {
            for (int y = 0; y <= height; y++) {
                for (int z = startD; z <= stopD; z++) {
                    center.clone().add(x, y, z).getBlock().setType(org.bukkit.Material.AIR);
                }
            }
        }
    }

    private Location randomNextJumpBlock(Location currentBlock) {
        Random random = new Random();

        int r = random.nextInt(8) + 1;

        return switch (r) {
            case 1 -> currentBlock.clone().add(3, 1, 1);
            case 2 -> currentBlock.clone().add(1, 1, 3);
            case 3 -> currentBlock.clone().add(-1, 1, -3);
            case 4 -> currentBlock.clone().add(-3, 1, -1);
            case 5 -> currentBlock.clone().add(3, 0, 4);
            case 6 -> currentBlock.clone().add(-3, 1, -4);
            case 7 -> currentBlock.clone().add(4, 1, 0);
            case 8 -> currentBlock.clone().add(0, 1, 4);
            default ->  currentBlock.clone().add(1, 1, 1);
        };
    }

    private Material randomConcreateColor() {
        Random random = new Random();
        int r = random.nextInt(16) + 1;
        return switch (r) {
            case 2 -> Material.ORANGE_CONCRETE;
            case 3 -> Material.MAGENTA_CONCRETE;
            case 4 -> Material.LIGHT_BLUE_CONCRETE;
            case 5 -> Material.YELLOW_CONCRETE;
            case 6 -> Material.LIME_CONCRETE;
            case 7 -> Material.PINK_CONCRETE;
            case 8 -> Material.GRAY_CONCRETE;
            case 9 -> Material.LIGHT_GRAY_CONCRETE;
            case 10 -> Material.CYAN_CONCRETE;
            case 11 -> Material.PURPLE_CONCRETE;
            case 12 -> Material.BLUE_CONCRETE;
            case 13 -> Material.BROWN_CONCRETE;
            case 14 -> Material.GREEN_CONCRETE;
            case 15 -> Material.RED_CONCRETE;
            case 16 -> Material.BLACK_CONCRETE;
            default -> Material.WHITE_CONCRETE;
        };
    }

    private void endJumpAndRun(Boolean success) {
        Player player = super.playerLuckyBlockData.getParticipant().getPlayer();
        if (success) {
            super.playSound(Sound.ENTITY_PLAYER_LEVELUP);
            player.sendMessage(ChatColor.GREEN + "You have completed the Jump & Run!");
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 1000, 1));
            Objects.requireNonNull(player.getLocation().getWorld()).dropItemNaturally(player.getLocation(), new ItemStack(Material.NETHERITE_SWORD, 1));
        } else {
            player.sendMessage(ChatColor.RED + "You have failed the Jump & Run!");
            super.playSound(Sound.BLOCK_ANVIL_PLACE);
        }
    }

    private boolean isStandingOnBlock(Player player, Location blockLocation) {
        if (blockLocation == null) {
            return false;
        }
        Location playerLocation = player.getLocation();
        // Check if the player's feet are at the target block's location
        return playerLocation.getBlockX() == blockLocation.getBlockX() &&
            playerLocation.getBlockY() - 1 == blockLocation.getBlockY() &&
            playerLocation.getBlockZ() == blockLocation.getBlockZ();
    }
}
