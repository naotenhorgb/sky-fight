package com.naotenhorgb.skyFight.utils;

import com.naotenhorgb.skyFight.data.MessagesConfig;
import com.naotenhorgb.skyFight.data.enums.InventoryEnums;
import com.naotenhorgb.skyFight.data.enums.StatusEnums;
import com.naotenhorgb.skyFight.managers.IngameManager;
import org.jetbrains.annotations.Nullable;
import org.bukkit.entity.Player;

public class Game {

    private final MatchMaterial materialConverter = new MatchMaterial();
    private final IngameManager ingameManager;
    private final LocationUtils locationUtils;

    public Game(IngameManager ingameManager, LocationUtils locationUtils) {
        this.ingameManager = ingameManager;
        this.locationUtils = locationUtils;
    }

    public void sendToGameSpawn(Player player){
        ingameManager.setPlayer(player, StatusEnums.OUTGAME);
        player.teleport(locationUtils.getGameSpawn());
        giveInventory(player, InventoryEnums.SAFEZONE);
        player.setHealth(player.getMaxHealth());
    }

    public void sendToLobby(Player player){
        ingameManager.setPlayer(player, StatusEnums.OUTGAME);
        player.teleport(locationUtils.getLobbySpawn());
        giveInventory(player, InventoryEnums.LOBBY);
        player.setHealth(player.getMaxHealth());
    }

    public void giveInventory(Player player, InventoryEnums mode){
        player.getInventory().clear();
        switch (mode) {
            case LOBBY:
                player.getInventory().setItem(3, materialConverter.getItemStack("ENDER_PEARL"));
                player.getInventory().setItem(5, materialConverter.getItemStack("COMPASS"));
                break;
            case INGAME:
                player.getInventory().setItem(2, materialConverter.getItemStack("GOLDEN_AXE"));
                player.getInventory().setItem(3, materialConverter.getItemStack("WOODEN_AXE"));
                player.getInventory().setItem(4, materialConverter.getItemStack("STONE_AXE"));
                player.getInventory().setItem(5, materialConverter.getItemStack("IRON_AXE"));
                player.getInventory().setItem(6, materialConverter.getItemStack("DIAMOND_AXE"));
                break;
            case SAFEZONE:
                player.getInventory().setItem(3, materialConverter.getItemStack("ENDER_PEARL"));
                player.getInventory().setItem(5, materialConverter.getItemStack("NETHER_STAR"));
                break;
            default:
                player.sendMessage(MessagesConfig.get("unknown_error") + " #0001");
                break;
        }

    }

    public void kill(Player victim, @Nullable Player reason) {
        sendToGameSpawn(victim);

        if(reason != null) {
            reason.setHealth(reason.getMaxHealth());
        }
        // TODO:
        //  give random item bonus to reason
        //  add kill count increase to reason and death increase to victim
        //  add metadata search for last reason
    }

}
