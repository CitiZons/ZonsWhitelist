package com.citizons.dev.whitelist;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class NetworkManager {
    private final ZonsWhitelist plugin;
    private final boolean enabled_network;
    private final String interface_url;
    private final String authentication_code;
    private final Gson gson = new Gson();

    public NetworkManager(ZonsWhitelist instance) {
        boolean enabledNetwork1;
        this.plugin = instance;
        enabledNetwork1 = instance.dataMgr.getConfig().getBoolean("enabled-network");
        this.interface_url = instance.dataMgr.getConfig().getString("server-url");
        this.authentication_code = instance.dataMgr.getConfig().getString("authentication-code");
        if (enabledNetwork1) {
            boolean serverStatus = this.verifyServerStatus();
            if (!serverStatus) {
                this.plugin.getLogger().warning("Failed to connect to the authentication server. Network features are disabled.");
                enabledNetwork1 = false;
            } else {
                this.plugin.getLogger().info("Successfully connected to the authentication server.");
            }
        }
        this.enabled_network = enabledNetwork1;
    }

    public boolean verifyServerStatus() {
        if (!enabled_network) {
            return false;
        }

        HttpURLConnection connection = null;
        try {
            URL url = new URI(this.interface_url + "/authentication").toURL();
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                return false;
            }

            try (InputStream is = connection.getInputStream();
                 InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
                 BufferedReader br = new BufferedReader(isr)) {

                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                JsonObject json = gson.fromJson(sb.toString(), JsonObject.class);
                return json != null && json.has("status") && "ok".equalsIgnoreCase(json.get("status").getAsString());
            }
        } catch (Exception e) {
            return false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public boolean verifyPlayer(String playerName, String playerUUID) {
        if (!enabled_network) {
            return false;
        }

        HttpURLConnection connection = null;
        try {
            String encodedName = URLEncoder.encode(playerName, StandardCharsets.UTF_8);
            String encodedUuid = URLEncoder.encode(playerUUID, StandardCharsets.UTF_8);
            String urlStr = String.format("%s/player/get_info?uuid=%s&name=%s", this.interface_url, encodedUuid, encodedName);
            URL url = new URI(urlStr).toURL();

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", this.authentication_code);
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                return false;
            }

            try (InputStream is = connection.getInputStream();
                 InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
                 BufferedReader br = new BufferedReader(isr)) {

                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                JsonObject json = gson.fromJson(sb.toString(), JsonObject.class);
                return json != null && json.has("whitelisted") && json.get("whitelisted").getAsBoolean();
            }
        } catch (Exception e) {
            return false;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

}