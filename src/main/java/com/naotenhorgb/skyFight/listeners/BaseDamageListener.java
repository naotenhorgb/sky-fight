package com.naotenhorgb.skyFight.listeners;

import com.naotenhorgb.skyFight.utils.LocationUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class BaseDamageListener implements Listener {

    private final LocationUtils locationUtils;

    public BaseDamageListener(LocationUtils locationUtils) {
        this.locationUtils = locationUtils;
    }

    @EventHandler
    public void onBaseDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (player.getWorld() == locationUtils.getLobbySpawn().getWorld()) {
                event.setCancelled(true);
            }
        }
    }
}
