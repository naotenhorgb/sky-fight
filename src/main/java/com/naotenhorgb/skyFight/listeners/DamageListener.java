package com.naotenhorgb.skyFight.listeners;

import com.naotenhorgb.skyFight.data.PlayerStatus;
import com.naotenhorgb.skyFight.data.enums.StatusEnums;
import com.naotenhorgb.skyFight.managers.PlayerManager;
import com.naotenhorgb.skyFight.core.Game;
import com.naotenhorgb.skyFight.core.LocationUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;


public class DamageListener implements Listener {

    private final LocationUtils locationUtils;
    private final PlayerManager playerManager;
    private final Game game;

    public DamageListener(LocationUtils locationUtils, PlayerManager playerManager, Game game) {
        this.locationUtils = locationUtils;
        this.playerManager = playerManager;
        this.game = game;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;

        Player victim = (Player) event.getEntity();
        PlayerStatus playerStatus = playerManager.getPlayerStatus(victim);
        Enum<StatusEnums> status = playerStatus.getStatus();

        if (victim.getWorld() == locationUtils.getLobbySpawn().getWorld()) {
            if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
                game.sendToLobby(victim);
            }
            event.setCancelled(true);
            return;
        }

        if (locationUtils.getGameSafezone().isIn(victim)) {
            event.setCancelled(true);
            return;
        }

        if (event.getDamage() >= victim.getHealth()) {
            event.setCancelled(true);
            game.kill(victim);
        }

        if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            if (status == StatusEnums.ENTER_INGAME) {
                playerStatus.setStatus(StatusEnums.INGAME);
                event.setCancelled(true);
            } else {
                event.setDamage(Math.floor(event.getDamage() / 2));
            }
        }
    }
}
