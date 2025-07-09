package com.naotenhorgb.skyFight.commands;

import com.naotenhorgb.skyFight.utils.LocationUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {

    private final LocationUtils locationUtils;

    public SetSpawnCommand(LocationUtils locationUtils) {
        this.locationUtils = locationUtils;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Você só pode executar este comando estando ingame!");
            return false;
        }

        locationUtils.setLobbySpawn(((Player) sender).getLocation());
        sender.sendMessage("Spawn setado com sucesso!");
        ((Player) sender).teleport(locationUtils.getLobbySpawn());

        if (args.length > 0 && args[0].equalsIgnoreCase("debug")) {
            sender.sendMessage(locationUtils.getLobbySpawn().toString());
        }
        return false;
    }
}
