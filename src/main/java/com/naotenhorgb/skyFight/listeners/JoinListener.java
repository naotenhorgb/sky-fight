package com.naotenhorgb.skyFight.listeners;

import com.naotenhorgb.skyFight.core.Game;
import com.naotenhorgb.skyFight.core.LocationUtils;
import com.naotenhorgb.skyFight.core.MessagesUtils;
import com.naotenhorgb.skyFight.data.PlayerStatus;
import com.naotenhorgb.skyFight.data.enums.StatusEnums;
import com.naotenhorgb.skyFight.managers.PlayerManager;
import org.bukkit.WeatherType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private final Game game;
    private final PlayerManager playerManager;
    private final LocationUtils locationUtils ;

    public JoinListener(Game game, PlayerManager playerManager, LocationUtils locationUtils) {
        this.game = game;
        this.playerManager = playerManager;
        this.locationUtils = locationUtils;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        PlayerStatus playerStatus = playerManager.getPlayerStatus(player);

        player.setPlayerWeather(WeatherType.CLEAR);
        player.setPlayerTime(1200, false);
        player.setFoodLevel(20);

        if(!locationUtils.getSetuped()){
            MessagesUtils.send(player, "no_setup");
            playerStatus.setStatus(StatusEnums.BUILD);
            return;
        }
        game.sendToLobby(player);
    }
}
