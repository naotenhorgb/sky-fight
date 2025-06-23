package com.naotenhorgb.skyFight.listeners;

import com.naotenhorgb.skyFight.utils.LocationUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    private final LocationUtils locationUtils = new LocationUtils();

    // TODO: replace manual tp with teleportToSpawn()

    @EventHandler
    public void onDeathEvent(PlayerDeathEvent e){
        if (!e.getEntity().hasPlayedBefore() && locationUtils.getSpawn() != null) {
            e.getEntity().teleport(locationUtils.getSpawn());
        } else {
            e.getEntity().sendMessage("Spawn não encontrado");
        }
    }

}
