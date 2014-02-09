package net.siriuser.playerrider.storage;

import net.siriuser.playerrider.PlayerRider;
import net.syamn.utils.LogUtil;
import net.syamn.utils.file.FileStructure;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public class ConfigurationManager {
    private final PlayerRider plugin;
    private final int lastVersion = 1;

    private FileConfiguration config;
    private File pluginDir;

    public ConfigurationManager(final PlayerRider plugin) {
        this.plugin = plugin;
        this.pluginDir = plugin.getDataFolder();
    }

    public void loadConfig(final boolean initiaLoad) throws Exception {
        FileStructure.createDir(pluginDir);

        File file = new File(pluginDir, "config.yml");
        if (!file.exists()) {
            FileStructure.extractResource("/config.yml", pluginDir, false, false, plugin);
            LogUtil.info("Config.yml is not found! Created default config.yml!");
        }

        plugin.reloadConfig();
        config = plugin.getConfig();

        checkVer(config.getInt("ConfigVersion"));
    }

    private void checkVer(final int ver) {
        if (ver < lastVersion) {
            final String destName = "oldconfig-v" + ver + ".yml";
            String srcPath = new File(pluginDir, "config.yml").getPath();
            String destPath = new File(pluginDir, destName).getPath();

            try {
                FileStructure.copyTransfer(srcPath, destPath);
                LogUtil.info("Copyed old config.yml to " + destName + "!");
            } catch (Exception ex) {
                LogUtil.warning("Faild to copy old config.yml!");
            }

            FileStructure.extractResource("/config.yml", pluginDir, true, false, plugin);

            plugin.reloadConfig();
            config = plugin.getConfig();

            //TODO: 古いConfigの項目を新しいConfigに反映させる
        }
    }

    /**
     * Debug setting
     */
    public boolean isDebug() {
        return config.getBoolean("Debug", false);
    }
}
