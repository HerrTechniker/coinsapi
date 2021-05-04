package de.herrtechniker.coinsapi.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLManager {

    /*
     *
     *
     * set coins
     *
     *
     * */

    public static void setCoins(String uuid, String coins) {
        MySQL.update("INSERT INTO coins (uuid,coins) VALUES ('" + uuid + "','" + coins + "')");
    }

    /*
     *
     *
     * change coins
     *
     *
     * */

    public static void changeCoins(String uuid, String coins) {
        MySQL.update("UPDATE coins SET coins='" + coins + "' WHERE uuid='" + uuid + "'");
    }

    /*
     *
     *
     * remove coins
     *
     *
     * */

    public static void removeCoins(String uuid) {MySQL.update("DELETE FROM coins WHERE uuid='" + uuid + "'");}

    /*
     *
     *
     * has coins
     *
     *
     * */

    public static boolean hasCoins(String uuid) {
        ResultSet resultSet = MySQL.getResult("SELECT coins FROM coins WHERE uuid='" + uuid + "'");
        try {
            return resultSet.next();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    /*
     *
     *
     * get coins
     *
     *
     * */

    public static String getCoins(String uuid) {
        ResultSet resultSet = MySQL.getResult("SELECT coins FROM coins WHERE uuid='" + uuid + "'");
        try {
            if (resultSet.next()) {
                return resultSet.getString("coins");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /*
    *
    *
    * Namefetcher get Name from uuid
    *
    *
    * */

    public static String getName(String uuid) {
        ResultSet resultSet = MySQL.getResult("SELECT name FROM namefetcher WHERE uuid='" + uuid + "'");
        try {
            if (resultSet.next()) {
                return resultSet.getString("name");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /*
     *
     *
     * Namefetcher get uuid from name
     *
     *
     * */

    public static String getUUID(String name) {
        ResultSet resultSet = MySQL.getResult("SELECT uuid FROM namefetcher WHERE name='" + name + "'");
        try {
            if (resultSet.next()) {
                return resultSet.getString("uuid");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    /*
     *
     *
     * Namefetcher set entry
     *
     *
     * */

    public static void setPlayerNamefetcher(String uuid, String name) {
        MySQL.update("INSERT INTO namefetcher (uuid,name) VALUES ('" + uuid + "','" + name + "')");
    }

    /*
    *
    *
    * Namefetcher has entry
    *
    *
    * */

    public static boolean hasNamefetcherEntry(String uuid) {
        ResultSet resultSet = MySQL.getResult("SELECT * FROM namefetcher WHERE uuid='" + uuid + "'");
        try {
            return resultSet.next();
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
