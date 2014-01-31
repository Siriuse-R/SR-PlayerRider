/**
 * Project : SR-PlayerRider
 * Package : net.siriuser.playerrider
 * Created : 2014/02/01 - 2:54:02
 */
package net.siriuser.playerrider;

import net.syamn.utils.LogUtil;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

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
    }

    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);

        PluginDescriptionFile pdfFile = this.getDescription();
        LogUtil.info(pdfFile.getName() + " version " + pdfFile.getVersion() + " is disabled!");
    }

    /**
     * @return Kernel Instance
     */
    public static PlayerRider getInstance() {
        return instance;
    }
}
