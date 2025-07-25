package com.naotenhorgb.skyFight;

import com.naotenhorgb.skyFight.commands.*;
import com.naotenhorgb.skyFight.data.LocationsConfig;
import com.naotenhorgb.skyFight.data.MessagesConfig;
import com.naotenhorgb.skyFight.data.SkyfightConfig;
import com.naotenhorgb.skyFight.listeners.*;
import com.naotenhorgb.skyFight.managers.PlayerManager;
import com.naotenhorgb.skyFight.utils.Cuboid;
import com.naotenhorgb.skyFight.utils.Game;
import com.naotenhorgb.skyFight.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class SkyFight extends JavaPlugin {

    @Override
    public void onEnable() {


        LocationsConfig.load();
        SkyfightConfig.load();
        MessagesConfig.load();

        final PlayerManager playerManager = new PlayerManager();
        final LocationUtils locationUtils = new LocationUtils();
        final Game game = new Game(playerManager, locationUtils);

        Location lobbySpawn = locationUtils.getLobbySpawn();
        Location gameSpawn = locationUtils.getGameSpawn();
        Cuboid gameSafezone = locationUtils.getGameSafezone();
        Cuboid gameBoundaries = locationUtils.getGameBoundaries();

        this.getCommand("setspawn").setExecutor(new SetSpawnCommand(locationUtils));
        this.getCommand("setup").setExecutor(new SetupCommand(locationUtils));
        this.getCommand("lobby").setExecutor(new LobbyCommand(locationUtils, playerManager, game));
        this.getCommand("build").setExecutor(new BuildCommand(playerManager, game));
        this.getCommand("sendstatus").setExecutor(new SendStatusCommand(playerManager));

        final PluginManager plugin = getServer().getPluginManager();
        if(Boolean.parseBoolean(SkyfightConfig.get().setuped)) {
            plugin.registerEvents(new JoinListener(playerManager, game), this);
            plugin.registerEvents(new HungerListener(), this);
            plugin.registerEvents(new DropListener(playerManager), this);
            plugin.registerEvents(new WeatherListener(), this);
            plugin.registerEvents(new SpawnListener(locationUtils), this);
            plugin.registerEvents(new DamageListener(locationUtils, playerManager, game), this);
            plugin.registerEvents(new ItemClickListener(playerManager), this);
            plugin.registerEvents(new PlayerMovementListener(locationUtils, playerManager, game), this);
            plugin.registerEvents(new BlockBreakListener(playerManager), this);
            plugin.registerEvents(new BlockPlaceListener(playerManager), this);
            plugin.registerEvents(new DamageByEntityListener(playerManager), this);
        } else {
            Bukkit.getConsoleSender().sendMessage("\n\n");
            MessagesConfig.send(Bukkit.getConsoleSender(), "no_setup");
            Bukkit.getConsoleSender().sendMessage("\n\n");
        }

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
}
