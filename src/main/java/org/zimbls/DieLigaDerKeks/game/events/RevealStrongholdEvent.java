package org.zimbls.DieLigaDerKeks.game.events;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.zimbls.DieLigaDerKeks.stateMachine.GameStateMachine;
import org.zimbls.DieLigaDerKeks.util.LanguagePreferencesBasedProperties;

public class RevealStrongholdEvent extends Event
{
    public RevealStrongholdEvent(GameStateMachine state)
    {
        super(state);
        super.minPlayers = 2;
        super.maxPlayers = 99;
    }

    @Override
    public void runEvent() {
        super.state.getGame().getParticipants().forEach(participant -> {
           Location stronghold = super.state.getGame().getGameWorld().getNearestStronghold(participant.getLastGameLocation());
           participant.getPlayer().sendMessage(ChatColor.GREEN + LanguagePreferencesBasedProperties.getProperty(participant.getPlayer().getUniqueId(), "event.message.StrongholdLocationMessage") + " X: "+stronghold.getX() + " Y: "+stronghold.getY() + " Z: " + stronghold.getZ());
        });

    }

    @Override
    public String getTitle() {
        return "RevealStronghold";
    }

}

