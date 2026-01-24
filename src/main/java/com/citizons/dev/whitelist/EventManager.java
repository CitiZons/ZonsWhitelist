package com.citizons.dev.whitelist;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.util.logging.Logger;

public final class EventManager implements Listener {
    private final Logger logger;
    private final ZonsWhitelist plugin;

    public EventManager(ZonsWhitelist instance) {
        this.plugin = instance;
        this.logger = instance.getLogger();
    }

    @EventHandler
    public void onProfileWhitelistVerify(AsyncPlayerPreLoginEvent event) {
        if (!plugin.dataMgr.isWhitelistEnabled())
            return;
        String playerName = event.getName().toLowerCase();
        String playerUUID = event.getUniqueId().toString().toLowerCase();
        Component message = Component.text(
                plugin.dataMgr.getConfig().getString(
                        "not-whitelisted-message",
                        "[ZonsW] You are not whitelisted."
                ),
                NamedTextColor.WHITE
        );
        logger.info(String.format("Player join: %s - %s", playerName, playerUUID));
        if (plugin.dataMgr.checkPlayerCanJoin(playerUUID)) {
            event.allow();
            return;
        } else if (plugin.dataMgr.checkPlayerCanJoin(playerName) && plugin.dataMgr.isUsernameEnabled()) {
            logger.warning(String.format(
                    "Player %s join with Username verification, and which is not recommended!",
                    playerName));
            event.allow();
            return;
        }
        event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST);
        event.kickMessage(message);
        logger.info(String.format(
                "Non-whitelisted player %s - %s has been denied to join the server.",
                playerName, playerUUID));
        logger.info("Use /zonsw list to see the whitelisted players.");
    }
}
