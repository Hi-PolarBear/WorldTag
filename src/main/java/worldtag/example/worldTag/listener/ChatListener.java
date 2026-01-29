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
            return;
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

        String tagText;

        if (config.contains("worlds." + worldName + ".name")) {
            try {
                tagText = config.getString("worlds." + worldName + ".name");

                if (tagText == null || tagText.isEmpty()) {
                    tagText = worldName;
                }
            } catch (Exception e) {
                WorldTag.getInstance().getLogger().warning("读取世界 " + worldName + " 的配置时出错: " + e.getMessage());
                tagText = ChatColor.RED + "ERROR";
            }
        } else {

            String defaultText = config.getString("default.unknown_world_text", "&7未知");
            tagText = defaultText == null ? "&7未知" : defaultText;
        }


        String formattedTag = translateColorCodes(tagText);

        String tag = ChatColor.WHITE + "[" + formattedTag + ChatColor.WHITE + "] ";


        String format = event.getFormat();
        if (format == null || format.isEmpty()) {
            format = "%s: %s";
        }
        event.setFormat(tag + format);
    }

    private String translateColorCodes(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }


        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
