package com.naotenhorgb.skyFight.listeners;

import com.naotenhorgb.skyFight.data.PlayerStatus;
import com.naotenhorgb.skyFight.managers.PlayerManager;
import com.naotenhorgb.skyFight.core.MatchMaterial;
import com.naotenhorgb.skyFight.data.enums.StatusEnums;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ItemClickListener implements Listener {

    private final MatchMaterial materialConverter = new MatchMaterial();
    private final PlayerManager playerManager;

    public ItemClickListener(PlayerManager playerManager){
        this.playerManager = playerManager;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        PlayerStatus playerStatus = playerManager.getPlayerStatus(player);

        ItemStack itemStack = materialConverter.getItemStack("ENDER_EYE");
        if(playerStatus.getStatus() == StatusEnums.OUTGAME && event.getMaterial().equals(Material.ENDER_PEARL)) {
            player.performCommand("lobby");
            event.setCancelled(true);
        }
    }
}
