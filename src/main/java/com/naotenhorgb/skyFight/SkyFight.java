package com.naotenhorgb.skyFight;

import com.naotenhorgb.skyFight.commands.BuildCommand;
import com.naotenhorgb.skyFight.commands.LobbyCommand;
import com.naotenhorgb.skyFight.commands.SendStatusCommand;
import com.naotenhorgb.skyFight.commands.SetupCommand;
import com.naotenhorgb.skyFight.core.Game;
import com.naotenhorgb.skyFight.core.LocationUtils;
import com.naotenhorgb.skyFight.core.MessagesUtils;
import com.naotenhorgb.skyFight.core.SetupUtils;
import com.naotenhorgb.skyFight.data.LocationsConfig;
import com.naotenhorgb.skyFight.data.MessagesConfig;
import com.naotenhorgb.skyFight.data.SkyfightConfig;
import com.naotenhorgb.skyFight.listeners.*;
import com.naotenhorgb.skyFight.managers.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
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
        final SetupUtils setupUtils = new SetupUtils();

        this.getCommand("lobby").setExecutor(new LobbyCommand(locationUtils, playerManager, game));
        this.getCommand("build").setExecutor(new BuildCommand(playerManager, game));
        this.getCommand("sendstatus").setExecutor(new SendStatusCommand(playerManager));
        this.getCommand("setup").setExecutor(new SetupCommand(locationUtils, playerManager, setupUtils));

        final PluginManager plugin = getServer().getPluginManager();
        if(locationUtils.getSetuped()) {

            World lobbyWorld = locationUtils.getLobbySpawn().getWorld();
            World gameWorld = locationUtils.getGameSpawn().getWorld();

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

            lobbyWorld.setGameRuleValue("ANNOUNCE_ADVANCEMENTS", "false");
            lobbyWorld.setGameRuleValue("DO_FIRE_TICK", "false");
            lobbyWorld.setGameRuleValue("DO_MOB_SPAWNING", "false");
            lobbyWorld.setGameRuleValue("DO_MOB_LOOT", "false");
            lobbyWorld.setGameRuleValue("SHOW_DEATH_MESSAGES", "false");
            lobbyWorld.setGameRuleValue("RANDOM_TICK_SPEED", "0");

            lobbyWorld.setSpawnFlags(false, false);
            lobbyWorld.setPVP(false);

            gameWorld.setGameRuleValue("ANNOUNCE_ADVANCEMENTS", "false");
            gameWorld.setGameRuleValue("DO_FIRE_TICK", "false");
            gameWorld.setGameRuleValue("DO_MOB_SPAWNING", "false");
            gameWorld.setGameRuleValue("DO_MOB_LOOT", "false");
            gameWorld.setGameRuleValue("SHOW_DEATH_MESSAGES", "false");
            gameWorld.setGameRuleValue("RANDOM_TICK_SPEED", "0");

            gameWorld.setSpawnFlags(false, false);
            gameWorld.setPVP(true);

            gameWorld.setTime(1000);
            lobbyWorld.setTime(1000);

            gameWorld.setStorm(false);
            lobbyWorld.setStorm(false);

        } else {
            MessagesUtils.send(Bukkit.getConsoleSender(), "no_setup");
        }
        plugin.registerEvents(new JoinListener(game, playerManager, locationUtils), this);
        plugin.registerEvents(new ChatListener(playerManager, locationUtils, setupUtils), this);

    }

    @Override
    public void onDisable() {
    }

}
