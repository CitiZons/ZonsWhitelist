# ZonsWhitelist

ZonsWhitelist 是一个基于 Bukkit 的插件，用于管理 Minecraft 服务器的白名单功能。它支持本地白名单存储和服务端动态验证，提供灵活的白名单管理方式。

## 功能特性

- **本地白名单管理**：支持添加、删除和保存白名单到本地文件。
- **服务端动态验证**：通过服务端接口实时验证玩家是否在白名单中。
- **配置化支持**：通过 `config.yml` 文件自定义插件行为。
- **异步操作**：避免阻塞主线程，提升服务器性能。

## 安装

1. 下载插件的 `.jar` 文件。
2. 将 `.jar` 文件放入服务器的 `plugins` 文件夹。
3. 启动服务器以生成默认配置文件。
4. 根据需要修改 `config.yml` 文件，然后重启服务器。

## 配置文件

`config.yml` 示例：

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

- `enabled-network`：是否启用服务端验证。
- `server-url`：服务端 API 的基础 URL。
- `authentication-code`：服务端验证所需的授权码。
- `is-whitelist-enabled`：是否启用白名单功能。
- `is-username-enabled`：是否允许通过用户名验证。

## 指令

- `/whitelist add <玩家名>`：添加玩家到白名单。
- `/whitelist del <玩家名>`：从白名单中移除玩家。
- `/whitelist enable [yes|no]`：启用或禁用白名单功能。


&copy; CitiZons 2024
