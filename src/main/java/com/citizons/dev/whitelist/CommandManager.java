package com.citizons.dev.whitelist;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

public class CommandManager implements CommandExecutor {
    static Logger log = ZonsWhitelist.log;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("zonsw")) {
            return false;
        }
        if (args.length == 3) {
            if (Objects.equals(args[0].toLowerCase(), "whitelist")) {
                if (Objects.equals(args[1].toLowerCase(), "add")) {
                    DataManager.updateWhitelist(UUID.fromString(args[2].toLowerCase()));
                    DataManager.saveWhitelist();
                    log.info(String.format("Added UUID to whitelist: %s", args[2]));
                    sender.sendMessage("Successfully added UUID to whitelist");
                    return true;
                }
                if (Objects.equals(args[1].toLowerCase(), "del")) {
                    DataManager.updateWhitelist(UUID.fromString(args[2].toLowerCase()), false);
                    DataManager.saveWhitelist();
                    log.info(String.format("Deleted UUID from whitelist: %s", args[2]));
                    sender.sendMessage("Successfully deleted UUID from whitelist");
                    return true;
                }
            }
            if (Objects.equals(args[0].toLowerCase(), "blacklist")) {
                if (Objects.equals(args[1].toLowerCase(), "add")) {
                    DataManager.updateBlacklist(UUID.fromString(args[2].toLowerCase()), true);
                    DataManager.saveBlacklist();
                    log.info(String.format("Added UUID to blacklist: %s", args[2]));
                    sender.sendMessage("Successfully added UUID to blacklist");
                    return true;
                }
                if (Objects.equals(args[1].toLowerCase(), "del")) {
                    DataManager.updateBlacklist(UUID.fromString(args[2].toLowerCase()), false);
                    DataManager.saveBlacklist();
                    log.info(String.format("Deleted UUID from blacklist: %s", args[2]));
                    sender.sendMessage("Successfully deleted UUID from blacklist");
                    return true;
                }
            }
        }
        if (args.length == 2) {
            if (Objects.equals(args[0].toLowerCase(), "add")) {
                DataManager.updateWhitelist(UUID.fromString(args[1].toLowerCase()));
                DataManager.saveWhitelist();
                log.info(String.format("Added UUID to whitelist: %s", args[1]));
                sender.sendMessage("Successfully added UUID to whitelist");
                return true;
            }
            if (Objects.equals(args[0].toLowerCase(), "del")) {
                DataManager.updateWhitelist(UUID.fromString(args[1].toLowerCase()), false);
                DataManager.saveWhitelist();
                log.info(String.format("Deleted UUID from whitelist: %s", args[1]));
                sender.sendMessage("Successfully deleted UUID from whitelist");
                return true;
            }
            if (Objects.equals(args[0].toLowerCase(), "enable")) {
                if (Objects.equals(args[1], "yes")) {
                    DataManager.updateWhitelistEnabled(true);
                    log.info(String.format("Whitelist enabled - user %s", sender.getName()));
                    sender.sendMessage("Whitelist enabled");
                    return true;
                }
                if (Objects.equals(args[1].toLowerCase(), "no")) {
                    DataManager.updateWhitelistEnabled(false);
                    log.info(String.format("Whitelist disabled - user %s", sender.getName()));
                    sender.sendMessage("Whitelist disabled");
                    return true;
                }
            }
        }
        if (args.length == 1) {
            if (Objects.equals(args[0].toLowerCase(), "reload")) {
                ZonsWhitelist.instance.loadConfigs();
                log.info("Reload complete");
                sender.sendMessage("Reload config complete");
                return true;
            }
            if (Objects.equals(args[0].toLowerCase(), "list")) {
                sender.sendMessage("Whitelisted players:");
                for (String player : DataManager.getWhitelistedPlayers()) {
                    sender.sendMessage(player);
                }
                return true;
            }
            if (Objects.equals(args[0].toLowerCase(), "status")) {
                if (DataManager.isEnabled()) {
                    sender.sendMessage("Whitelist is enabled");
                } else {
                    sender.sendMessage("Whitelist is disabled");
                }
                return true;
            }
            if (Objects.equals(args[0].toLowerCase(), "help")) {
                sender.sendMessage("Commands:");
                sender.sendMessage("/zonsw add <UUID>");
                sender.sendMessage("/zonsw del <UUID>");
                sender.sendMessage("/zonsw whitelist add <UUID>");
                sender.sendMessage("/zonsw whitelist del <UUID>");
                sender.sendMessage("/zonsw blacklist add <UUID>");
                sender.sendMessage("/zonsw blacklist del <UUID>");
                sender.sendMessage("/zonsw enable <yes/no>");
                sender.sendMessage("/zonsw reload");
                sender.sendMessage("/zonsw list");
                sender.sendMessage("/zonsw status");
                return true;
            }
        }
        sender.sendMessage("You've provided invalid command");
        sender.sendMessage("Use /zonsw help to see the list of commands");
        return false;
    }
}
