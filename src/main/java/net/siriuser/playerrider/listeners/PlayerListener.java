package net.siriuser.playerrider.listeners;

import net.siriuser.playerrider.Perms;
import net.siriuser.playerrider.PlayerRider;
import net.syamn.utils.Util;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerListener implements Listener {
    private PlayerRider plugin;
    public PlayerListener (final PlayerRider plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerClicked (final PlayerInteractEntityEvent event) {
        final Player player = event.getPlayer();
        if (event.getRightClicked() instanceof Player) {
            final Player target = (Player) event.getRightClicked();
            if (!duckEjectPassenger(player, target) && playerCanRide(player)) {
                Player vehicle = getVehicle(player);
                if (vehicle == null) {
                    vehicle = (Player) event.getRightClicked();
                    Player duck = getRootVehicle(vehicle);
                    getLastPassenger(vehicle).setPassenger(player);
                    Util.message(player, "&7%PLAYER%が%TARGET%に乗りました！"
                            .replace("%PLAYER%", player.getName())
                            .replace("%TARGET%", target.getName()));
                    Util.message((Player) target, "&7%PLAYER%が%TARGET%に乗りました！"
                            .replace("%PLAYER%", player.getName())
                            .replace("%TARGET%", target.getName()));
                } else {
                    vehicle.eject();
                }
            }
        }
    }

    private boolean playerCanRide(Player player) {
        return Perms.RIDE.has(player) && player.getPassenger() == null;
    }

    private Player getRootVehicle(Player vehicle) {
        while (getVehicle(vehicle) != null) {
            vehicle = (Player) getVehicle(vehicle);
        }
        return vehicle;
    }

    private Player getVehicle(Player player) {
        for (Player onlinePlayer : plugin.getServer().getOnlinePlayers()) {
            Entity passenger = onlinePlayer.getPassenger();
            if (passenger instanceof Player && passenger.getEntityId() == player.getEntityId()) {
                return onlinePlayer;
            }
        }
        return null;
    }

    private boolean duckEjectPassenger(Player duck, Entity passenger) {
        if (passenger.equals(duck.getPassenger())) {
            duck.eject();
            return true;
        }
        return false;
    }

    private Player getLastPassenger(Player vehicle) {
        while (vehicle.getPassenger() != null && vehicle.getPassenger() instanceof Player) {
            vehicle = (Player) vehicle.getPassenger();
        }

        return vehicle;
    }
}
