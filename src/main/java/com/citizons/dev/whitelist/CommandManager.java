package com.citizons.dev.whitelist;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Objects;
import java.util.logging.Logger;

public class CommandManager implements CommandExecutor {
    private final ZonsWhitelist plugin;
    private final Logger log;

    public CommandManager(ZonsWhitelist instance) {
        this.plugin = instance;
        this.log = instance.getLogger();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("zonsw")) {
            return false;
        }
        if (args.length == 3) {
            if (Objects.equals(args[0].toLowerCase(), "whitelist")) {
                if (Objects.equals(args[1].toLowerCase(), "add")) {
                    this.plugin.dataMgr.addWhitelistUser(args[2].toLowerCase());
                    this.plugin.dataMgr.saveWhitelist();
                    log.info(String.format("Added UUID to whitelist: %s", args[2]));
                    sender.sendMessage("Successfully added UUID to whitelist");
                    return true;
                }
                if (Objects.equals(args[1].toLowerCase(), "del")) {
                    this.plugin.dataMgr.removeWhitelistUser(args[2].toLowerCase());
                    this.plugin.dataMgr.saveWhitelist();
                    log.info(String.format("Deleted UUID from whitelist: %s", args[2]));
                    sender.sendMessage("Successfully deleted UUID from whitelist");
                    return true;
                }
            }
        }
        if (args.length == 2) {
            if (Objects.equals(args[0].toLowerCase(), "add")) {
                this.plugin.dataMgr.addWhitelistUser(args[1].toLowerCase());
                this.plugin.dataMgr.saveWhitelist();
                log.info(String.format("Added UUID to whitelist: %s", args[1]));
                sender.sendMessage("Successfully added UUID to whitelist");
                return true;
            }
            if (Objects.equals(args[0].toLowerCase(), "del")) {
                this.plugin.dataMgr.removeWhitelistUser(args[1].toLowerCase());
                this.plugin.dataMgr.saveWhitelist();
                log.info(String.format("Deleted UUID from whitelist: %s", args[1]));
                sender.sendMessage("Successfully deleted UUID from whitelist");
                return true;
            }
            if (Objects.equals(args[0].toLowerCase(), "enable")) {
                if (Objects.equals(args[1], "yes")) {
                    this.plugin.dataMgr.updateWhitelistEnabledStatus(true);
                    log.info(String.format("Whitelist enabled - user %s", sender.getName()));
                    sender.sendMessage("Whitelist enabled");
                    return true;
                }
                if (Objects.equals(args[1].toLowerCase(), "no")) {
                    this.plugin.dataMgr.updateWhitelistEnabledStatus(true);
                    log.info(String.format("Whitelist disabled - user %s", sender.getName()));
                    sender.sendMessage("Whitelist disabled");
                    return true;
                }
            }
        }
        if (args.length == 1) {
            if (Objects.equals(args[0].toLowerCase(), "reload")) {
                plugin.dataMgr.loadConfigs();
                log.info("Reload complete");
                sender.sendMessage("Reload config complete");
                return true;
            }
            if (Objects.equals(args[0].toLowerCase(), "list")) {
                sender.sendMessage("Whitelisted players:");
                for (String player : this.plugin.dataMgr.getWhitelistedPlayers()) {
                    sender.sendMessage(player);
                }
                return true;
            }
            if (Objects.equals(args[0].toLowerCase(), "status")) {
                if (plugin.dataMgr.isWhitelistEnabled()) {
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
        sender.sendMessage("Use '/zonsw help' to see the list of commands");
        return false;
    }
}
