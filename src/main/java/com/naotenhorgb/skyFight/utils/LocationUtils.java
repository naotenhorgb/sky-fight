package com.naotenhorgb.skyFight.utils;

import com.naotenhorgb.skyFight.managers.LocationsConfig;
import org.bukkit.Location;

public class LocationUtils {

    private final LocationsConfig config;
    private final YamlConverter yamlConverter = new YamlConverter();

    private Location lobbySpawn;
    private Location gameSpawn;
    private Cuboid arenaSafezone;
    private Cuboid gameBoundaries;

    public LocationUtils() {
        this.config = LocationsConfig.load();
        this.lobbySpawn = yamlConverter.stringToLocation(config.lobby_spawn);
        this.gameSpawn = yamlConverter.stringToLocation(config.game_spawn);
        this.arenaSafezone = yamlConverter.stringsToCuboid(config.game_safezone);
        this.gameBoundaries = yamlConverter.stringsToCuboid(config.game_boundaries);
    }

    public Location getLobbySpawn() {
        return lobbySpawn;
    }

    public Location getGameSpawn() {
        return gameSpawn;
    }

    public Cuboid getGameSafezone() {
        return arenaSafezone;
    }

    public Cuboid getGameBoundaries() {
        return gameBoundaries;
    }

    public LocationsConfig getConfig() {
        return config;
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
        this.arenaSafezone = gameSafezone;
        config.game_safezone = yamlConverter.cuboidToString(gameSafezone);
        config.save();
    }

    public void setGameBoundaries(Cuboid gameBoundaries) {
        this.gameBoundaries = gameBoundaries;
        config.game_boundaries = yamlConverter.cuboidToString(gameBoundaries);
        config.save();
    }
}
