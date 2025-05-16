package com.citizons.dev.whitelist;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URI;

public class NetworkManager {
    private final ZonsWhitelist plugin;
    private final boolean enabled_network;
    private final String interface_url;
    private final String authentication_code;

    public NetworkManager(ZonsWhitelist instance) {
        this.plugin = instance;
        this.enabled_network = instance.dataMgr.getConfig().getBoolean("enabled-network");
        this.interface_url = instance.dataMgr.getConfig().getString("server-url");
        this.authentication_code = instance.dataMgr.getConfig().getString("authentication-code");
    }

    public boolean verifyServerStatus() {
        // Verify server status
        try {
            URL url = new URI(this.interface_url + "/authentication").toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                return false;
            }
            String response = connection.getResponseMessage();
            JsonObject json = new Gson().fromJson(response, JsonObject.class);
            if (json.get("status").getAsString().equals("ok")) {
                return true;
            }
            connection.disconnect();
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean verifyPlayer(String playerName, String playerUUID) {
        try {
            URL url = new URL(this.interface_url + "/player/get_info?uuid=" + playerUUID + "&name=" + playerName);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", this.authentication_code);
            connection.setConnectTimeout(5000);
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                return false;
            }
            String response = connection.getResponseMessage();
            JsonObject json = new Gson().fromJson(response, JsonObject.class);
            if (!json.get("whitelisted").getAsString().equals("true")) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
