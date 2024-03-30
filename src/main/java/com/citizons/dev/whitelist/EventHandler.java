package com.citizons.dev.whitelist;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.UUID;

public final class EventHandler implements Listener {

    @org.bukkit.event.EventHandler
    public void onProfileWhitelistVerify(AsyncPlayerPreLoginEvent event) {
        if(!DataHandler.isEnabled())
            return;
        var playerName = event.getPlayerProfile().getName();
        UUID playerUniqueID = event.getPlayerProfile().getId();
        var message = ChatColor.translateAlternateColorCodes(
                '§', PluginMain.config
                        .getString("not-whitelisted-message",
                                "You are not whitelisted."));
        if (playerUniqueID != null) {
            PluginMain.instance.getLogger().info(
                    String.format("Player join: %s - %s",
                            playerName, playerUniqueID));
            if (playerName != null &&
                    DataHandler.isPlayerCanJoin(playerUniqueID, playerName)) {
                event.allow();
        }
        } else {
            event.disallow(
                    AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST,
                    Component.text(message));
        }
    }
}
