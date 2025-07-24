package com.naotenhorgb.skyFight.managers;

import com.naotenhorgb.skyFight.data.PlayerStatus;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PlayerManager {

    private final HashMap<UUID, PlayerStatus> playerStatus = new HashMap<>();

    public PlayerStatus getPlayerStatus(Player player){
        return playerStatus.computeIfAbsent(player.getUniqueId(), id -> new PlayerStatus());
    }

    public void clearPlayerStatus(Player player){
        playerStatus.remove(player.getUniqueId());
    }

}
