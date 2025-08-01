package com.naotenhorgb.skyFight.listeners;

import com.naotenhorgb.skyFight.core.Game;
import com.naotenhorgb.skyFight.core.LocationUtils;
import com.naotenhorgb.skyFight.data.PlayerStatus;
import com.naotenhorgb.skyFight.data.enums.InventoryEnums;
import com.naotenhorgb.skyFight.data.enums.StatusEnums;
import com.naotenhorgb.skyFight.managers.PlayerManager;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMovementListener implements Listener {

    private final World lobbyWorld;
    private final LocationUtils locationUtils;
    private final PlayerManager playerManager;
    private final Game game;

    public PlayerMovementListener(LocationUtils locUtils, PlayerManager ingame, Game game) {
        this.lobbyWorld = locUtils.getLobbySpawn().getWorld();
        this.locationUtils = locUtils;
        this.playerManager = ingame;
        this.game = game;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        PlayerStatus playerStatus = playerManager.getPlayerStatus(player);
        Enum<StatusEnums> status = playerStatus.getStatus();

        if (player.getWorld().equals(lobbyWorld)) return;
        if (status == StatusEnums.BUILD || player.getGameMode() == GameMode.SPECTATOR) return;

        if (player.getLocation().getY() <= locationUtils.getGameSafezone().getYMin()
                && status == StatusEnums.OUTGAME) {
            game.giveInventory(player, InventoryEnums.INGAME);
            playerStatus.setStatus(StatusEnums.ENTER_INGAME);
        }

        else if (player.getLocation().getY() < locationUtils.getDeathY() || !locationUtils.getGameBoundaries().isInIgnoreY(player.getLocation())) {
            if (status == StatusEnums.INGAME || status == StatusEnums.ENTER_INGAME) {
                game.kill(player);
            }
        }

        else if (locationUtils.getGameSafezone().isIn(player) && status == StatusEnums.INGAME) {
            game.kill(player);
        }

    }
}