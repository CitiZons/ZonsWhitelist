package com.citizons.dev.whitelist;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Objects;
import java.util.UUID;

public class ZCommandHandler implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("zonsw")) {
            if (args.length == 2 && Objects.equals(args[0], "add")) {
                ZDataHandler.updateWhitelist(UUID.fromString(args[1].toLowerCase()));
                ZDataHandler.saveWhitelist();
                PluginMain.instance.getLogger().info(
                        String.format("Added UUID to whitelist: %s", args[1]));
                sender.sendMessage("Successfully added UUID to whitelist");
                return true;
            }
            if (args.length == 1 && Objects.equals(args[0], "reload")) {
                PluginMain.instance.loadConfigs();
                PluginMain.instance.getLogger().info("Reload complete");
                return true;
            }
        }
        return false;
    }
}
