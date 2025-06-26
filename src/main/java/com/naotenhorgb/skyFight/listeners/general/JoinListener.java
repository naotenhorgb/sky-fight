package com.naotenhorgb.skyFight.listeners.general;

import com.naotenhorgb.skyFight.utils.LocationUtils;
import org.bukkit.WeatherType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private final LocationUtils locationUtils;

    public JoinListener(LocationUtils locationUtils){
        this.locationUtils = locationUtils;
    }

    // TODO: replace manual tp with teleportToSpawn()

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        e.getPlayer().setPlayerWeather(WeatherType.CLEAR);
        e.getPlayer().setPlayerTime(1200, false);
        e.getPlayer().setFoodLevel(100);

        if (locationUtils.getSpawn() != null) {
            e.getPlayer().teleport(locationUtils.getSpawn());
        } else {
            e.getPlayer().sendMessage("Spawn não encontrado");
        }
    }
}
