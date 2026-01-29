package worldtag.example.worldTag;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import worldtag.example.worldTag.listener.ChatListener;

public final class WorldTag extends JavaPlugin {

    private static WorldTag instance;
    private static final String LOG_SEPARATOR = ChatColor.GRAY + "=================================";
    private static final String PLUGIN_NAME = ChatColor.AQUA + "WorldTag";
    private static final String VERSION = "1.2.3";
    private static final String AUTHOR = "Nice_Cam_ Luniverse_vix";

    @Override
    public void onEnable() {
        instance = this;

        try {
            saveDefaultConfig();
            getServer().getPluginManager().registerEvents(new ChatListener(), this);
            logStartupMessages();
            getLogger().info(PLUGIN_NAME + ChatColor.RESET + " 插件启用完成！");
        } catch (Exception e) {
            logErrorMessage(e);
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        getLogger().info(LOG_SEPARATOR);
        getLogger().info(PLUGIN_NAME + ChatColor.RESET + " 插件已卸载！");
        getLogger().info(ChatColor.GREEN + "感谢使用，期待再次为您服务！");
        getLogger().info(LOG_SEPARATOR);
    }

    public static WorldTag getInstance() {
        return instance;
    }

    private void logStartupMessages() {
        getLogger().info(LOG_SEPARATOR);
        getLogger().info(PLUGIN_NAME + ChatColor.RESET + " 插件已成功加载！");
        getLogger().info(ChatColor.YELLOW + "插件版本: " + ChatColor.RESET + VERSION);
        getLogger().info(ChatColor.GOLD + "作者: " + ChatColor.RESET + AUTHOR);
        getLogger().info(ChatColor.LIGHT_PURPLE + "欢迎使用 " + PLUGIN_NAME + ChatColor.RESET + " 插件！");
        getLogger().info(LOG_SEPARATOR);
    }

    private void logErrorMessage(Exception e) {
        getLogger().severe(ChatColor.DARK_RED + LOG_SEPARATOR);
        getLogger().severe(ChatColor.RED + PLUGIN_NAME + ChatColor.RESET + " 插件加载失败！");
        getLogger().severe(ChatColor.GOLD + "错误类型: " + ChatColor.RESET + e.getClass().getSimpleName());
        getLogger().severe(ChatColor.GOLD + "错误详情: " + ChatColor.RESET + e.getMessage());
        getLogger().severe(ChatColor.YELLOW + "请检查配置文件或联系开发者获取支持");
        e.printStackTrace();
        getLogger().severe(ChatColor.DARK_RED + LOG_SEPARATOR);
    }
}