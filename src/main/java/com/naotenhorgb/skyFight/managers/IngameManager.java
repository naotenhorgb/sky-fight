package com.naotenhorgb.skyFight.managers;

import com.naotenhorgb.skyFight.data.enums.StatusEnums;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class IngameManager {

    private Map<Player, Enum<StatusEnums>> playerStatus = new HashMap<>();

    public void setPlayer(Player player, Enum<StatusEnums> status) {
        playerStatus.put(player, status);
    }

    public void removePlayer(Player player) {
        playerStatus.remove(player);
    }

    public boolean hasStatus(Player player, StatusEnums status) {
        return status.equals(playerStatus.get(player));
    }

    public Enum<StatusEnums> getStatus(Player player) {
        return playerStatus.get(player);
    }

}
