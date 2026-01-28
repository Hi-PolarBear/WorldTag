package worldtag.example.worldTag;

import org.bukkit.plugin.java.JavaPlugin;
import worldtag.example.worldTag.listener.ChatListener;

public final class WorldTag extends JavaPlugin {

    private static WorldTag instance;
    private static final String LOG_SEPARATOR = "=================================";
    private static final String PLUGIN_NAME = "WorldTag";
    private static final String VERSION = "1.2.2";
    private static final String AUTHOR = "Nice_Cam_ Luniverse_vix";
    
    @Override
    public void onEnable() {
        instance = this;
        
        try {

            saveDefaultConfig();
            

            getServer().getPluginManager().registerEvents(new ChatListener(), this);
            

            logStartupMessages();
            
            getLogger().info(PLUGIN_NAME + " 插件启用完成！");
        } catch (Exception e) {
            logErrorMessage(e);
            getServer().getPluginManager().disablePlugin(this);
        }
    }
    
    @Override
    public void onDisable() {
        getLogger().info(LOG_SEPARATOR);
        getLogger().info(PLUGIN_NAME + " 插件已卸载！");
        getLogger().info("感谢使用，期待再次为您服务！");
        getLogger().info(LOG_SEPARATOR);
    }


    public static WorldTag getInstance() {
        return instance;
    }
    

    private void logStartupMessages() {
        getLogger().info(LOG_SEPARATOR);
        getLogger().info(PLUGIN_NAME + " 插件已成功加载！");
        getLogger().info("插件版本: " + VERSION);
        getLogger().info("作者: " + AUTHOR);
        getLogger().info("欢迎使用 " + PLUGIN_NAME + " 插件！");
        getLogger().info(LOG_SEPARATOR);
    }
    

    private void logErrorMessage(Exception e) {
        getLogger().severe(LOG_SEPARATOR);
        getLogger().severe(PLUGIN_NAME + " 插件加载失败！");
        getLogger().severe("错误类型: " + e.getClass().getSimpleName());
        getLogger().severe("错误详情: " + e.getMessage());
        getLogger().severe("请检查配置文件或联系开发者获取支持");
        e.printStackTrace();
        getLogger().severe(LOG_SEPARATOR);
    }
}