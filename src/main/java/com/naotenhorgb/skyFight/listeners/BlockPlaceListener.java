package com.naotenhorgb.skyFight.listeners;

import com.naotenhorgb.skyFight.managers.IngameManager;
import com.naotenhorgb.skyFight.data.enums.StatusEnums;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

    private final IngameManager ingameManager;

    public BlockPlaceListener(IngameManager ingameManager) {
        this.ingameManager = ingameManager;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        if ((!ingameManager.hasStatus(event.getPlayer(), StatusEnums.BUILD)) ||
                !event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) event.setCancelled(true);
    }
}
