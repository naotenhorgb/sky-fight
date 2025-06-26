package com.naotenhorgb.skyFight;

import com.naotenhorgb.skyFight.commands.HelloWorld;
import com.naotenhorgb.skyFight.commands.SetSpawnCommand;
import com.naotenhorgb.skyFight.listeners.general.*;
import com.naotenhorgb.skyFight.utils.LocationUtils;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class SkyFight extends JavaPlugin {

    private final LocationUtils locationUtils = new LocationUtils();
    private final Plugin plugin = this;

    @Override
    public void onEnable() {
        this.getCommand("setspawn").setExecutor(new SetSpawnCommand(locationUtils));
        this.getCommand("helloworld").setExecutor(new HelloWorld());

        getServer().getPluginManager().registerEvents(new JoinListener(locationUtils), this);
        getServer().getPluginManager().registerEvents(new DeathListener(locationUtils, plugin), this);
        getServer().getPluginManager().registerEvents(new HungerListener(), this);
        getServer().getPluginManager().registerEvents(new DropListener(), this);
        getServer().getPluginManager().registerEvents(new WeatherListener(), this);

        locationUtils.getSpawn().getWorld().setGameRuleValue("ANNOUNCE_ADVANCEMENTS", "false");
        locationUtils.getSpawn().getWorld().setGameRuleValue("DO_FIRE_TICK", "false");
        locationUtils.getSpawn().getWorld().setGameRuleValue("DO_MOB_SPAWNING", "false");
        locationUtils.getSpawn().getWorld().setGameRuleValue("DO_MOB_LOOT", "false");
        locationUtils.getSpawn().getWorld().setGameRuleValue("SHOW_DEATH_MESSAGES", "false");
        locationUtils.getSpawn().getWorld().setGameRuleValue("RANDOM_TICK_SPEED", "0");

        locationUtils.getSpawn().getWorld().setSpawnFlags(false, false);
        locationUtils.getSpawn().getWorld().setPVP(true);

    }

    @Override
    public void onDisable() {
    }
}
