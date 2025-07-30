package com.naotenhorgb.skyFight.listeners;

import com.naotenhorgb.skyFight.data.PlayerStatus;
import com.naotenhorgb.skyFight.managers.PlayerManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageByEntityListener implements Listener {

    private final PlayerManager playerManager;

    public DamageByEntityListener(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @EventHandler
    public void onProjectileDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player victim)) return;

        Entity damageSource = event.getDamager();

        PlayerStatus playerStatus = playerManager.getPlayerStatus(victim);

        if (damageSource instanceof Player) {
            Player attacker = (Player) event.getDamager();
            playerStatus.setAttacker(attacker);
        }
        else if (damageSource instanceof Projectile) {
            Player attacker = (Player) ((Projectile) damageSource).getShooter();
            playerStatus.setAttacker(attacker);
        }

    }
}