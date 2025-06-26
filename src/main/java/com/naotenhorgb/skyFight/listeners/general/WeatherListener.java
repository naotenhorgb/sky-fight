package com.naotenhorgb.skyFight.listeners.general;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherListener implements Listener {

    /* WARNING:
    WeatherListener is likely obsolete and can be replaced with
    e.getPlayer().setPlayerWeather(WeatherType.CLEAR);
     */

    @EventHandler
    public void onWeather(WeatherChangeEvent e) {
        e.getWorld().setStorm(false);
        e.setCancelled(true);
    }

}
