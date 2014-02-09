/**
 * Project : SR-PlayerRider
 * Package : net.siriuser.playerrider
 * Created : 2014/02/01 - 2:54:02
 */
package net.siriuser.playerrider;

import net.syamn.utils.LogUtil;

import net.syamn.utils.Metrics;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

/**
 * @author SiriuseR
 *
 */
public class PlayerRider extends JavaPlugin {
    private static PlayerRider instance;

    @Override
    public void onEnable() {
        LogUtil.init(this);

        PluginManager pm = getServer().getPluginManager();

        PluginDescriptionFile pdfFile = this.getDescription();
        LogUtil.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " is enabled!");

        setupMetrics();
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);

        PluginDescriptionFile pdfFile = this.getDescription();
        LogUtil.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " is disabled!");
    }

    private void setupMetrics() {
        try {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException ex) {
            LogUtil.warning("[PlayerRider] cant send metrics data!");
            ex.printStackTrace();
        }
    }

    /**
     * @return Kernel Instance
     */
    public static PlayerRider getInstance() {
        return instance;
    }
}
