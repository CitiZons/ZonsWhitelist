# ZonsWhitelist

[中文文档](README_zh.md)


ZonsWhitelist 是一个基于 Bukkit 的插件，用于管理 Minecraft 服务器的白名单功能。它支持本地白名单存储和服务端动态验证，提供灵活的白名单管理方式。
ZonsWhitelist is a Bukkit-based plugin for managing the whitelist functionality of Minecraft servers. It supports local whitelist storage and server-side dynamic verification, providing a flexible way to manage whitelists.

## Features

- **Local Whitelist Management**: Supports adding, removing, and saving whitelists to local files.
- **Server-Side Dynamic Verification**: Real-time verification of players on the whitelist via server API.
- **Configurable Support**: Customize plugin behavior through the `config.yml` file.
- **Asynchronous Operations**: Avoid blocking the main thread to improve server performance.

## Installation

1. Download the plugin `.jar` file.
2. Place the `.jar` file into the server's `plugins` folder.
3. Start the server to generate the default configuration file.
4. Modify the `config.yml` file as needed, then restart the server.

## Configuration File

Example `config.yml`:

```yaml
not-whitelisted-message: "[ZonsW] You are not whitelisted."
whitelisted-players: []
is-whitelist-enabled: true
is-username-enabled: false
# Online authentications
## All arguments cannot be modified in server console
## Modify the configurations before you start the server
## For api protocol, please visit https://github.com/CitiZons/ZonsWhitelist and see README
enabled-network: false
server-url: "https://user.citizons.com/api"
authentication-code: "authentication"
```

- enabled-network: Whether to enable server-side verification.
- server-url: The base URL of the server API.
- authentication-code: The authorization code required for server-side verification.
- is-whitelist-enabled: Whether to enable the whitelist functionality.
- is-username-enabled: Whether to allow verification by username. 


## Commands

/whitelist add <player>: Add a player to the whitelist.
/whitelist del <player>: Remove a player from the whitelist.
/whitelist enable [yes|no]: Enable or disable the whitelist functionality.


&copy; CitiZons 2024
