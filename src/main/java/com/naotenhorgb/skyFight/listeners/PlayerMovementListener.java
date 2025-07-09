package com.naotenhorgb.skyFight.listeners;

import com.naotenhorgb.skyFight.managers.IngameManager;
import com.naotenhorgb.skyFight.utils.Game;
import com.naotenhorgb.skyFight.utils.LocationUtils;
import com.naotenhorgb.skyFight.utils.StatusEnums;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMovementListener implements Listener {

    private final LocationUtils locationUtils;
    private final Game game;
    private final IngameManager ingameManager;

    public PlayerMovementListener(LocationUtils locationUtils, IngameManager ingameManager, Game game) {
        this.locationUtils = locationUtils;
        this.ingameManager = ingameManager;
        this.game = game;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if(player.getWorld().equals(locationUtils.getLobbySpawn().getWorld())) return;
        if (player.getLocation().getY() <= locationUtils.getGameSafezone().getyMin()
                && ingameManager.hasStatus(player, StatusEnums.OUTGAME)) {
            game.giveIngameInventory(player);
            ingameManager.setPlayer(player, StatusEnums.INGAME);
        }
        if(!locationUtils.getGameBoundaries().isIn(player)
                && ingameManager.hasStatus(player, StatusEnums.INGAME)
        ) {
            game.handleDeath(player, null);
        }
    }

}
