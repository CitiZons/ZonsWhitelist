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
        boolean result = false;
        if (command.getName().equalsIgnoreCase("zonsw")) {
            if (args.length == 2 && Objects.equals(args[0], "add")) {
                ZDataHandler.updateWhitelist(UUID.fromString(args[1].toLowerCase()));
                ZDataHandler.saveWhitelist();
                log.info(
                        String.format("Added UUID to whitelist: %s", args[1]));
                sender.sendMessage("Successfully added UUID to whitelist");
                result = true;
            } else if (args.length == 1 && Objects.equals(args[0], "reload")) {
                PluginMain.instance.loadConfigs();
                log.info("Reload complete");
                sender.sendMessage("Reload config complete");
                result = true;
            }
        }
        return result;
    }
}
