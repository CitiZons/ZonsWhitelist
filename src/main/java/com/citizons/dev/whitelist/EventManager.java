package com.citizons.dev.whitelist;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.UUID;
import java.util.logging.Logger;

public final class EventManager implements Listener {
    static Logger log = ZonsWhitelist.log;

    @EventHandler
    public void onProfileWhitelistVerify(AsyncPlayerPreLoginEvent event) {
        if (!DataManager.isEnabled())
            return;
        String playerName = event.getName();
        UUID playerUniqueID = event.getUniqueId();
        String message = ChatColor.translateAlternateColorCodes(
                '§', ZonsWhitelist.config
                        .getString("not-whitelisted-message",
                                "[ZonsW]You are not whitelisted."));
        log.info(String.format("Player join: %s - %s", playerName, playerUniqueID));
        if (DataManager.isPlayerCanJoin(playerUniqueID, playerName)) {
            event.allow();
            return;
        }
        event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, message);
        log.info(String.format("Non-whitelisted player %s has been denied to join the server.", playerName));
        log.info(String.format("Player UUID: %s", playerUniqueID));
        log.info("Use /zonsw list to see the whitelisted players.");
    }
}
