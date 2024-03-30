package com.citizons.dev.whitelist;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ZDataHandler {
    private static final List<UUID> whitelistedPlayers = new ArrayList<>();
    private static final List<UUID> blacklistedPlayers = new ArrayList<>();
    private static boolean isEnabledWhitelist = true;

    public static boolean isEnabled() {
        return isEnabledWhitelist;
    }

    public static void updateWhitelistEnabled(boolean IsEnabled) {
        isEnabledWhitelist = IsEnabled;
    }

    public static boolean isPlayerCanJoin(UUID UniqueID, String PlayerName) {
        if (!whitelistedPlayers.contains(UniqueID))
            return false;
        return !blacklistedPlayers.contains(UniqueID);
    }

    public static void updateWhitelist(UUID UniqueID) {
        if (!whitelistedPlayers.contains(UniqueID))
            whitelistedPlayers.add(UniqueID);
    }

    public static void updateBlacklist(UUID UniqueID) {
        if (!blacklistedPlayers.contains(UniqueID))
            blacklistedPlayers.add(UniqueID);
        whitelistedPlayers.remove(UniqueID);
    }

    public static void saveWhitelist() {
        PluginMain.config.set("whitelisted-players", whitelistedPlayers);
        PluginMain.instance.saveConfig();
    }
}
