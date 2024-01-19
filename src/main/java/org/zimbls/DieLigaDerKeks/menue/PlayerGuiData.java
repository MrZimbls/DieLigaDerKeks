package org.zimbls.DieLigaDerKeks.menue;

import org.bukkit.entity.Player;
import org.zimbls.DieLigaDerKeks.game.Game;
import org.zimbls.DieLigaDerKeks.game.Participant;

public class PlayerGuiData {
   private Player guiHolder;
   private Game game;
   private Participant participant;

   public PlayerGuiData(Player guiHolder){
      this.guiHolder = guiHolder;
   }

   public Player getGuiHolder() {
      return guiHolder;
   }

   public Game getGame() {
      return game;
   }

   public void setGame(Game game) {
      this.game = game;
   }

   public Participant getParticipant() {
      return participant;
   }

   public void setParticipant(Participant participant) {
      this.participant = participant;
   }
}
