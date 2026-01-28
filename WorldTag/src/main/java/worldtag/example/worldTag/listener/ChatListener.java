package worldtag.example.worldTag.listener;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import worldtag.example.worldTag.WorldTag;

public class ChatListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (player == null) {
            return; // 防止玩家为空
        }

        String worldName = player.getWorld().getName();
        if (worldName == null || worldName.isEmpty()) {
            WorldTag.getInstance().getLogger().warning("无法获取玩家所在的世界名称");
            return;
        }

        FileConfiguration config = WorldTag.getInstance().getConfig();
        if (config == null) {
            WorldTag.getInstance().getLogger().warning("配置文件为空，无法处理聊天事件");
            return;
        }

        String tag;
        if (!config.contains("worlds." + worldName)) {

            String defaultUnknownText = config.getString("default.unknown_world_text", "未知");
            String defaultUnknownColor = config.getString("default.unknown_world_color", "GRAY");
            
            ChatColor unknownColor;
            try {
                unknownColor = ChatColor.valueOf(defaultUnknownColor != null ? defaultUnknownColor.toUpperCase() : "GRAY");
            } catch (IllegalArgumentException e) {
                WorldTag.getInstance().getLogger().warning("无效的默认颜色代码 '" + defaultUnknownColor + "', 使用默认颜色 GRAY");
                unknownColor = ChatColor.GRAY;
            }
            
            tag = ChatColor.WHITE + "[" + unknownColor + (defaultUnknownText != null ? defaultUnknownText : "未知") + ChatColor.WHITE + "] ";
        } else {
            String displayName = null;
            String colorCode = null;
            
            try {
                displayName = config.getString("worlds." + worldName + ".name");
                if (displayName == null) {
                    displayName = worldName;
                }
                
                colorCode = config.getString("worlds." + worldName + ".color");
                if (colorCode == null) {
                    colorCode = "WHITE";
                }
            } catch (Exception e) {
                WorldTag.getInstance().getLogger().warning("读取世界 " + worldName + " 的配置时出错: " + e.getMessage());
                String errorTag = ChatColor.WHITE + "[" + ChatColor.RED + "ERROR" + ChatColor.WHITE + "] ";
                event.setFormat(errorTag + event.getFormat());
                return;
            }

            ChatColor worldColor;
            try {
                worldColor = ChatColor.valueOf(colorCode != null ? colorCode.toUpperCase() : "WHITE");
            } catch (IllegalArgumentException e) {
                WorldTag.getInstance().getLogger().warning("无效的颜色代码 '" + colorCode + "' 用于世界 " + worldName + ", 使用默认颜色 WHITE");
                worldColor = ChatColor.WHITE;
            } catch (Exception e) {
                WorldTag.getInstance().getLogger().warning("处理颜色代码时发生未知错误: " + e.getMessage() + " 用于世界 " + worldName);
                worldColor = ChatColor.WHITE;
            }

            tag = ChatColor.WHITE + "[" + worldColor + (displayName != null ? displayName : worldName) + ChatColor.WHITE + "] ";
        }
        
        String format = event.getFormat();
        if (format == null) {
            format = "%s: %s";
        }
        
        event.setFormat(tag + format);
    }
}
