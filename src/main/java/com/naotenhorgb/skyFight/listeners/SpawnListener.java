package com.naotenhorgb.skyFight.listeners;

import com.naotenhorgb.skyFight.utils.LocationUtils;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class SpawnListener implements Listener {

    private final LocationUtils locationUtils;

    public SpawnListener(LocationUtils locationUtils) {
        this.locationUtils = locationUtils;
    }

    @EventHandler
    public void onSpawn(EntitySpawnEvent event) {
        if (event.getEntity() instanceof Player || event.getEntity() instanceof Projectile) {
            return;
        }

        World outgame = locationUtils.getLobbySpawn().getWorld();
        if (event.getEntity().getWorld().equals(outgame)) {
            event.setCancelled(true);
        }


    }
}
