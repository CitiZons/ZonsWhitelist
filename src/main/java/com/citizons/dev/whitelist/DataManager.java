package com.citizons.dev.whitelist;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataManager {
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
        addWhitelist(UniqueID);
    }

    public static void updateWhitelist(UUID UniqueID, boolean Add) {
        if (Add)
            addWhitelist(UniqueID);
        else
            removeWhitelist(UniqueID);
    }

    public static void updateBlacklist(UUID UniqueID, boolean Add) {
        String uuid = UniqueID.toString();
        if (Add) {
            if (!blacklistedPlayers.contains(uuid))
                blacklistedPlayers.add(uuid);
            whitelistedPlayers.remove(uuid);
        } else {
            blacklistedPlayers.remove(uuid);
        }
    }

    public static void removeLists() {
        whitelistedPlayers.clear();
        blacklistedPlayers.clear();
    }

    public static List<String> getWhitelistedPlayers() {
        return whitelistedPlayers;
    }

    private static void addWhitelist(UUID UniqueID) {
        String uuid = UniqueID.toString();
        if (!whitelistedPlayers.contains(uuid))
            whitelistedPlayers.add(uuid);
        blacklistedPlayers.remove(uuid);
    }

    private static void removeWhitelist(UUID UniqueID) {
        String uuid = UniqueID.toString();
        whitelistedPlayers.remove(uuid);
    }

    public static void saveWhitelist() {
        ZonsWhitelist.config.set("whitelisted-players", whitelistedPlayers);
        ZonsWhitelist.instance.saveConfig();
    }

    public static void saveBlacklist() {
        ZonsWhitelist.config.set("blacklisted-players", blacklistedPlayers);
        ZonsWhitelist.instance.saveConfig();
    }
}
