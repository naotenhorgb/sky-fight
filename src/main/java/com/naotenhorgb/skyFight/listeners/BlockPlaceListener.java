package com.naotenhorgb.skyFight.listeners;

import com.naotenhorgb.skyFight.data.PlayerStatus;
import com.naotenhorgb.skyFight.data.enums.StatusEnums;
import com.naotenhorgb.skyFight.managers.PlayerManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {

    private final PlayerManager playerManager;

    public BlockPlaceListener(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        PlayerStatus playerStatus = playerManager.getPlayerStatus(player);
        if (playerStatus.getStatus() != StatusEnums.BUILD || !player.getGameMode().equals(GameMode.CREATIVE)) {
            event.setCancelled(true);
        }
    }
}
