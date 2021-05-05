package de.herrtechniker.coinsapi.api;

import de.herrtechniker.coinsapi.exception.PlayerNoCoinsException;
import de.herrtechniker.coinsapi.exception.PlayerNotFoundException;
import de.herrtechniker.coinsapi.main.CoinsAPI_Main;
import de.herrtechniker.coinsapi.mysql.MySQLManager;

public class CoinsAPI {

    public static void removeCoins(String uuid, String coins) {
        try {
            try {
                MySQLManager.changeCoins(uuid, coins);
                CoinsAPI_Main.getPlugin(CoinsAPI_Main.class).getEco().withdrawPlayer(uuid, Double.parseDouble(coins));
            }catch (PlayerNoCoinsException e) {
                System.out.println(e.toString());
            }
        }catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    public static void resetCoins(String uuid) {
        try {
            MySQLManager.setCoins(uuid, "0");
            double currentCoins = CoinsAPI_Main.getPlugin(CoinsAPI_Main.class).getEco().getBalance(uuid);
            CoinsAPI_Main.getPlugin(CoinsAPI_Main.class).getEco().withdrawPlayer(uuid, currentCoins);
        }catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public static void setCoins(String uuid, String coins) {
        try {
            MySQLManager.setCoins(uuid, coins);
            double currentCoins = CoinsAPI_Main.getPlugin(CoinsAPI_Main.class).getEco().getBalance(uuid);
            CoinsAPI_Main.getPlugin(CoinsAPI_Main.class).getEco().withdrawPlayer(uuid, currentCoins);
            CoinsAPI_Main.getPlugin(CoinsAPI_Main.class).getEco().depositPlayer(uuid, Double.parseDouble(coins));
        }catch (NumberFormatException e) {
            System.out.println(e.toString());
        }
    }

    public static void addCoins(String uuid, String coins) {
        int currentCoinsMySQL = Integer.parseInt(MySQLManager.getCoins(uuid));
        int toAddCoins = Integer.parseInt(coins);
        try {
            if (MySQLManager.getCoins(uuid) == null) {
                MySQLManager.setCoins(uuid, coins);
            }else {
                MySQLManager.changeCoins(uuid, String.valueOf(toAddCoins + currentCoinsMySQL));
            }
            CoinsAPI_Main.getPlugin(CoinsAPI_Main.class).getEco().depositPlayer(uuid, Double.parseDouble(coins));
        }catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public static String getCoins(String uuid) {
        String coins = null;
        try {
            coins = MySQLManager.getCoins(uuid);
        }catch (PlayerNotFoundException e) {
            System.out.println(e.toString());
        }
        return coins;
    }

    public static String getName(String uuid) {
        String name = null;
        try {
            name = MySQLManager.getName(uuid);
        }catch (PlayerNotFoundException e) {
            System.out.println(e.toString());
        }
        return name;
    }

    public static String getUUID(String name) {
        String uuid = null;
        try {
            uuid = MySQLManager.getUUID(name);
        }catch (PlayerNotFoundException e) {
            System.out.println(e.toString());
        }
        return name;
    }
}
