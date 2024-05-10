package com.citizons.dev.whitelist;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.UUID;
import java.util.logging.Logger;

public final class ZEventHandler implements Listener {
    static Logger log = PluginMain.log;

    @EventHandler
    public void onProfileWhitelistVerify(AsyncPlayerPreLoginEvent event) {
        if (!ZDataHandler.isEnabled())
            return;
        String playerName = event.getName();
        UUID playerUniqueID = event.getUniqueId();
        String message = ChatColor.translateAlternateColorCodes(
                '§', PluginMain.config
                        .getString("not-whitelisted-message",
                                "[ZonsW]You are not whitelisted."));
        log.info(String.format("Player join: %s - %s", playerName, playerUniqueID));
        if (ZDataHandler.isPlayerCanJoin(playerUniqueID, playerName)) {
            event.allow();
            return;
        }
        event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, message);
        log.info(String.format("Non-whitelisted player %s has been denied to join the server.", playerName));
        log.info(String.format("Player UUID: %s", playerUniqueID));
        log.info("Use /zonsw list to see the whitelisted players.");
    }
}
