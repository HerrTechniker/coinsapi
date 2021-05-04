package de.herrtechniker.coinsapi.main;

import de.herrtechniker.coinsapi.commands.Coins_Commands;
import de.herrtechniker.coinsapi.listener.JoinListener;
import de.herrtechniker.coinsapi.mysql.MySQL;
import de.herrtechniker.coinsapi.mysql.MySQLConfig;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public Economy eco = null;
    private final String prefix = "§7[§6CoinSystem§7] §r";

    @Override
    public void onEnable() {
        System.out.println("[CoinsAPI] loading Plugin...");
        System.out.println("----------[ CoinsAPI ]----------");
        System.out.println(" Product: CoinsAPI");
        System.out.println(" Created By: Oliver S. -  HerrTechniker");
        System.out.println(" ");
        System.out.println(" All rights reserved by HerrTechniker");
        System.out.println("----------[ CoinsAPI ]----------");

        if (!setupEconomy()) {
            System.out.println("Please install Vault for using CoinsAPI!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        MySQLConfig mySQLFile = new MySQLConfig(this);
        mySQLFile.setStandard();
        mySQLFile.readData();

        MySQL.connect();
        MySQL.createCoins();
        MySQL.createCoinsNameFetcher();

        if (setupEconomy()) {
            System.out.println("[CoinsAPI] Vault was found!");
        }else {
            System.out.println("[CoinsAPI] Please install Vault to use CoinsAPI!");
        }

        getCommand("coins").setExecutor(new Coins_Commands(this));

        Bukkit.getPluginManager().registerEvents(new JoinListener(), this);

        System.out.println("[CoinsAPI] Plugin enabled");
    }

    @Override
    public void onDisable() {
        System.out.println("[CoinsAPI] disable Plugin...");
        System.out.println("----------[ CoinsAPI ]----------");
        System.out.println(" Product: CoinsAPI");
        System.out.println("        Thank you for using");
        System.out.println("            CoinsAPI");
        System.out.println(" ");
        System.out.println(" All rights reserved by HerrTechniker");
        System.out.println("----------[ CoinsAPI ]----------");

        MySQL.disconnect();

        System.out.println("[CoinsAPI] Plugin disabled");

    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        eco = rsp.getProvider();
        return eco != null;
    }

    public Economy getEco() {return eco;}

    public String getPrefix() {return prefix;}
}
