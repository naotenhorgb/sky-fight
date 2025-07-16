package com.naotenhorgb.skyFight.listeners;

import com.naotenhorgb.skyFight.data.MessagesConfig;
import com.naotenhorgb.skyFight.data.SkyfightConfig;
import com.naotenhorgb.skyFight.data.enums.StatusEnums;
import com.naotenhorgb.skyFight.managers.IngameManager;
import com.naotenhorgb.skyFight.utils.Game;
import org.bukkit.WeatherType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private final Game game;
    private final IngameManager ingameManager;
    private final boolean setup = Boolean.parseBoolean(SkyfightConfig.get().setuped);

    public JoinListener(IngameManager ingameManager, Game game) {
        this.ingameManager = ingameManager;
        this.game = game;
    }

    // TODO: replace manual tp with teleportToSpawn()

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.setPlayerWeather(WeatherType.CLEAR);
        player.setPlayerTime(1200, false);
        player.setFoodLevel(20);
        if(!setup){
            MessagesConfig.send(player, "no_setup");
            ingameManager.setPlayer(player, StatusEnums.BUILD);
            return;
        }
        game.sendToLobby(player);
    }
}
