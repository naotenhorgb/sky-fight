package com.naotenhorgb.skyFight.listeners;

import com.naotenhorgb.skyFight.data.PlayerStatus;
import com.naotenhorgb.skyFight.data.SkyfightConfig;
import com.naotenhorgb.skyFight.data.enums.InventoryEnums;
import com.naotenhorgb.skyFight.managers.PlayerManager;
import com.naotenhorgb.skyFight.utils.Game;
import com.naotenhorgb.skyFight.utils.LocationUtils;
import com.naotenhorgb.skyFight.data.enums.StatusEnums;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMovementListener implements Listener {

    private final boolean setup;
    private final World lobbyWorld;
    private final LocationUtils locationUtils;
    private final PlayerManager playerManager;
    private final Game game;

    public PlayerMovementListener(LocationUtils locUtils, PlayerManager ingame, Game game) {
        this.setup = Boolean.parseBoolean(SkyfightConfig.get().setuped);
        this.lobbyWorld = locUtils.getLobbySpawn().getWorld();
        this.locationUtils = locUtils;
        this.playerManager = ingame;
        this.game = game;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (!setup) return;

        Player player = event.getPlayer();
        PlayerStatus playerStatus = playerManager.getPlayerStatus(player);
        Enum<StatusEnums> status = playerStatus.getStatus();

        if (player.getWorld().equals(lobbyWorld)) return;
        if (status == StatusEnums.BUILD || player.getGameMode() == GameMode.SPECTATOR) return;

        if (player.getLocation().getY() <= locationUtils.getGameSafezone().getyMin()
                && status == StatusEnums.OUTGAME) {
            game.giveInventory(player, InventoryEnums.INGAME);
            playerStatus.setStatus(StatusEnums.ENTER_INGAME);
        }

        if (player.getLocation().getY() < locationUtils.getDeathY() || !locationUtils.getGameBoundaries().isInIgnoreY(player.getLocation())) {
            if (status == StatusEnums.INGAME) {
                game.kill(player);
            }
        }

        if (locationUtils.getGameSafezone().isIn(player) && status == StatusEnums.INGAME) {
            game.kill(player);
        }

    }
}