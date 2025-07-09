package com.naotenhorgb.skyFight.utils;

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
        giveGameSpawnInventory(player);
        player.teleport(locationUtils.getGameSpawn());
        ingameManager.setPlayer(player, StatusEnums.OUTGAME);
        player.setHealth(player.getMaxHealth());
    }

    public void sendToLobby(Player player){
        giveLobbyInventory(player);
        player.teleport(locationUtils.getLobbySpawn());
        ingameManager.setPlayer(player, StatusEnums.OUTGAME);
        player.setHealth(player.getMaxHealth());
    }

    public void giveGameSpawnInventory(Player player){
        player.getInventory().clear();
        player.getInventory().setItem(3, materialConverter.getItemStack("ENDER_PEARL"));
        player.getInventory().setItem(5, materialConverter.getItemStack("NETHER_STAR"));

    }

    public void giveLobbyInventory(Player player){
        player.getInventory().clear();
        player.getInventory().setItem(3, materialConverter.getItemStack("ENDER_PEARL"));
        player.getInventory().setItem(5, materialConverter.getItemStack("COMPASS"));
    }

    public void giveIngameInventory(Player player) {
        player.getInventory().clear();
        player.getInventory().setItem(2, materialConverter.getItemStack("GOLDEN_AXE"));
        player.getInventory().setItem(3, materialConverter.getItemStack("WOODEN_AXE"));
        player.getInventory().setItem(4, materialConverter.getItemStack("STONE_AXE"));
        player.getInventory().setItem(5, materialConverter.getItemStack("IRON_AXE"));
        player.getInventory().setItem(6, materialConverter.getItemStack("DIAMOND_AXE"));
    }

    public void handleDeath(Player victim, @Nullable Player attacker) {
        ingameManager.setPlayer(victim, StatusEnums.OUTGAME);
        giveGameSpawnInventory(victim);
        victim.teleport(locationUtils.getGameSpawn());

        if(attacker != null) {
            attacker.setHealth(attacker.getMaxHealth());
        }
        // TODO:
        //  give random item bonus to attacker
        //  add kill count increase to attacker and death increase to victim
        //  add metadata search for last attacker
    }

}
