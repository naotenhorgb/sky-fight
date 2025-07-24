package com.naotenhorgb.skyFight.listeners;

import com.naotenhorgb.skyFight.data.MessagesConfig;
import com.naotenhorgb.skyFight.data.PlayerStatus;
import com.naotenhorgb.skyFight.data.SkyfightConfig;
import com.naotenhorgb.skyFight.data.enums.StatusEnums;
import com.naotenhorgb.skyFight.managers.PlayerManager;
import com.naotenhorgb.skyFight.utils.Game;
import org.bukkit.WeatherType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private final Game game;
    private final PlayerManager playerManager;
    private final boolean setup = Boolean.parseBoolean(SkyfightConfig.get().setuped);

    public JoinListener(PlayerManager playerManager, Game game) {
        this.playerManager = playerManager;
        this.game = game;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        PlayerStatus playerStatus = playerManager.getPlayerStatus(player);

        player.setPlayerWeather(WeatherType.CLEAR);
        player.setPlayerTime(1200, false);
        player.setFoodLevel(20);

        if(!setup){
            MessagesConfig.send(player, "no_setup");
            playerStatus.setStatus(StatusEnums.BUILD);
            return;
        }
        game.sendToLobby(player);
    }
}
