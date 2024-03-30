package com.citizons.dev.whitelist;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Objects;
import java.util.UUID;

public class CommandHandler implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("zonsw")) {
            if (Objects.equals(args[0], "add")) {
                DataHandler.updateWhitelist(UUID.fromString(args[1].toLowerCase()));
                PluginMain.instance.getLogger().info(
                        String.format("Added UUID to whitelist: %s", args[1]));
                sender.sendMessage("Successfully added UUID to whitelist");
            }
            if(Objects.equals(args[0], "reload")) {
                PluginMain.instance.getConfig();
                PluginMain.instance.loadConfigs();
            }
            return true;
        } else {
            return false;
        }
    }
}
