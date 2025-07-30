package com.naotenhorgb.skyFight.core;

import com.naotenhorgb.skyFight.data.LocationsConfig;
import lombok.Getter;
import org.bukkit.Location;

public class LocationUtils {

    private final LocationsConfig config;
    private final YamlConverter yamlConverter = new YamlConverter();

    @Getter private Location lobbySpawn;
    @Getter private Location gameSpawn;
    @Getter private Cuboid gameSafezone;
    @Getter private Cuboid gameBoundaries;
    @Getter private int deathY;

    public LocationUtils() {
        this.config = LocationsConfig.load();
        this.lobbySpawn = yamlConverter.stringToLocation(config.lobby_spawn);
        this.gameSpawn = yamlConverter.stringToLocation(config.game_spawn);
        this.gameSafezone = yamlConverter.stringsToCuboid(config.game_safezone);
        this.gameBoundaries = yamlConverter.stringsToCuboid(config.game_boundaries);
        this.deathY = yamlConverter.stringsToInt(config.void_yloc);
    }

    public void setLobbySpawn(Location lobbySpawn) {
        this.lobbySpawn = lobbySpawn;
        config.lobby_spawn = yamlConverter.locationToString(lobbySpawn);
        config.save();
    }

    public void setGameSpawn(Location gameSpawn) {
        this.gameSpawn = gameSpawn;
        config.game_spawn = yamlConverter.locationToString(gameSpawn);
        config.save();
    }

    public void setGameSafezone(Cuboid gameSafezone) {
        this.gameSafezone = gameSafezone;
        config.game_safezone = yamlConverter.cuboidToString(gameSafezone);
        config.save();
    }

    public void setGameBoundaries(Cuboid gameBoundaries) {
        this.gameBoundaries = gameBoundaries;
        config.game_boundaries = yamlConverter.cuboidToString(gameBoundaries);
        config.save();
    }

    public void setDeathY(int deathY) {
        this.deathY = deathY;
        config.void_yloc = yamlConverter.intToString(deathY);
        config.save();
    }

    public boolean getSetuped() {
        return !config.lobby_spawn.equals(" ");
    }

    public String get(String key) {
        return LocationsConfig.get(key);
    }

}
