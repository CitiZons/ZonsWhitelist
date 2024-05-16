package com.citizons.dev.whitelist;

public class NetworkManager {
    private final ZonsWhitelist plugin;
    private final boolean enabled_network;
    private final String interface_url;
    private final String authentication_code;

    public NetworkManager(ZonsWhitelist instance) {
        this.plugin = instance;
        this.interface_url = instance.dataMgr.getConfig().getString("server-url");
        this.enabled_network = instance.dataMgr.getConfig().getBoolean("enabled-network");
        this.authentication_code = instance.dataMgr.getConfig().getString("authencation-code");
    }


}
