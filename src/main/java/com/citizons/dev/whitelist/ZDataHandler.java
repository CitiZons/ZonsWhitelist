package com.citizons.dev.whitelist;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ZDataHandler {
    private static final List<String> whitelistedPlayers = new ArrayList<>();
    private static final List<String> blacklistedPlayers = new ArrayList<>();
    private static boolean isEnabledWhitelist = true;

    public static boolean isEnabled() {
        return isEnabledWhitelist;
    }

    public static void updateWhitelistEnabled(boolean IsEnabled) {
        isEnabledWhitelist = IsEnabled;
    }

    public static boolean isPlayerCanJoin(UUID UniqueID, String PlayerName) {
        String uuid = UniqueID.toString();
        if (!whitelistedPlayers.contains(uuid))
            return false;
        return !blacklistedPlayers.contains(uuid);
    }

    public static void updateWhitelist(UUID UniqueID) {
        String uuid = UniqueID.toString();
        if (!whitelistedPlayers.contains(uuid))
            whitelistedPlayers.add(uuid);
    }

    public static void updateBlacklist(UUID UniqueID) {
        String uuid = UniqueID.toString();
        if (!blacklistedPlayers.contains(uuid))
            blacklistedPlayers.add(uuid);
        whitelistedPlayers.remove(uuid);
    }

    public static void saveWhitelist() {
        PluginMain.config.set("whitelisted-players", whitelistedPlayers);
        PluginMain.instance.saveConfig();
    }
}
