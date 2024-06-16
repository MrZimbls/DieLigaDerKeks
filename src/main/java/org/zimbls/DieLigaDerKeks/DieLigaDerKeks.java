package org.zimbls.DieLigaDerKeks;

import org.bukkit.plugin.java.JavaPlugin;
import org.zimbls.DieLigaDerKeks.commands.*;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.CustomArmorEffectsHandler;
import org.zimbls.DieLigaDerKeks.game.luckyBlocks.LuckyBlockHandler;
import org.zimbls.DieLigaDerKeks.listener.*;
import org.zimbls.DieLigaDerKeks.stateMachine.GameStateMachine;
import org.zimbls.DieLigaDerKeks.util.SetupProccessHandler;

public class DieLigaDerKeks extends JavaPlugin {
   @Override
   public void onEnable() {
      GameStateMachine state = new GameStateMachine();
      LuckyBlockHandler luckyBlockHandler = new LuckyBlockHandler();
      CustomArmorEffectsHandler customArmorEffectsHandler = new CustomArmorEffectsHandler(state, this);
      LigaCommandHandler ligaCommandHandler = new LigaCommandHandler(this, state);
      SetupProccessHandler setupProccessHandler = new SetupProccessHandler(this, state);
      getServer().getPluginManager().registerEvents(new MobKillListener(state), this);
      getServer().getPluginManager().registerEvents(new MenueventListener(), this);
      getServer().getPluginManager().registerEvents(new PlayerDeathListener(state), this);
      getServer().getPluginManager().registerEvents(new FirstToSleepInBedListener(state), this);
      getServer().getPluginManager().registerEvents(new PlayerEventListener(state), this);
      getServer().getPluginManager().registerEvents(new LuckyBlockListener(state, luckyBlockHandler, this), this);

      getCommand("revive").setExecutor(new ReviveCommand(this, state));
      getCommand("addPoints").setExecutor(new AddPointsCommand(this, state));
      getCommand("removePoints").setExecutor(new RemovePointsCommand(this, state));
      getCommand("setupLobby").setExecutor(new SetupCommandHandler(this, setupProccessHandler, state));
      getLogger().info("DieLigaDerKeks is running in state: " + state.getState());
   }
   @Override
   public void onDisable() {
      getLogger().info("onDisable is called!");
   }
}
