package de.herrtechniker.coinsapi.listener;

import de.herrtechniker.coinsapi.mysql.MySQLManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!MySQLManager.hasCoins(player.getUniqueId().toString())) {
            MySQLManager.setCoins(player.getUniqueId().toString(), "0");
        }
        if (!MySQLManager.hasNamefetcherEntry(player.getUniqueId().toString())) {
            MySQLManager.setPlayerNamefetcher(player.getUniqueId().toString(), player.getName());
        }
    }

}
