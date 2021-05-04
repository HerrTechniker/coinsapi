package de.herrtechniker.coinsapi.mysql;


import de.herrtechniker.coinsapi.main.Main;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class MySQLConfig {

    public File mysqlFile;
    public final File dataFolder;
    public FileConfiguration configuration;

    public MySQLConfig(Main plugin) {this.dataFolder = plugin.getDataFolder();}

    public void setStandard() {
        this.mysqlFile = new File(this.dataFolder, "mysql.yml");
        this.configuration = new YamlConfiguration();
        if (!this.dataFolder.exists()) {
            this.dataFolder.mkdir();
        }
        try {
            if (!this.mysqlFile.exists()) {
                this.mysqlFile.createNewFile();

                this.configuration.load(this.mysqlFile);

                configuration.set("HOST", "localhost");
                configuration.set("PORT", "3306");
                configuration.set("DATABASE", "database");
                configuration.set("USER", "root");
                configuration.set("PASSWORD", "password");

                this.configuration.save(this.mysqlFile);
            }
        }catch (IOException|InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    private File getFile() {return new File("plugins/MySQL", "mysql.yml");}


    public void readData() {
        try {
            this.configuration.load(this.mysqlFile);
        }catch (IOException|org.bukkit.configuration.InvalidConfigurationException e) {
            e.printStackTrace();
        }

        MySQL.HOST = configuration.getString("HOST");
        MySQL.PORT = configuration.getString("PORT");
        MySQL.DATABASE = configuration.getString("DATABASE");
        MySQL.USER = configuration.getString("USER");
        MySQL.PASSWORD = configuration.getString("PASSWORD");
    }

}
