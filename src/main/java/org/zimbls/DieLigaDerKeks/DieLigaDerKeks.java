package org.zimbls.DieLigaDerKeks;

import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;
import org.zimbls.DieLigaDerKeks.commands.LigaCommandHandler;
import org.zimbls.DieLigaDerKeks.listener.MenueventListener;
import org.zimbls.DieLigaDerKeks.listener.MobKillListener;
import org.zimbls.DieLigaDerKeks.listener.PlayerDeathListener;
import org.zimbls.DieLigaDerKeks.stateMachine.GameStateMachine;

public class DieLigaDerKeks extends JavaPlugin {
   @Override
   public void onEnable() {
      GameStateMachine state = new GameStateMachine();
      LigaCommandHandler ligaCommandHandler = new LigaCommandHandler(this, state);
      getServer().getPluginManager().registerEvents(new MobKillListener(state), this);
      getServer().getPluginManager().registerEvents(new MenueventListener(), this);
      getServer().getPluginManager().registerEvents(new PlayerDeathListener(state), this);
      getLogger().info("DieLigaDerKeks is running in state: " + state.getState());
   }
   @Override
   public void onDisable() {
      getLogger().info("onDisable is called!");
   }
}
