package org.zimbls.DieLigaDerKeks.listener;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.zimbls.DieLigaDerKeks.game.Participant;
import org.zimbls.DieLigaDerKeks.stateMachine.GameState;
import org.zimbls.DieLigaDerKeks.stateMachine.GameStateMachine;
import org.zimbls.DieLigaDerKeks.util.Mob;

public class MobKillListener implements Listener {
   private GameStateMachine state;
   public MobKillListener(GameStateMachine state){
      this.state = state;
   }

   @EventHandler
   public void onEntityDeath(EntityDeathEvent event){
      if (event.getEntity().getKiller() != null) {
         if (state.getState() == GameState.RUNNING) {
            Player player = event.getEntity().getKiller();
            String mobName = event.getEntity().getName();
            Participant participant = state.getGame().getParticipantByName(player.getName());
            if (participant != null) {
               Mob mob = state.getGame().getMobFormMobName(mobName);
               if (mob != null) {
                  if (participant.addIfNewMob(mob)) {
                     String message = "+" + mob.getPoints() + " for " + mobName;
                     player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
                  }
               }
            }
         }
      }
   }
}
