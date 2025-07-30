package com.naotenhorgb.skyFight.core;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

@Setter
@Getter
public class SetupUtils {

    private World gameWorld;
    private World lobbyWorld;
    private boolean autoActivated;
    private Player executor;

    public boolean bothPosAreSet(Location pos1, Location pos2) {
        return pos1 != null && pos2 != null;
    }

    public boolean isWorldEqual(Location location, Player player, World world) {
        player.sendMessage(world + "" + location.getWorld());
        if (world == null) {
            Bukkit.getLogger().severe("Mundo é null");
            return false;
        }
        if (location.getWorld() == world) {
            MessagesUtils.sendWithSetupPrefix(player, "setup_same_world");
            return true;
        }

        return false;
    }

}
