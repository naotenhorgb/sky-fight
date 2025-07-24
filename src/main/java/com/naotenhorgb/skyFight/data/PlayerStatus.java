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

    // todo: add default system for preventing null issues

    private int coins = 0;
    private int statKills = 0;
    private int statDeaths = 0;
    private int statBlocks = 0;

    private int itemHoe = 0;
    private int itemBaloon = 0;
    private int itemSnowball = 0;

    private HashMap<UUID, Location> blocks;
    private Enum<StatusEnums> status;

    private Player attacker;



}
