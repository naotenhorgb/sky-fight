package com.naotenhorgb.skyFight.listeners;

import com.naotenhorgb.skyFight.managers.IngameManager;
import com.naotenhorgb.skyFight.utils.Game;
import com.naotenhorgb.skyFight.utils.LocationUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import static com.naotenhorgb.skyFight.utils.StatusEnums.INGAME;
import static com.naotenhorgb.skyFight.utils.StatusEnums.OUTGAME;

public class ExtraDamageListener implements Listener {

    private final LocationUtils locationUtils;
    private final IngameManager ingameManager;
    private final Game game;

    public ExtraDamageListener(LocationUtils locationUtils, IngameManager ingameManager, Game game) {
        this.locationUtils = locationUtils;
        this.ingameManager = ingameManager;
        this.game = game;
    }

    @EventHandler
    public void onExtraDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        Player victim = (Player) event.getEntity();

        if (locationUtils.getGameSafezone().isIn(victim)) {
            event.setCancelled(true);
            return;
        }

        // todo: add damage prevention on first fall
        if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            event.setDamage(Math.floor(event.getDamage() / 2));
        }

        if (event.getDamage() > victim.getHealth()) {
            if (event instanceof EntityDamageByEntityEvent &&
                    ((EntityDamageByEntityEvent) event).getDamager() instanceof Player) {
                Player attacker = (Player) ((EntityDamageByEntityEvent) event).getDamager();
                game.handleDeath(victim, attacker);
            } else {
                game.handleDeath(victim, null);
            }
        }
    }
}
