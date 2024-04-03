package com.citizons.dev.whitelist;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

public final class PluginMain extends JavaPlugin {

    static PluginMain instance = null;
    static FileConfiguration config = null;
    static Logger log = null;

    @Override
    public void onEnable() {
        // Plugin startup logic
        log = getLogger();
        log.info("Zons Whitelist is initializing.");
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new ZEventHandler(), this);
        Objects.requireNonNull(this.getCommand("zonsw")).setExecutor(new ZCommandHandler());
        instance = this;
        loadConfigs();
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling Zons Whitelist.");
        saveConfig();
    }

    public void loadConfigs() {
        config = getConfig();
        ZDataHandler.updateWhitelistEnabled(
                config.getBoolean("is-whitelist-enabled", false));
        var whitelistedPlayers = config.getList("whitelisted-players");
        var blacklistedPlayers = config.getList("blacklisted-players");
        if (whitelistedPlayers != null) {
            for (Object whitelistedPlayer : whitelistedPlayers) {
                try {
                    ZDataHandler.updateWhitelist(
                            UUID.fromString((String) whitelistedPlayer)
                    );
                } catch (Exception error) {
                    log.info(String.format(
                            "load config failed due to %s", error.toString()));
                }
            }
        }
        if (blacklistedPlayers != null) {
            for (Object blacklistedPlayer : blacklistedPlayers) {
                ZDataHandler.updateBlacklist(
                        UUID.fromString((String) blacklistedPlayer)
                );
            }
        }
    }
}
