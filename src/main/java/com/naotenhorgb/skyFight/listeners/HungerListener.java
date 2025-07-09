package com.naotenhorgb.skyFight.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class HungerListener implements Listener {

    @EventHandler
    public void onHunger(FoodLevelChangeEvent event){
        event.setFoodLevel(20);
        event.setCancelled(true);
    }
}
