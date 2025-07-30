package com.naotenhorgb.skyFight.listeners;

import com.naotenhorgb.skyFight.core.Cuboid;
import com.naotenhorgb.skyFight.core.LocationUtils;
import com.naotenhorgb.skyFight.core.MessagesUtils;
import com.naotenhorgb.skyFight.core.SetupUtils;
import com.naotenhorgb.skyFight.data.PlayerStatus;
import com.naotenhorgb.skyFight.data.SkyfightConfig;
import com.naotenhorgb.skyFight.data.enums.StatusEnums;
import com.naotenhorgb.skyFight.managers.PlayerManager;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private final PlayerManager playerManager;
    private final LocationUtils locationUtils;
    private final SetupUtils setupUtils;

    private Location pos1;
    private int step = 0;
    private World lobbyWorld;

    public ChatListener(PlayerManager playerManager, LocationUtils locationUtils, SetupUtils setupUtils) {
        this.playerManager = playerManager;
        this.locationUtils = locationUtils;
        this.setupUtils = setupUtils;
    }


    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();
        Location location = player.getLocation();
        PlayerStatus playerStatus = playerManager.getPlayerStatus(player);

        if (playerStatus.getStatus() != StatusEnums.BUILD || !event.getMessage().equals("next")) {
            return;
        } event.setCancelled(true);

        if (setupUtils.getExecutor() == null) {
            setupUtils.setExecutor(player);
        }

        if (setupUtils.getExecutor() != player) {
            MessagesUtils.sendWithSetupPrefix(player, "setup_different_executor");
            return;
        }

        if (location.getY() < 0 && location.getY() > 255) {
            MessagesUtils.sendWithSetupPrefix(player, "setup_invalidY");
            return;
        }

        switch (step) {
            case 0:
                MessagesUtils.sendWithSetupPrefix(player, "setup_0");
                break;
            case 1:
                if (setupUtils.isWorldEqual(location, player, setupUtils.getGameWorld())) return;
                MessagesUtils.sendWithSetupPrefix(player, "setup_1");
                locationUtils.setLobbySpawn(location);
                setupUtils.setLobbyWorld(location.getWorld());
                lobbyWorld = setupUtils.getLobbyWorld();
                break;
            case 2:
                if (setupUtils.isWorldEqual(location, player, lobbyWorld)) return;
                MessagesUtils.sendWithSetupPrefix(player, "setup_2");
                locationUtils.setGameSpawn(location);
                setupUtils.setGameWorld(location.getWorld());
                break;
            case 3:
                if (setupUtils.isWorldEqual(location, player, lobbyWorld)) return;
                MessagesUtils.sendWithSetupPrefix(player, "setup_3");
                pos1 = location;
                break;
            case 4:
                if (setupUtils.isWorldEqual(location, player, lobbyWorld)) return;
                MessagesUtils.sendWithSetupPrefix(player, "setup_4");
                locationUtils.setGameSafezone(new Cuboid(pos1, location));
                setupUtils.setGameWorld(location.getWorld());
                break;
            case 5:
                if (setupUtils.isWorldEqual(location, player, lobbyWorld)) return;
                MessagesUtils.sendWithSetupPrefix(player, "setup_5");
                locationUtils.setDeathY(location.getBlockY());
                setupUtils.setGameWorld(location.getWorld());
                break;
            case 6:
                if (setupUtils.isWorldEqual(location, player, lobbyWorld)) return;
                MessagesUtils.sendWithSetupPrefix(player, "setup_6");
                pos1 = location;
                break;
            case 7:
                if (setupUtils.isWorldEqual(location, player, lobbyWorld)) return;

                MessagesUtils.sendWithSetupPrefix(player, "setup_7");
                locationUtils.setGameBoundaries(new Cuboid(pos1, location));
                setupUtils.setGameWorld(location.getWorld());
                playerStatus.setStatus(StatusEnums.OUTGAME);

                SkyfightConfig.load().save();
                step = 0;
                break;
            default:
                playerStatus.setStatus(StatusEnums.OUTGAME);
                step = 0;
                break;
        }
        step++;
    }

}

