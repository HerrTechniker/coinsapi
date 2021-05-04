package de.herrtechniker.coinsapi.commands;

import de.herrtechniker.api.LanguageAPI;
import de.herrtechniker.coinsapi.api.CoinsAPI;
import de.herrtechniker.coinsapi.main.Main;
import de.herrtechniker.coinsapi.mysql.MySQLManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class Coins_Commands implements CommandExecutor {

    private Main plugin;

    public Coins_Commands(Main plugin) {this.plugin = plugin;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            /*
            *
            *
            * EN START
            *
            *
            * */

            if (LanguageAPI.getPlayerLanguage(player.getUniqueId().toString()).equals("en_EN") || (Bukkit.getPluginManager().getPlugin("LanguageAPI") == null)) {
                if (plugin.getEco() != null) {
                    if (args.length == 0) {
                        if (plugin.getEco().hasAccount(player)) {
                            String coins = CoinsAPI.getCoins(player.getUniqueId().toString());
                            player.sendMessage(plugin.getPrefix() + "§7You currently have §e" + coins + "§7coins");
                        }else {
                            player.sendMessage(plugin.getPrefix() + "§7You currently have §e0 §7coins");
                        }
                    }else if (args.length == 1) {
                        if (args[0].equalsIgnoreCase("help")) {
                            if (player.hasPermission("coinsapi.add") || player.hasPermission("coinsapi.remove") || player.hasPermission("coinsapi.reset") || player.hasPermission("coinsapi.set") || player.hasPermission("coinsapi.*")) {
                                player.sendMessage("§7----------" + plugin.getPrefix() + "§7----------");
                                player.sendMessage("");
                                player.sendMessage("§a/coins");
                                player.sendMessage("§a/coins <targetPlayer>");
                                player.sendMessage("§a/coins help");
                                player.sendMessage("§a/coins add <targetPlayer> <coins>");
                                player.sendMessage("§a/coins remove <targetPlayer> <coins>");
                                player.sendMessage("§a/coins set <targetPlayer> <coins>");
                                player.sendMessage("§a/coins reset <targetPlayer>");
                                player.sendMessage("");
                                player.sendMessage("§7----------" + plugin.getPrefix() + "§7----------");
                            }else {
                                player.sendMessage("§7----------" + plugin.getPrefix() + "§7----------");
                                player.sendMessage("");
                                player.sendMessage("§a/coins");
                                player.sendMessage("§a/coins <targetPlayer>");
                                player.sendMessage("§a/coins help");
                                player.sendMessage("");
                                player.sendMessage("§7----------" + plugin.getPrefix() + "§7----------");
                            }
                        }else {
                            OfflinePlayer offlinePlayer = Bukkit.getPlayer(args[0]);
                            Player target = Bukkit.getPlayer(args[0]);
                            if (target != null) {
                                if (plugin.getEco().hasAccount(target)) {
                                    String coins = CoinsAPI.getCoins(target.getUniqueId().toString());
                                    player.sendMessage(plugin.getPrefix() + "§7The Player §e" + target.getName() + "§7 has §e" + coins + " §7coins.");
                                }else {
                                    player.sendMessage(plugin.getPrefix() + "§7The Player §e" + target.getName() + "§7 has §e0 §7coins.");
                                }
                            }else {
                                if (plugin.getEco().hasAccount(offlinePlayer)) {
                                    String coins = CoinsAPI.getCoins(target.getUniqueId().toString());
                                    player.sendMessage(plugin.getPrefix() + "§7The Player §e" + offlinePlayer.getName() + "§7 has §e" + coins + " §7coins.");
                                }else {
                                    player.sendMessage(plugin.getPrefix() + "§7The Player §e" + offlinePlayer.getName() + "§7 has §e0 §7coins.");
                                }
                            }
                        }
                    }else if (args.length == 2) {
                        OfflinePlayer offlinePlayer = Bukkit.getPlayer(args[1]);
                        Player target = Bukkit.getPlayer(args[1]);
                        if (args[0].equalsIgnoreCase("reset")) {
                            if (player.hasPermission("coinsapi.reset") || player.hasPermission("coinsapi.*")) {
                                if (target != null) {
                                    if (plugin.getEco().hasAccount(target)) {
                                        double coins = plugin.getEco().getBalance(target);
                                        plugin.getEco().withdrawPlayer(target, coins);
                                        MySQLManager.changeCoins(target.getUniqueId().toString(), "0");
                                    } else {
                                        plugin.getEco().createPlayerAccount(target);
                                    }
                                    player.sendMessage(plugin.getPrefix() + "§7You have successful reset the coins from §e" + target.getName() + "§7.");
                                }else {
                                    if (plugin.getEco().hasAccount(offlinePlayer)) {
                                        double coins = plugin.getEco().getBalance(offlinePlayer);
                                        plugin.getEco().withdrawPlayer(offlinePlayer, coins);
                                        MySQLManager.changeCoins(offlinePlayer.getUniqueId().toString(), "0");
                                    } else {
                                        plugin.getEco().createPlayerAccount(offlinePlayer);
                                    }
                                    player.sendMessage(plugin.getPrefix() + "§7You have successful reset the coins from §e" + offlinePlayer.getName() + "§7.");
                                }
                            }else {
                                player.sendMessage(plugin.getPrefix() + "§cYou do not have any permission to do that!");
                            }
                        }
                    }else if (args.length == 3) {
                        OfflinePlayer offlinePlayer = Bukkit.getPlayer(args[1]);
                        Player target = Bukkit.getPlayer(args[1]);
                        if (args[0].equalsIgnoreCase("remove")) {
                            if (player.hasPermission("coinsapi.remove") || player.hasPermission("coinsapi.*")) {
                                if (target != null) {
                                    if (plugin.getEco().hasAccount(target)) {
                                        if (plugin.getEco().getBalance(target) >= Double.parseDouble(args[2])) {
                                            plugin.getEco().withdrawPlayer(target, Integer.parseInt(args[2]));
                                            int currentCoins = Integer.parseInt(Objects.requireNonNull(MySQLManager.getCoins(target.getUniqueId().toString())));
                                            if (currentCoins >= Integer.parseInt(args[2])) {
                                                MySQLManager.changeCoins(target.getUniqueId().toString(), String.valueOf(currentCoins - Integer.parseInt(args[2])));
                                            }else {
                                                player.sendMessage(plugin.getPrefix() + "§cThe Player §e" + target.getName() + " §chas not enough coins!");
                                            }
                                            player.sendMessage(plugin.getPrefix() + "§7You have successful removed §e" + args[2] + " §7Coins from §e" + target.getName() + "§7.");
                                        }else {
                                            player.sendMessage(plugin.getPrefix() + "§cThe Player §e" + target.getName() + " §chas not enough Coins to remove!");
                                        }
                                    }else {
                                        player.sendMessage(plugin.getPrefix() + "§cYou can´t remove §e" + args[2] + " §ccoins from the Player §e" + target.getName() + "§c, because he has no coins");
                                    }
                                }else {
                                    if (plugin.getEco().hasAccount(offlinePlayer)) {
                                        if (plugin.getEco().getBalance(offlinePlayer) >= Double.parseDouble(args[2])) {
                                            plugin.getEco().withdrawPlayer(offlinePlayer, Integer.parseInt(args[2]));
                                            int currentCoins = Integer.parseInt(Objects.requireNonNull(MySQLManager.getCoins(offlinePlayer.getUniqueId().toString())));
                                            if (currentCoins >= Integer.parseInt(args[2])) {
                                                MySQLManager.changeCoins(offlinePlayer.getUniqueId().toString(), String.valueOf(currentCoins - Integer.parseInt(args[2])));
                                            }else {
                                                player.sendMessage(plugin.getPrefix() + "§cThe Player §e" + offlinePlayer.getName() + " §chas not enough coins!");
                                            }
                                            player.sendMessage(plugin.getPrefix() + "§7You have successful removed §e" + args[2] + " §7Coins from §e" + offlinePlayer.getName() + "§7.");
                                        }else {
                                            player.sendMessage(plugin.getPrefix() + "§cThe Player §e" + target.getName() + " §chas not enough Coins to remove!");
                                        }
                                    }else {
                                        player.sendMessage(plugin.getPrefix() + "§cYou can´t remove §e" + args[2] + " §ccoins from the Player §e" + offlinePlayer.getName() + "§c, because he has no coins");
                                    }
                                }
                            }else {
                                player.sendMessage(plugin.getPrefix() + "§cYou do not have any permission to do that!");
                            }
                        }else if (args[0].equalsIgnoreCase("add")) {
                            if (player.hasPermission("coinsapi.add") || player.hasPermission("coinsapi.*")) {
                                if (target != null) {
                                    if (!plugin.getEco().hasAccount(target)) {
                                        plugin.getEco().createPlayerAccount(target);
                                    }
                                    plugin.getEco().depositPlayer(target, Integer.parseInt(args[2]));
                                    String currentCoins = MySQLManager.getCoins(target.getUniqueId().toString());
                                    if (currentCoins != null) {
                                        int coins = Integer.parseInt(currentCoins) + Integer.parseInt(args[2]);
                                        MySQLManager.changeCoins(target.getUniqueId().toString(), String.valueOf(coins));
                                    }else {
                                        MySQLManager.changeCoins(target.getUniqueId().toString(), args[2]);
                                    }
                                    player.sendMessage(plugin.getPrefix() + "§7You have successful given §e" + args[2] + " §7coins to Player §e" + target.getName() + "§7.");
                                    target.sendMessage(plugin.getPrefix() + "§7You got §e" + args[2] + " §7coins.");
                                }else {
                                    if (!plugin.getEco().hasAccount(offlinePlayer)) {
                                        plugin.getEco().createPlayerAccount(offlinePlayer);
                                    }
                                    plugin.getEco().depositPlayer(offlinePlayer, Integer.parseInt(args[2]));
                                    String currentCoins = MySQLManager.getCoins(offlinePlayer.getUniqueId().toString());
                                    if (currentCoins != null) {
                                        int coins = Integer.parseInt(currentCoins) + Integer.parseInt(args[2]);
                                        MySQLManager.changeCoins(offlinePlayer.getUniqueId().toString(), String.valueOf(coins));
                                    }else {
                                        MySQLManager.changeCoins(offlinePlayer.getUniqueId().toString(), args[2]);
                                    }
                                    player.sendMessage(plugin.getPrefix() + "§7You have successful given §e" + args[2] + " §7coins to Player §e" + offlinePlayer.getName() + "§7.");
                                }
                            }else {
                                player.sendMessage(plugin.getPrefix() + "§cYou do not have any permission to do that!");
                            }
                        }else if (args[0].equalsIgnoreCase("set")) {
                            if (player.hasPermission("coinsapi.set") || player.hasPermission("coinsapi.*")) {
                                if (target != null) {
                                    if (plugin.getEco().hasAccount(target)) {
                                        double coins = plugin.getEco().getBalance(target);
                                        plugin.getEco().withdrawPlayer(target, coins);
                                        plugin.getEco().depositPlayer(target, Integer.parseInt(args[2]));
                                        MySQLManager.changeCoins(target.getUniqueId().toString(), args[2]);
                                        player.sendMessage(plugin.getPrefix() + "§7You have successful set the coins from the Player §e" + target.getName() + " §7to §e" + args[2] + "§7coins.");
                                    }
                                }else {
                                    if (plugin.getEco().hasAccount(offlinePlayer)) {
                                        double coins = plugin.getEco().getBalance(offlinePlayer);
                                        plugin.getEco().withdrawPlayer(offlinePlayer, coins);
                                        plugin.getEco().depositPlayer(offlinePlayer, Integer.parseInt(args[2]));
                                        MySQLManager.changeCoins(offlinePlayer.getUniqueId().toString(), args[2]);
                                        player.sendMessage(plugin.getPrefix() + "§7You have successful set the coins from the Player §e" + offlinePlayer.getName() + " §7to §e" + args[2] + "§7coins.");
                                    }
                                }
                            }else {
                                player.sendMessage(plugin.getPrefix() + "§cYou do not have any permission to do that!");
                            }
                        }
                    }else {
                        player.sendMessage(plugin.getPrefix() + "§cPlease use: §e/coins help §cto execute the command correctly!");
                    }
                }else {
                    if (player.hasPermission("coinsapi.add") || player.hasPermission("coinsapi.remove") || player.hasPermission("coinsapi.reset") || player.hasPermission("coinsapi.set") || player.hasPermission("coinsapi.*")) {
                        player.sendMessage(plugin.getPrefix() + "§cPlease install Vault to use CoinsAPI correctly");
                    }else {
                        player.sendMessage(plugin.getPrefix() + "§cI am sorry, but you can´t do this!");
                    }
                }

                /*
                *
                *
                * DE START
                *
                *
                * */

            }else if (LanguageAPI.getPlayerLanguage(player.getUniqueId().toString()).equals("de_DE")) {
                if (plugin.getEco() != null) {
                    if (args.length == 0) {
                        if (plugin.getEco().hasAccount(player)) {
                            String coins = CoinsAPI.getCoins(player.getUniqueId().toString());
                            player.sendMessage(plugin.getPrefix() + "§7Du hast momentan §e" + coins + "§7Coins.");
                        }else {
                            player.sendMessage(plugin.getPrefix() + "§7Du hast momentan §e0 §7Coins.");
                        }
                    }else if (args.length == 1) {
                        if (args[0].equalsIgnoreCase("help")) {
                            if (player.hasPermission("coinsapi.add") || player.hasPermission("coinsapi.remove") || player.hasPermission("coinsapi.reset") || player.hasPermission("coinsapi.set") || player.hasPermission("coinsapi.*")) {
                                player.sendMessage("§7----------" + plugin.getPrefix() + "§7----------");
                                player.sendMessage("");
                                player.sendMessage("§a/coins");
                                player.sendMessage("§a/coins <targetPlayer>");
                                player.sendMessage("§a/coins help");
                                player.sendMessage("§a/coins add <targetPlayer> <coins>");
                                player.sendMessage("§a/coins remove <targetPlayer> <coins>");
                                player.sendMessage("§a/coins set <targetPlayer> <coins>");
                                player.sendMessage("§a/coins reset <targetPlayer>");
                                player.sendMessage("");
                                player.sendMessage("§7----------" + plugin.getPrefix() + "§7----------");
                            }else {
                                player.sendMessage("§7----------" + plugin.getPrefix() + "§7----------");
                                player.sendMessage("");
                                player.sendMessage("§a/coins");
                                player.sendMessage("§a/coins <targetPlayer>");
                                player.sendMessage("§a/coins help");
                                player.sendMessage("");
                                player.sendMessage("§7----------" + plugin.getPrefix() + "§7----------");
                            }
                        }else {
                            OfflinePlayer offlinePlayer = Bukkit.getPlayer(args[0]);
                            Player target = Bukkit.getPlayer(args[0]);
                            if (target != null) {
                                if (plugin.getEco().hasAccount(target)) {
                                    String coins = CoinsAPI.getCoins(target.getUniqueId().toString());
                                    player.sendMessage(plugin.getPrefix() + "§7Der Spieler §e" + target.getName() + "§7 hat §e" + coins + " §7Coins.");
                                }else {
                                    player.sendMessage(plugin.getPrefix() + "§7Der Spieler §e" + target.getName() + "§7 hat §e0 §7Coins.");
                                }
                            }else {
                                if (plugin.getEco().hasAccount(offlinePlayer)) {
                                    String coins = CoinsAPI.getCoins(target.getUniqueId().toString());
                                    player.sendMessage(plugin.getPrefix() + "§7Der Spieler §e" + offlinePlayer.getName() + "§7 hat §e" + coins + " §7Coins.");
                                }else {
                                    player.sendMessage(plugin.getPrefix() + "§7Der Spieler §e" + offlinePlayer.getName() + "§7 hat §e0 §7Coins.");
                                }
                            }
                        }
                    }else if (args.length == 2) {
                        OfflinePlayer offlinePlayer = Bukkit.getPlayer(args[1]);
                        Player target = Bukkit.getPlayer(args[1]);
                        if (args[0].equalsIgnoreCase("reset")) {
                            if (player.hasPermission("coinsapi.reset") || player.hasPermission("coinsapi.*")) {
                                if (target != null) {
                                    if (plugin.getEco().hasAccount(target)) {
                                        double coins = plugin.getEco().getBalance(target);
                                        plugin.getEco().withdrawPlayer(target, coins);
                                        MySQLManager.changeCoins(target.getUniqueId().toString(), "0");
                                    } else {
                                        plugin.getEco().createPlayerAccount(target);
                                    }
                                    player.sendMessage(plugin.getPrefix() + "§7Du hast erfolgreich die Coins vom Spieler §e" + target.getName() + "§7zurückgesetzt.");
                                }else {
                                    if (plugin.getEco().hasAccount(offlinePlayer)) {
                                        double coins = plugin.getEco().getBalance(offlinePlayer);
                                        plugin.getEco().withdrawPlayer(offlinePlayer, coins);
                                        MySQLManager.changeCoins(offlinePlayer.getUniqueId().toString(), "0");
                                    } else {
                                        plugin.getEco().createPlayerAccount(offlinePlayer);
                                    }
                                    player.sendMessage(plugin.getPrefix() + "§7Du hast erfolgreich die Coins vom Spieler §e" + offlinePlayer.getName() + "§7zurückgesetzt.");
                                }
                            }else {
                                player.sendMessage(plugin.getPrefix() + "§cDazu haust du leider keine Rechte!");
                            }
                        }
                    }else if (args.length == 3) {
                        OfflinePlayer offlinePlayer = Bukkit.getPlayer(args[1]);
                        Player target = Bukkit.getPlayer(args[1]);
                        if (args[0].equalsIgnoreCase("remove")) {
                            if (player.hasPermission("coinsapi.remove") || player.hasPermission("coinsapi.*")) {
                                if (target != null) {
                                    if (plugin.getEco().hasAccount(target)) {
                                        if (plugin.getEco().getBalance(target) >= Double.parseDouble(args[2])) {
                                            plugin.getEco().withdrawPlayer(target, Integer.parseInt(args[2]));
                                            int currentCoins = Integer.parseInt(Objects.requireNonNull(MySQLManager.getCoins(target.getUniqueId().toString())));
                                            if (currentCoins >= Integer.parseInt(args[2])) {
                                                MySQLManager.changeCoins(target.getUniqueId().toString(), String.valueOf(currentCoins - Integer.parseInt(args[2])));
                                            }else {
                                                player.sendMessage(plugin.getPrefix() + "§cDer Spieler §e" + target.getName() + " §chat nicht genügend Coins!");
                                            }
                                            player.sendMessage(plugin.getPrefix() + "§7Du hast erfolgreich§e" + args[2] + " §7Coins von dem Spieler §e" + target.getName() + "§7entfernt.");
                                        }else {
                                            player.sendMessage(plugin.getPrefix() + "§cDer Spieler §e" + target.getName() + " §chat nicht genügend Coins zum entfernen!");
                                        }
                                    }else {
                                        player.sendMessage(plugin.getPrefix() + "§cDu kannst leider §e" + args[2] + " §cCoins von dem Spieler §e" + target.getName() + "§centfernen, weil er keine Coins hat!");
                                    }
                                }else {
                                    if (plugin.getEco().hasAccount(offlinePlayer)) {
                                        if (plugin.getEco().getBalance(offlinePlayer) >= Double.parseDouble(args[2])) {
                                            plugin.getEco().withdrawPlayer(offlinePlayer, Integer.parseInt(args[2]));
                                            int currentCoins = Integer.parseInt(Objects.requireNonNull(MySQLManager.getCoins(offlinePlayer.getUniqueId().toString())));
                                            if (currentCoins >= Integer.parseInt(args[2])) {
                                                MySQLManager.changeCoins(offlinePlayer.getUniqueId().toString(), String.valueOf(currentCoins - Integer.parseInt(args[2])));
                                            }else {
                                                player.sendMessage(plugin.getPrefix() + "§cDer Spieler §e" + offlinePlayer.getName() + " §chat nicht genügend Coins!");
                                            }
                                            player.sendMessage(plugin.getPrefix() + "§7Du hast erfolgreich§e" + args[2] + " §7Coins von dem Spieler §e" + offlinePlayer.getName() + "§7entfernt.");
                                        }else {
                                            player.sendMessage(plugin.getPrefix() + "§cDer Spieler §e" + offlinePlayer.getName() + " §chat nicht genügend Coins zum entfernen!");
                                        }
                                    }else {
                                        player.sendMessage(plugin.getPrefix() + "§cDu kannst leider §e" + args[2] + " §cCoins von dem Spieler §e" + offlinePlayer.getName() + "§centfernen, weil er keine Coins hat!");
                                    }
                                }
                            }else {
                                player.sendMessage(plugin.getPrefix() + "§cDazu haust du leider keine Rechte!");
                            }
                        }else if (args[0].equalsIgnoreCase("add")) {
                            if (player.hasPermission("coinsapi.add") || player.hasPermission("coinsapi.*")) {
                                if (target != null) {
                                    if (!plugin.getEco().hasAccount(target)) {
                                        plugin.getEco().createPlayerAccount(target);
                                    }
                                    plugin.getEco().depositPlayer(target, Integer.parseInt(args[2]));
                                    String currentCoins = MySQLManager.getCoins(target.getUniqueId().toString());
                                    if (currentCoins != null) {
                                        int coins = Integer.parseInt(currentCoins) + Integer.parseInt(args[2]);
                                        MySQLManager.changeCoins(target.getUniqueId().toString(), String.valueOf(coins));
                                    }else {
                                        MySQLManager.changeCoins(target.getUniqueId().toString(), args[2]);
                                    }
                                    player.sendMessage(plugin.getPrefix() + "§7Du hast erfolgreich dem Spieler §e" + target.getName() + " §e" + args[2] + " §7Coins gegeben.");
                                    target.sendMessage(plugin.getPrefix() + "§7Du hast §e" + args[2] + " §7Coins bekommen.");
                                }else {
                                    if (!plugin.getEco().hasAccount(offlinePlayer)) {
                                        plugin.getEco().createPlayerAccount(offlinePlayer);
                                    }
                                    plugin.getEco().depositPlayer(offlinePlayer, Integer.parseInt(args[2]));
                                    String currentCoins = MySQLManager.getCoins(offlinePlayer.getUniqueId().toString());
                                    if (currentCoins != null) {
                                        int coins = Integer.parseInt(currentCoins) + Integer.parseInt(args[2]);
                                        MySQLManager.changeCoins(offlinePlayer.getUniqueId().toString(), String.valueOf(coins));
                                    }else {
                                        MySQLManager.changeCoins(offlinePlayer.getUniqueId().toString(), args[2]);
                                    }
                                    player.sendMessage(plugin.getPrefix() + "§7Du hast erfolgreich dem Spieler §e" + offlinePlayer.getName() + " §e" + args[2] + " §7Coins gegeben.");
                                }
                            }else {
                                player.sendMessage(plugin.getPrefix() + "§cDazu haust du leider keine Rechte!");
                            }
                        }else if (args[0].equalsIgnoreCase("set")) {
                            if (player.hasPermission("coinsapi.set") || player.hasPermission("coinsapi.*")) {
                                if (target != null) {
                                    if (plugin.getEco().hasAccount(target)) {
                                        double coins = plugin.getEco().getBalance(target);
                                        plugin.getEco().withdrawPlayer(target, coins);
                                        plugin.getEco().depositPlayer(target, Integer.parseInt(args[2]));
                                        MySQLManager.changeCoins(target.getUniqueId().toString(), args[2]);
                                        player.sendMessage(plugin.getPrefix() + "§7Du hast erfolgreich dem Spieler §e" + target.getName() + " §7die Coins auf §e" + args[2] + "§7Coins gesetzt.");
                                    }
                                }else {
                                    if (plugin.getEco().hasAccount(offlinePlayer)) {
                                        double coins = plugin.getEco().getBalance(offlinePlayer);
                                        plugin.getEco().withdrawPlayer(offlinePlayer, coins);
                                        plugin.getEco().depositPlayer(offlinePlayer, Integer.parseInt(args[2]));
                                        MySQLManager.changeCoins(offlinePlayer.getUniqueId().toString(), args[2]);
                                        player.sendMessage(plugin.getPrefix() + "§7Du hast erfolgreich dem Spieler §e" + offlinePlayer.getName() + " §7die Coins auf §e" + args[2] + "§7Coins gesetzt.");
                                    }
                                }
                            }else {
                                player.sendMessage(plugin.getPrefix() + "§cDazu haust du leider keine Rechte!");
                            }
                        }
                    }else {
                        player.sendMessage(plugin.getPrefix() + "§cBitte nutze: §e/coins help §czum richtigen ausführen des Kommandos!");
                    }
                }else {
                    if (player.hasPermission("coinsapi.add") || player.hasPermission("coinsapi.remove") || player.hasPermission("coinsapi.reset") || player.hasPermission("coinsapi.set") || player.hasPermission("coinsapi.*")) {
                        player.sendMessage(plugin.getPrefix() + "§cBitte installiere Vault, um die CoinsAPI richtig zu nutzen!");
                    }else {
                        player.sendMessage(plugin.getPrefix() + "§cLeider kannst du das nicht machen!");
                    }
                }
            }
        }else {
            sender.sendMessage("[CoinsAPI] Please go on the Server and try again");
        }
        return true;
    }
}