package com.naotenhorgb.skyFight.listeners;

import com.naotenhorgb.skyFight.managers.IngameManager;
import com.naotenhorgb.skyFight.utils.MatchMaterial;
import com.naotenhorgb.skyFight.data.enums.StatusEnums;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ItemClickListener implements Listener {

    private final MatchMaterial materialConverter = new MatchMaterial();
    private final IngameManager ingameManager;

    public ItemClickListener(IngameManager ingameManager){
        this.ingameManager = ingameManager;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent event){
        Player player = event.getPlayer();
        ItemStack itemStack = materialConverter.getItemStack("ENDER_EYE");
        if(ingameManager.hasStatus(player, StatusEnums.OUTGAME) && event.getMaterial().equals(Material.ENDER_PEARL)) {
            player.performCommand("lobby");
            event.setCancelled(true);
        }
    }
}
