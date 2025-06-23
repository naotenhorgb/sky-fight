package com.naotenhorgb.skyFight;

import com.naotenhorgb.skyFight.commands.HelloWorld;
import com.naotenhorgb.skyFight.commands.SetSpawnCommand;
import com.naotenhorgb.skyFight.listeners.onJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class SkyFight extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("setspawn").setExecutor(new SetSpawnCommand());
        this.getCommand("helloworld").setExecutor(new HelloWorld());

        getServer().getPluginManager().registerEvents(new onJoinEvent(), this);
    }

    @Override
    public void onDisable() {
    }
}
