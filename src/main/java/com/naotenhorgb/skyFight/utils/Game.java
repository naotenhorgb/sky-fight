package com.naotenhorgb.skyFight.utils;

import com.naotenhorgb.skyFight.data.MessagesConfig;
import com.naotenhorgb.skyFight.data.PlayerStatus;
import com.naotenhorgb.skyFight.data.enums.InventoryEnums;
import com.naotenhorgb.skyFight.data.enums.StatusEnums;
import com.naotenhorgb.skyFight.managers.PlayerManager;
import org.jetbrains.annotations.NotNull;
import org.bukkit.entity.Player;

public class Game {

    private final MatchMaterial materialConverter = new MatchMaterial();
    private final PlayerManager playerManager;
    private final LocationUtils locationUtils;

    public Game(PlayerManager playerManager, LocationUtils locationUtils) {
        this.playerManager = playerManager;
        this.locationUtils = locationUtils;
    }

    public void sendToGameSpawn(@NotNull Player player){
        playerManager.getPlayerStatus(player).setStatus(StatusEnums.OUTGAME);
        player.teleport(locationUtils.getGameSpawn());
        giveInventory(player, InventoryEnums.SAFEZONE);
        player.setHealth(player.getMaxHealth());
    }

    public void sendToLobby(@NotNull Player player){
        playerManager.getPlayerStatus(player).setStatus(StatusEnums.OUTGAME);
        player.teleport(locationUtils.getLobbySpawn());
        giveInventory(player, InventoryEnums.LOBBY);
        player.setHealth(player.getMaxHealth());
    }

    public void giveInventory(@NotNull Player player, InventoryEnums mode){
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

    public void kill(Player victim) {
        PlayerStatus victimStatus = playerManager.getPlayerStatus(victim);
        victimStatus.setStatDeaths(victimStatus.getStatDeaths() + 1);

        if(victimStatus.getAttacker() != null) {
            PlayerStatus attackerStatus = playerManager.getPlayerStatus(victimStatus.getAttacker());
            Player attacker = victimStatus.getAttacker();
            attackerStatus.setStatKills(attackerStatus.getStatKills() + 1);
            // very basic coins just to fill the checklist
            attackerStatus.setCoins(attackerStatus.getCoins() + 100);
            attacker.setHealth(attacker.getMaxHealth());
            // todo:
            //  send message to people ingame
            //  give random item bonus to attacker
        }
        sendToGameSpawn(victim);
    }

}
