package com.naotenhorgb.skyFight;

import com.naotenhorgb.skyFight.commands.HelloWorld;
import com.naotenhorgb.skyFight.commands.SetSpawnCommand;
import com.naotenhorgb.skyFight.listeners.general.*;
import com.naotenhorgb.skyFight.utils.LocationUtils;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class SkyFight extends JavaPlugin {

    private final LocationUtils locationUtils = new LocationUtils();
    private final Plugin plugin = this;
    private final World spawn = locationUtils.getSpawn().getWorld();

    @Override
    public void onEnable() {
        this.getCommand("setspawn").setExecutor(new SetSpawnCommand(locationUtils));
        this.getCommand("helloworld").setExecutor(new HelloWorld());

        getServer().getPluginManager().registerEvents(new JoinListener(locationUtils), this);
        getServer().getPluginManager().registerEvents(new DeathListener(locationUtils, plugin), this);
        getServer().getPluginManager().registerEvents(new HungerListener(), this);
        getServer().getPluginManager().registerEvents(new DropListener(), this);
        getServer().getPluginManager().registerEvents(new WeatherListener(), this);

        // TODO: test if this block works
        spawn.setGameRuleValue("ANNOUNCE_ADVANCEMENTS", "false");
        spawn.setGameRuleValue("DO_FIRE_TICK", "false");
        spawn.setGameRuleValue("DO_MOB_SPAWNING", "false");
        spawn.setGameRuleValue("DO_MOB_LOOT", "false");
        spawn.setGameRuleValue("SHOW_DEATH_MESSAGES", "false");
        spawn.setGameRuleValue("RANDOM_TICK_SPEED", "0");

        // this too
        locationUtils.getSpawn().getWorld().setSpawnFlags(false, false);
        locationUtils.getSpawn().getWorld().setPVP(true);
    }

    @Override
    public void onDisable() {
    }
}
