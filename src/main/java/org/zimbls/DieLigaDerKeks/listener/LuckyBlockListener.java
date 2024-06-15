package org.zimbls.DieLigaDerKeks.listener;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.LuckyBlockHandler;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.PlayerLuckyBlockData;
import org.zimbls.DieLigaDerKeks.stateMachine.GameState;
import org.zimbls.DieLigaDerKeks.stateMachine.GameStateMachine;

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
}
