package com.naotenhorgb.skyFight.core;

import com.naotenhorgb.skyFight.data.MessagesConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class MessagesUtils {

    // todo: large cleanup

    public static String getPrefix() {
        return color(MessagesConfig.get().prefix);
    }

    public static String getSetupPrefix() {
        return color(MessagesConfig.get().setup_prefix);
    }

    public static String format(String msg) {
        return color(msg.replace("{prefix}", getPrefix()));
    }

    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static void send(CommandSender sender, String key) {
        String msg = MessagesConfig.get(key);
        for (String line : msg.split("\n")) {
            sender.sendMessage(getPrefix() + " " + line);
        }
    }

    public static void sendWithSetupPrefix(CommandSender sender, String key) {
        String msg = MessagesConfig.get(key);
        for (String line : msg.split("\n")) {
            sender.sendMessage(getSetupPrefix() + " " + line);
        }
    }
}
