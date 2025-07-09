package com.naotenhorgb.skyFight.listeners;

import com.naotenhorgb.skyFight.managers.IngameManager;
import com.naotenhorgb.skyFight.utils.Game;
import com.naotenhorgb.skyFight.utils.StatusEnums;
import com.naotenhorgb.skyFight.utils.LocationUtils;
import org.bukkit.WeatherType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private final Game game;

    public JoinListener(Game game) {
        this.game = game;
    }

    // TODO: replace manual tp with teleportToSpawn()

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.setPlayerWeather(WeatherType.CLEAR);
        player.setPlayerTime(1200, false);
        player.setFoodLevel(20);
        game.sendToLobby(player);
    }
}
