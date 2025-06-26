package com.naotenhorgb.skyFight.listeners.general;

import com.naotenhorgb.skyFight.utils.LocationUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class DeathListener implements Listener {

    private final LocationUtils locationUtils;
    private final Plugin plugin;

    /* WARNING:
    DeathListener is likely obsolete if DamageListener is done correctly
     */

    public DeathListener(LocationUtils locationUtils, Plugin plugin) {
        this.locationUtils = locationUtils;
        this.plugin = plugin;
    }

    // TODO: replace manual tp with teleportToSpawn()

    @EventHandler
    public void onDeathEvent(PlayerDeathEvent e) {
        // nota: isto é um substituto temp para um detector de morte via onDamage
        if (locationUtils.getSpawn() != null) {
            e.getEntity().spigot().respawn();
            new BukkitRunnable() {
                @Override

                public void run() {
                    e.getEntity().teleport(locationUtils.getSpawn());
                }

            }.runTask(plugin);
        } else {
            e.getEntity().sendMessage("Spawn não encontrado");
        }
    }

}
