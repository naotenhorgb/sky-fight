package com.naotenhorgb.skyFight.data;

import com.naotenhorgb.skyFight.data.enums.StatusEnums;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

@Setter
@Getter
public class PlayerStatus {

    private int statKills;
    private int statDeaths;
    private int statBlocks;
    private HashMap<UUID, Location> blocks;
    private Enum<StatusEnums> status;
    private Player attacker;
    private Player target;
    private int coins;



}
