package org.zimbls.DieLigaDerKeks.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.zimbls.DieLigaDerKeks.listener.MobKillListener;
import org.zimbls.DieLigaDerKeks.listener.PlayerMassageListener;
import org.zimbls.DieLigaDerKeks.stateMachine.GameStateMachine;
import org.zimbls.DieLigaDerKeks.util.SetupProccessHandler;

public class SetupCommandHandler implements CommandExecutor {
      private GameStateMachine state;
      private JavaPlugin plugin;
      private SetupProccessHandler setupProccessHandler;

      public SetupCommandHandler(JavaPlugin plugin, SetupProccessHandler setupProccessHandler, GameStateMachine state) {
         this.plugin = plugin;
         this.state = state;
         this.setupProccessHandler = setupProccessHandler;
      }

      @Override
      public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
         plugin.getLogger().info("Handling setup command");

         if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
         }

         Player player = (Player) sender;

         setupProccessHandler.startSetup(player);
         return true;
      }
}
