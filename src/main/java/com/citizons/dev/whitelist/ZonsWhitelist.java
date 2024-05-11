package com.citizons.dev.whitelist;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

public final class ZonsWhitelist extends JavaPlugin {

    static ZonsWhitelist instance = null;
    static FileConfiguration config = null;
    static Logger log = null;

    @Override
    public void onEnable() {
        log = getLogger();
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new EventManager(), this);
        Objects.requireNonNull(this.getCommand("zonsw")).setExecutor(new CommandManager());
        instance = this;
        loadConfigs();
    }

    @Override
    public void onDisable() {
        getLogger().info("Saving configs before disable");
        saveConfig();
    }

    public void loadConfigs() {
        config = getConfig();
        DataManager.removeLists();
        DataManager.updateWhitelistEnabled(
                config.getBoolean("is-whitelist-enabled", false));
        var whitelistedPlayers = config.getList("whitelisted-players");
        var blacklistedPlayers = config.getList("blacklisted-players");
        if (whitelistedPlayers != null) {
            for (Object whitelistedPlayer : whitelistedPlayers) {
                try {
                    DataManager.updateWhitelist(
                            UUID.fromString((String) whitelistedPlayer)
                    );
                } catch (Exception error) {
                    log.info(String.format(
                            "load config failed due to %s", error));
                }
            }
        }
        if (blacklistedPlayers != null) {
            for (Object blacklistedPlayer : blacklistedPlayers) {
                try {
                    DataManager.updateBlacklist(
                            UUID.fromString((String) blacklistedPlayer), true
                    );
                } catch (Exception error) {
                    log.info(String.format(
                            "load config failed due to %s", error));
                }
            }
        }
    }
}
