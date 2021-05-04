package de.herrtechniker.coinsapi.mysql;

import java.sql.*;

public class MySQL {

    public static String HOST;
    public static String PORT;
    public static String DATABASE;
    public static String USER;
    public static String PASSWORD;
    public static Connection connection;

    public static void connect() {
        if (!isConnected()) {
            try {
                connection = DriverManager.getConnection("jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + "?autoReconnect=true", USER, PASSWORD);
                System.out.println("[LanguageAPI] MySQL connected ");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static void disconnect() {
        if (isConnected()) {
            try {
                connection.close();
                System.out.println("[LanguageAPI] MySQL disconnected ");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static boolean isConnected() {return (connection != null);}

    public static Connection getConnection() {return connection;}

    public static void update(String qry) {
        if (isConnected()) {
            try {
                connection.createStatement().executeUpdate(qry);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static ResultSet getResult(String qry) {
        if (isConnected()) {
            try {
                return connection.createStatement().executeQuery(qry);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return null;
    }

    public static void createCoins() {
        if (isConnected()) {
            try {
                connection.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS coins (uuid CHAR(36) NOT NULL PRIMARY KEY,coins VARCHAR(10))");
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static void createCoinsNameFetcher() {
        if (isConnected()) {
            try {
                connection.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS namefetcher (uuid CHAR(36),name CHAR(20))");
            }catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
