package com.naotenhorgb.skyFight.commands;

import com.naotenhorgb.skyFight.utils.LocationUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {

    private final LocationUtils locationUtils;

    public SetSpawnCommand() {
        this.locationUtils = new LocationUtils();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (sender instanceof Player) {

            locationUtils.setSpawn(((Player) sender).getLocation());
            sender.sendMessage("Spawn setado com sucesso!");
            ((Player) sender).teleport(locationUtils.getSpawn());

            if (args[0].equalsIgnoreCase("debug")) {
                sender.sendMessage(locationUtils.getSpawn().toString());
            }
        }
        sender.sendMessage("Você só pode executar este comando estando ingame!");
        return false;
    }
}
