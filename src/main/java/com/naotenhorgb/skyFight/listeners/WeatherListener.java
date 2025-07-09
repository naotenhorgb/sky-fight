package com.naotenhorgb.skyFight.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherListener implements Listener {

    /* WARNING:
    WeatherListener is likely obsolete and can be replaced with
    e.getPlayer().setPlayerWeather(WeatherType.CLEAR);
     */

    @EventHandler
    public void onWeather(WeatherChangeEvent event) {
        event.setCancelled(true);
    }

}
