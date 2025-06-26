package com.naotenhorgb.skyFight.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class HelloWorld implements CommandExecutor {

    // TODO: remove hello world command and class

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        sender.sendMessage("Olá mundo!");
        return false;
    }
}
