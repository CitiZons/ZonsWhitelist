package com.citizons.dev.whitelist;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class ZonsWhitelist extends JavaPlugin {
    DataManager dataMgr = null;
    CommandManager cmdMgr = null;
    EventManager eventMgr = null;
    NetworkManager netMgr = null;

    @Override
    public void onEnable() {
        this.dataMgr = new DataManager(this);
        this.cmdMgr = new CommandManager(this);
        this.eventMgr = new EventManager(this);
        Bukkit.getPluginManager().registerEvents(eventMgr, this);
        Objects.requireNonNull(this.getCommand("zonsw")).setExecutor(cmdMgr);
    }

    @Override
    public void onDisable() {
        this.dataMgr.saveConfig();
    }
}
