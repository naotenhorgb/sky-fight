package com.naotenhorgb.skyFight.utils;

import com.naotenhorgb.skyFight.data.LocationsConfig;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;

public class LocationUtils {

    private final LocationsConfig config;
    private final YamlConverter yamlConverter = new YamlConverter();

    @Getter
    private Location lobbySpawn;
    @Getter
    private Location gameSpawn;
    @Getter @Setter
    private Cuboid arenaSafezone;
    @Getter
    private Cuboid gameBoundaries;
    @Getter
    private int deathY;

    public LocationUtils() {
        this.config = LocationsConfig.load();
        this.lobbySpawn = yamlConverter.stringToLocation(config.lobby_spawn);
        this.gameSpawn = yamlConverter.stringToLocation(config.game_spawn);
        this.arenaSafezone = yamlConverter.stringsToCuboid(config.game_safezone);
        this.gameBoundaries = yamlConverter.stringsToCuboid(config.game_boundaries);
        this.deathY = config.void_yloc;
    }

    public Cuboid getGameSafezone() {
        return arenaSafezone;
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

    public void setDeathY(int deathY) {
        this.deathY = deathY;
        config.void_yloc = deathY;
        config.save();
    }
}
