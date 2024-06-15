package org.zimbls.DieLigaDerKeks.game.luckyBlocks.actions;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.ActionRarity;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.LuckyBlockAction;
import org.zimbls.DieLigaDerKeks.util.Mob;

import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class SpawnRandomMobAction extends LuckyBlockAction {
    @Override
    public void run() {
        Random random = new Random();
        Map<String, Mob> mobs = super.playerLuckyBlockData.getGame().getMobMap();
        Mob mob = (Mob) mobs.values().toArray()[random.nextInt(mobs.size())];
        EntityType entityType = EntityType.valueOf(mob.getName().toUpperCase().replace(" ", "_"));
        Location location = super.playerLuckyBlockData.getBlockLocation();
        Objects.requireNonNull(location.getWorld()).spawnEntity(location, entityType);
        Objects.requireNonNull(location.getWorld()).playSound(location, Sound.AMBIENT_CAVE, 1.0f, 1.0f);
    }

    @Override
    public ActionRarity getRarity() {
        return ActionRarity.RARE;
    }

    @Override
    public String getDisplayName() {
        return "Spawn Random Mob";
    }
}
