package com.citizons.dev.whitelist;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

public class ZCommandHandler implements CommandExecutor {
    static Logger log = PluginMain.log;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("zonsw")) {
            return false;
        }
        if (args.length == 3) {
            if (Objects.equals(args[0], "whitelist")) {
                if (Objects.equals(args[1], "add")) {
                    ZDataHandler.updateWhitelist(UUID.fromString(args[2].toLowerCase()));
                    ZDataHandler.saveWhitelist();
                    log.info(
                            String.format("Added UUID to whitelist: %s", args[2]));
                    sender.sendMessage("Successfully added UUID to whitelist");
                    return true;
                } else if (Objects.equals(args[1], "del")) {
                    ZDataHandler.updateWhitelist(UUID.fromString(args[2].toLowerCase()), false);
                    ZDataHandler.saveWhitelist();
                    log.info(
                            String.format("Deleted UUID from whitelist: %s", args[2]));
                    sender.sendMessage("Successfully deleted UUID from whitelist");
                    return true;
                }
            } else if (Objects.equals(args[0], "blacklist")) {
                if (Objects.equals(args[1], "add")) {
                    ZDataHandler.updateBlacklist(UUID.fromString(args[2].toLowerCase()), true);
                    ZDataHandler.saveBlacklist();
                    log.info(
                            String.format("Added UUID to blacklist: %s", args[2]));
                    sender.sendMessage("Successfully added UUID to blacklist");
                    return true;
                } else if (Objects.equals(args[1], "del")) {
                    ZDataHandler.updateBlacklist(UUID.fromString(args[2].toLowerCase()), false);
                    ZDataHandler.saveBlacklist();
                    log.info(
                            String.format("Deleted UUID from blacklist: %s", args[2]));
                    sender.sendMessage("Successfully deleted UUID from blacklist");
                    return true;
                }
            }
        } else if (args.length == 2) {
            if (Objects.equals(args[0], "Add")) {
                ZDataHandler.updateWhitelist(UUID.fromString(args[1].toLowerCase()));
                ZDataHandler.saveWhitelist();
                log.info(
                        String.format("Added UUID to whitelist: %s", args[1]));
                sender.sendMessage("Successfully added UUID to whitelist");
                return true;
            } else if (Objects.equals(args[0], "del")) {
                ZDataHandler.updateWhitelist(UUID.fromString(args[1].toLowerCase()), false);
                ZDataHandler.saveWhitelist();
                log.info(
                        String.format("Deleted UUID from whitelist: %s", args[1]));
                sender.sendMessage("Successfully deleted UUID from whitelist");
                return true;
            } else if (Objects.equals(args[0], "enable")) {
                if (Objects.equals(args[1], "yes")) {
                    ZDataHandler.updateWhitelistEnabled(true);
                    log.info(String.format("Whitelist enabled - user %s", sender.getName()));
                    sender.sendMessage("Whitelist enabled");
                    return true;
                } else if (Objects.equals(args[1], "no")) {
                    ZDataHandler.updateWhitelistEnabled(false);
                    log.info(String.format("Whitelist disabled - user %s", sender.getName()));
                    sender.sendMessage("Whitelist disabled");
                    return true;
                }
            }
        } else if (args.length == 1 && Objects.equals(args[0], "reload")) {
            PluginMain.instance.loadConfigs();
            log.info("Reload complete");
            sender.sendMessage("Reload config complete");
            return true;
        }
        return false;
    }
}
