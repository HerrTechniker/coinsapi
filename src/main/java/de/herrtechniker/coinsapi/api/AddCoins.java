package de.herrtechniker.coinsapi.api;

import de.herrtechniker.coinsapi.mysql.MySQLManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class AddCoins extends Event {

    public static HandlerList handlers = new HandlerList();
    private Player player;
    private String coins;

    public AddCoins(Player arg0, String arg1) {
        this.player = arg0;
        this.coins = arg1;
    }

    public void addCoins(Player arg0, String arg1) {
        int currentCoins = Integer.parseInt(MySQLManager.getCoins(arg0.getUniqueId().toString()));
        int toAddCoins = Integer.parseInt(arg1);
        MySQLManager.changeCoins(arg0.getUniqueId().toString(), String.valueOf(currentCoins - toAddCoins));
    }

    public Player getPlayer() {return this.player;}

    public String getCoins() {return coins;}

    public static HandlerList getHandlerList(){return handlers;}

    public HandlerList getHandlers() {return handlers;}
}
