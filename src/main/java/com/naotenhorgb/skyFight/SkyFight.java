package com.naotenhorgb.skyFight;

import com.naotenhorgb.skyFight.commands.BuildCommand;
import com.naotenhorgb.skyFight.commands.LobbyCommand;
import com.naotenhorgb.skyFight.commands.SetSpawnCommand;
import com.naotenhorgb.skyFight.commands.SetupCommand;
import com.naotenhorgb.skyFight.listeners.*;
import com.naotenhorgb.skyFight.managers.IngameManager;
import com.naotenhorgb.skyFight.managers.LocationsConfig;
import com.naotenhorgb.skyFight.utils.Cuboid;
import com.naotenhorgb.skyFight.utils.Game;
import com.naotenhorgb.skyFight.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;

public final class SkyFight extends JavaPlugin {


    @Override
    public void onEnable() {

        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
            getLogger().info("Criou dataFolder: " + getDataFolder().getAbsolutePath());
        }

        // 2) testa se o recurso está realmente embutido
        InputStream test = getResource("locations.yml");
        if (test == null) {
            getLogger().severe(">>>>> RECURSO 'locations.yml' NÃO ENCONTRADO NO JAR! <<<<<");
        } else {
            try {
                getLogger().info("Recurso encontrado, tamanho = " + test.available() + " bytes");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // 3) apenas se existir, copia para disk
        saveResource("locations.yml", false);

        // 4) agora carrega
        LocationsConfig.init(this);
        LocationsConfig cfg = LocationsConfig.load();
        getLogger().info("locations.yml carregado: lobby_spawn=" + cfg.lobby_spawn);

        final IngameManager ingameManager = new IngameManager();
        final LocationUtils locationUtils = new LocationUtils();
        final Game game = new Game(ingameManager, locationUtils);

        // this is all temp
        final Location lobbySpawn = new Location(Bukkit.getWorld("world2"), 0, 49, 0);
        final Location gameSpawn = new Location(Bukkit.getWorld("world3"), -1053, 55, 610);
        final Cuboid arenaSafezone = new Cuboid(new Location(Bukkit.getWorld("world3"), -1058, 59, 616), new Location(Bukkit.getWorld("world3"), -1048, 53, 604));
        final Cuboid arenaBoundaries = new Cuboid(new Location(Bukkit.getWorld("world3"), 2000, 0, 2000), new Location(Bukkit.getWorld("world3"), 2000, 1, 2000));

        locationUtils.setGameSafezone(arenaSafezone);
        locationUtils.setLobbySpawn(lobbySpawn);
        locationUtils.setGameSpawn(gameSpawn);
        locationUtils.setGameBoundaries(arenaBoundaries);

        // TODO: replace with more modular system to register commands

        this.getCommand("setspawn").setExecutor(new SetSpawnCommand(locationUtils));
        this.getCommand("setup").setExecutor(new SetupCommand(locationUtils));
        this.getCommand("lobby").setExecutor(new LobbyCommand(locationUtils, ingameManager, game));
        this.getCommand("build").setExecutor(new BuildCommand(ingameManager, game));

        /*
        likely needs to be replaced with something else
        due to too much listeners in future
        TODO: replace with more modular system to register events
         */

        final PluginManager plugin = getServer().getPluginManager();
        plugin.registerEvents(new JoinListener(game), this);
        plugin.registerEvents(new HungerListener(), this);
        plugin.registerEvents(new DropListener(), this);
        plugin.registerEvents(new WeatherListener(), this);
        plugin.registerEvents(new SpawnListener(locationUtils), this);
        plugin.registerEvents(new BaseDamageListener(locationUtils), this);
        plugin.registerEvents(new ExtraDamageListener(locationUtils, ingameManager, game), this);
        plugin.registerEvents(new ItemClickListener(ingameManager), this);
        plugin.registerEvents(new PlayerMovementListener(locationUtils, ingameManager, game), this);


        // TODO: test if these blocks works
        // TODO: replace with less hardcoded setGamerule for everything when it happens a lot of times
        lobbySpawn.getWorld().setGameRuleValue("ANNOUNCE_ADVANCEMENTS", "false");
        lobbySpawn.getWorld().setGameRuleValue("DO_FIRE_TICK", "false");
        lobbySpawn.getWorld().setGameRuleValue("DO_MOB_SPAWNING", "false");
        lobbySpawn.getWorld().setGameRuleValue("DO_MOB_LOOT", "false");
        lobbySpawn.getWorld().setGameRuleValue("SHOW_DEATH_MESSAGES", "false");
        lobbySpawn.getWorld().setGameRuleValue("RANDOM_TICK_SPEED", "0");

        lobbySpawn.getWorld().setSpawnFlags(false, false);
        lobbySpawn.getWorld().setPVP(false);

        gameSpawn.getWorld().setGameRuleValue("ANNOUNCE_ADVANCEMENTS", "false");
        gameSpawn.getWorld().setGameRuleValue("DO_FIRE_TICK", "false");
        gameSpawn.getWorld().setGameRuleValue("DO_MOB_SPAWNING", "false");
        gameSpawn.getWorld().setGameRuleValue("DO_MOB_LOOT", "false");
        gameSpawn.getWorld().setGameRuleValue("SHOW_DEATH_MESSAGES", "false");
        gameSpawn.getWorld().setGameRuleValue("RANDOM_TICK_SPEED", "0");

        gameSpawn.getWorld().setSpawnFlags(false, false);
        gameSpawn.getWorld().setPVP(true);

        gameSpawn.getWorld().setTime(1200);
        lobbySpawn.getWorld().setTime(1200);

        gameSpawn.getWorld().setStorm(false);
        lobbySpawn.getWorld().setStorm(false);

    }

    @Override
    public void onDisable() {
    }

    public boolean isBungee() {
        return true;
    }
}
