package com.citizons.dev.whitelist;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

public class DataManager {
    private final ZonsWhitelist plugin;
    private final Logger logger;
    private final FileConfiguration config;
    private final List<String> whitelistedPlayers = new ArrayList<>();
    private boolean isEnabledWhitelist = true;
    private boolean isEnabledUsername = false;

    public DataManager(ZonsWhitelist instance) {
        instance.saveDefaultConfig();
        this.plugin = instance;
        this.logger = instance.getLogger();
        this.config = instance.getConfig();
        var isLoadConfigSuc = this.loadConfigs();
        if (!isLoadConfigSuc) {
            this.logger.warning("Failed to load configs!");
        }
    }

    public boolean loadConfigs() {
        try {
            this.removeLists();
            this.isEnabledWhitelist = config.getBoolean("is-whitelist-enabled", true);
            this.isEnabledUsername = config.getBoolean("is-username-enabled", false);
            var whitelistedPlayers = config.getList("whitelisted-players");
            if (whitelistedPlayers != null) {
                for (var player : whitelistedPlayers) {
                    if (!this.whitelistedPlayers.contains((String) player)) {
                        this.whitelistedPlayers.add((String) player);
                    }
                }
            }

        }
        catch (Exception error) {
            return false;
        }
        return true;
    }

    public boolean isWhitelistEnabled() {
        return this.isEnabledWhitelist;
    }

    public boolean isUsernameEnabled() {
        return this.isEnabledUsername;
    }
    public void updateWhitelistEnabledStatus(boolean isEnabled) {
        this.isEnabledWhitelist = isEnabled;
    }
    public boolean checkCredentialNotUUID(String credential) {
        try {
            var uuid = UUID.fromString(credential);
            return false;
        } catch (Exception error) {
            return true;
        }
    }

    public boolean checkPlayerCanJoin(String credential) {
        if (!this.isUsernameEnabled()) {
            if (checkCredentialNotUUID(credential))
                return false;
        }
        return this.whitelistedPlayers.contains(credential);
    }

    public boolean addWhitelistUser(String credential) {
        if (!this.isUsernameEnabled()) {
            if (checkCredentialNotUUID(credential))
                return false;
        }
        if (!this.whitelistedPlayers.contains(credential))
            this.whitelistedPlayers.add(credential);
        return true;
    }

    public boolean removeWhitelistUser(String credential) {
        if (!this.isUsernameEnabled()) {
            if (checkCredentialNotUUID(credential))
                return false;
        }
        this.whitelistedPlayers.remove(credential);
        return true;
    }

    public void removeLists() {
        this.whitelistedPlayers.clear();
    }

    public List<String> getWhitelistedPlayers() {
        return this.whitelistedPlayers;
    }

    public void saveWhitelist() {
        this.config.set("whitelisted-players", this.whitelistedPlayers);
        plugin.saveConfig();
    }

    public void saveConfig() {
        logger.info("Saving configs...");
        plugin.saveConfig();
    }

    public FileConfiguration getConfig() {
        return this.config;
    }
}
