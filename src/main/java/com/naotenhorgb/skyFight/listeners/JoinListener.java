package com.naotenhorgb.skyFight.listeners;

import com.naotenhorgb.skyFight.utils.LocationUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private final LocationUtils locationUtils = new LocationUtils();

    // TODO: replace manual tp with teleportToSpawn()

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (!e.getPlayer().hasPlayedBefore() && locationUtils.getSpawn() != null) {
            e.getPlayer().teleport(locationUtils.getSpawn());
        } else {
            e.getPlayer().sendMessage("Spawn não encontrado");
        }
    }
}
