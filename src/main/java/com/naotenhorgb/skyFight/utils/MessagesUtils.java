package com.naotenhorgb.skyFight.utils;

import com.naotenhorgb.skyFight.data.MessagesConfig;
import org.bukkit.ChatColor;

public class MessagesUtils {

    public static String getPrefix() {
        return color(MessagesConfig.get().prefix);
    }

    public static String format(String msg) {
        return color(msg.replace("{prefix}", getPrefix()));
    }

    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
