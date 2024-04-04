package com.citizons.dev.whitelist;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.UUID;

public final class ZEventHandler implements Listener {

    @EventHandler
    public void onProfileWhitelistVerify(AsyncPlayerPreLoginEvent event) {
        if (!ZDataHandler.isEnabled())
            return;
        String playerName = event.getName();
        UUID playerUniqueID = event.getUniqueId();
        String message = ChatColor.translateAlternateColorCodes(
                '§', PluginMain.config
                        .getString("not-whitelisted-message",
                                "You are not whitelisted."));
        PluginMain.instance.getLogger().info(
                String.format("Player join: %s - %s",
                        playerName, playerUniqueID));
        if (ZDataHandler.isPlayerCanJoin(playerUniqueID, playerName)) {
            event.allow();
            return;
        }
        Bukkit.broadcastMessage(
                String.format(
                        "Non-whitelisted player %s has been denied to join the server."
                        , playerName));
        event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, message);
    }
}
