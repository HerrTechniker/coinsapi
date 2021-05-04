package de.herrtechniker.coinsapi.api;

import de.herrtechniker.coinsapi.mysql.MySQLManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class SetCoins extends Event {

    public static HandlerList handlers = new HandlerList();
    private Player player;
    private String coins;

    public SetCoins(Player arg0, String arg1) {
        this.player = arg0;
        this.coins = arg1;
    }

    public void setCoins(Player arg0, String arg1) {
        int i = Integer.parseInt(arg1);
        if (i >= 0) {
            MySQLManager.setCoins(arg0.getUniqueId().toString(), arg1);
        }
    }

    public Player getPlayer() {return this.player;}

    public String getLanguage() {return this.coins;}

    public static HandlerList getHandlerList(){return handlers;}

    public HandlerList getHandlers() {return handlers;}
}
