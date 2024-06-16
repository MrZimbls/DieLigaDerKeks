package org.zimbls.DieLigaDerKeks.game.luckyBlocks.actions;

import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.ActionRarity;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.LuckyBlockAction;

import java.util.Random;

public class FireworkAction extends LuckyBlockAction {

    private Random random = new Random();

    @Override
    public void run() {
        Location location = super.playerLuckyBlockData.getBlockLocation();
        int durationSeconds = 5;

        int numberOfFireworks = durationSeconds * 2;

        for (int i = 0; i < numberOfFireworks; i++) {
            Bukkit.getServer().getScheduler().runTaskLater(super.playerLuckyBlockData.getPlugin(), () -> {
                FireworkEffect effect = createFireworkEffect();
                spawnFirework(location, effect);
            }, i * 10L); // 10L = 0.5 seconds delay between each firework
        }
    }

    @Override
    public ActionRarity getRarity() {
        return ActionRarity.COMMON;
    }

    @Override
    public String getDisplayName() {
        return "FIREWORK!";
    }

    private FireworkEffect createFireworkEffect() {
        FireworkEffect.Type type = FireworkEffect.Type.values()[random.nextInt(FireworkEffect.Type.values().length)];
        Color color1 = Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        Color color2 = Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        Color fadeColor1 = Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        Color fadeColor2 = Color.fromRGB(random.nextInt(256), random.nextInt(256), random.nextInt(256));

        return FireworkEffect.builder()
            .with(type)
            .withColor(color1, color2)
            .withFade(fadeColor1, fadeColor2)
            .withTrail()
            .withFlicker()
            .build();
    }

    private void spawnFirework(Location location, FireworkEffect effect) {
        World world = location.getWorld();
        if (world != null) {
            Firework firework = (Firework) world.spawnEntity(location, EntityType.FIREWORK);
            FireworkMeta meta = firework.getFireworkMeta();
            meta.addEffect(effect);
            meta.setPower(random.nextInt(3) + 1);
            firework.setFireworkMeta(meta);
        }
    }

}
