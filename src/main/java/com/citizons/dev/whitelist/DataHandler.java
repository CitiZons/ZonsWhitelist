package com.citizons.dev.whitelist;

import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

public class DataHandler {
    private static boolean isEnabledWhitelist = true;
    private static final List<UUID> whitelistedPlayers = new ArrayList<>();
    private static final List<UUID> blacklistedPlayers = new ArrayList<>();
    public static boolean isEnabled() {
        return isEnabledWhitelist;
    }
    public static void updateWhitelistEnabled(boolean IsEnabled) {
        isEnabledWhitelist = IsEnabled;
    }
    public static boolean isPlayerCanJoin(UUID UniqueID, String PlayerName) {
        if(!whitelistedPlayers.contains(UniqueID))
            return false;
        return whitelistedPlayers.contains(UniqueID);
    }
    public static void updateWhitelist(UUID UniqueID) {
        if (!whitelistedPlayers.contains(UniqueID))
            whitelistedPlayers.add(UniqueID);
        blacklistedPlayers.remove(UniqueID);
        PluginMain.config.set("whitelisted-players", whitelistedPlayers.toString());
        PluginMain.instance.saveConfig();
//        } else {
//            if (whitelistedPlayers.contains(UniqueID))
//                whitelistedPlayers.remove(UniqueID);
//        }
    }
}
