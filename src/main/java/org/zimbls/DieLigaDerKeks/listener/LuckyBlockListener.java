package org.zimbls.DieLigaDerKeks.listener;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class LuckyBlockListener implements Listener {

    public LuckyBlockListener() {
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (block.getType() == Material.SPONGE) {
            //TODO: Implement lucky block event logic
        }
    }
}
