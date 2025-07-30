package com.naotenhorgb.skyFight.listeners;

import com.naotenhorgb.skyFight.core.Cuboid;
import com.naotenhorgb.skyFight.core.LocationUtils;
import com.naotenhorgb.skyFight.core.MessagesUtils;
import com.naotenhorgb.skyFight.data.PlayerStatus;
import com.naotenhorgb.skyFight.data.enums.StatusEnums;
import com.naotenhorgb.skyFight.managers.PlayerManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private final PlayerManager playerManager;
    private final LocationUtils locationUtils;

    private Location pos1;
    private int step = 0;
    private Player executor;

    public ChatListener(PlayerManager playerManager, LocationUtils locationUtils) {
        this.playerManager = playerManager;
        this.locationUtils = locationUtils;
    }


    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();
        Location location = player.getLocation();
        PlayerStatus playerStatus = playerManager.getPlayerStatus(player);

        if (playerStatus.getStatus() != StatusEnums.BUILD || !event.getMessage().equals("next")) {
            return;
        }

        event.setCancelled(true);

        if (executor == null) executor = player;
        if (executor != player) {
            MessagesUtils.sendWithSetupPrefix(player, "setup_different_executor");
            return;
        }

        if (location.getY() < 0 && location.getY() > 255) {
            MessagesUtils.sendWithSetupPrefix(player, "setup_invalidY");
            return;
        }

        step++;
        switch (step) {
            case 0:
                MessagesUtils.sendWithSetupPrefix(player, "setup_0");
                break;
            case 1:
                // todo: fix verification for equal worlds
//                if (location.getWorld() == locationUtils.getGameSpawn().getWorld()) {
//                    MessagesConfig.send(player, "setup_same_world");
//                    return;
//                }
                MessagesUtils.sendWithSetupPrefix(player, "setup_1");
                locationUtils.setLobbySpawn(location);
                break;
            case 2:
                if (isWorldLobby(location, player)) return;
                MessagesUtils.sendWithSetupPrefix(player, "setup_2");
                locationUtils.setGameSpawn(location);
                break;
            case 3:
                if (isWorldLobby(location, player)) return;
                MessagesUtils.sendWithSetupPrefix(player, "setup_3");
                pos1 = location;
                break;
            case 4:
                if (isWorldLobby(location, player)) return;
                MessagesUtils.sendWithSetupPrefix(player, "setup_4");
                locationUtils.setGameSafezone(new Cuboid(pos1, location));
                break;
            case 5:
                if (isWorldLobby(location, player)) return;
                MessagesUtils.sendWithSetupPrefix(player, "setup_5");
                locationUtils.setDeathY(location.getBlockY());
                break;
            case 6:
                if (isWorldLobby(location, player)) return;
                MessagesUtils.sendWithSetupPrefix(player, "setup_6");
                pos1 = location;
                break;
            case 7:
                if (isWorldLobby(location, player)) return;
                MessagesUtils.sendWithSetupPrefix(player, "setup_7");
                locationUtils.setGameBoundaries(new Cuboid(pos1, location));
                break;
            default:
                MessagesUtils.sendWithSetupPrefix(player, "setup_reset");
                step = 0;
                break;
        }
    }

    private boolean isWorldLobby(Location location, Player player) {
        // todo: fix verification for equal worlds
//        if (location.getWorld() == locationUtils.getLobbySpawn().getWorld()) {
//            MessagesConfig.send(player, "setup_same_world");
//            return true;
//        }
        return false;
    }
}

