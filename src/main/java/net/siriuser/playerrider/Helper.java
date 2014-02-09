package net.siriuser.playerrider;

import net.siriuser.playerrider.storage.ConfigurationManager;
import net.syamn.utils.LogUtil;
import net.syamn.utils.queue.ConfirmQueue;

public class Helper {
    private static Helper instance = new Helper();
    private static long mainThreadID;
    private static long pluginStarted;

    private PlayerRider plugin;
    private ConfigurationManager config;

    public static Helper getInstance(){
        return instance;
    }

    public static void dispose() {
        instance = null;
    }

    private void init(final boolean startup) {
        try {
            config.loadConfig(true);
        } catch (Exception ex) {
            LogUtil.warning("an error occured while trying to load the config file.");
            ex.printStackTrace();
        }

        ConfirmQueue.getInstance();
    }

    public void setMainPlugin(final PlayerRider plugin) {
        mainThreadID = Thread.currentThread().getId();
        this.plugin = plugin;
        this.config = new ConfigurationManager(plugin);

        init(true);
    }

    public void disableAll() {
        ConfirmQueue.dispose();
    }

    public synchronized void relaod() {
        disableAll();
        System.gc();
        init(false);
    }

    public ConfigurationManager getConfig() {
        return config;
    }
}
