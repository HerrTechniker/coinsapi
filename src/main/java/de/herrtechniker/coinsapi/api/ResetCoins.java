package de.herrtechniker.coinsapi.api;

import de.herrtechniker.coinsapi.mysql.MySQLManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ResetCoins extends Event {

    public static HandlerList handlers = new HandlerList();
    private Player player;

    public ResetCoins(Player arg0) {this.player = arg0;}

    public void resetCoins(Player arg0) {MySQLManager.setCoins(arg0.getUniqueId().toString(), "0");}

    public Player getPlayer() {return this.player;}

    public static HandlerList getHandlerList(){return handlers;}

    public HandlerList getHandlers() {return handlers;}
}
