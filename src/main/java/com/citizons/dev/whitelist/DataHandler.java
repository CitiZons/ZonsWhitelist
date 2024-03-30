package com.citizons.dev.whitelist;

import java.util.UUID;
import java.util.ArrayList;
import java.util.List;

public class DataHandler {
    private static boolean isEnabledWhitelist = true;
    private static final List<UUID> whitelistedPlayers = new ArrayList<UUID>();
    private static final List<UUID> blacklistedPlayers = new ArrayList<UUID>();
    public static boolean isEnabled() {
        return isEnabledWhitelist;
    }
    public static void updateWhitelistEnabled(boolean IsEnabled) {
        isEnabledWhitelist = IsEnabled;
    }
    public static boolean isPlayerCanJoin(UUID UniqueID, String PlayerName) {
        if(!whitelistedPlayers.contains(UniqueID))
            return false;
        return blacklistedPlayers.contains(UniqueID);
    }
    public static void updateWhitelist(UUID UniqueID) {
            if (!whitelistedPlayers.contains(UniqueID))
                whitelistedPlayers.add(UniqueID);
        blacklistedPlayers.remove(UniqueID);
//        } else {
//            if (whitelistedPlayers.contains(UniqueID))
//                whitelistedPlayers.remove(UniqueID);
//        }
    }
}
