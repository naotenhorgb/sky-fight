package com.naotenhorgb.skyFight.commands;

import com.naotenhorgb.skyFight.core.Cuboid;
import com.naotenhorgb.skyFight.core.LocationUtils;
import com.naotenhorgb.skyFight.core.MessagesUtils;
import com.naotenhorgb.skyFight.data.PlayerStatus;
import com.naotenhorgb.skyFight.data.enums.StatusEnums;
import com.naotenhorgb.skyFight.managers.PlayerManager;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetupCommand implements CommandExecutor {

    private final LocationUtils locationUtils;
    private final PlayerManager playerManager;

    private Location safezonePos1;
    private Location safezonePos2;
    private Location borderPos1;
    private Location borderPos2;

    public SetupCommand(LocationUtils locationUtils, PlayerManager playerManager) {
        this.locationUtils = locationUtils;
        this.playerManager = playerManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;

        Player player = (Player) sender;
        Location location = player.getLocation();
        PlayerStatus playerStatus = playerManager.getPlayerStatus(player);

        if (args.length == 0) {
            MessagesUtils.sendWithSetupPrefix(player, "setup_help");
            return true;
        }

        String subcommand = args[0].toLowerCase();

        if (location.getY() < 0 && location.getY() > 255) {
            MessagesUtils.sendWithSetupPrefix(player, "setup_invalidY");
            return true;
        }

        // todo: add verification for gameSpawn being outside safezone

        switch (subcommand) {
            case "auto":
                playerStatus.setStatus(StatusEnums.BUILD);
                MessagesUtils.send(player, "setup_auto");
                break;
            case "lobbyspawn":
                if (location.getWorld() == locationUtils.getGameSpawn().getWorld()) {
                    MessagesUtils.sendWithSetupPrefix(player, "setup_same_world");
                    return true;
                }
                MessagesUtils.sendWithSetupPrefix(player, "setup_lobbyspawn");
                locationUtils.setLobbySpawn(location);
                break;
            case "safezonespawn":
                if (isWorldLobby(location, player)) return true;
                MessagesUtils.sendWithSetupPrefix(player, "setup_safezonespawn");
                locationUtils.setGameSpawn(location);
                break;
            case "safezonepos1":
                if (isWorldLobby(location, player)) return true;
                MessagesUtils.sendWithSetupPrefix(player, "setup_safezonepos1");
                safezonePos1 = location;
                break;
            case "safezonepos2":
                if (isWorldLobby(location, player)) return true;
                MessagesUtils.sendWithSetupPrefix(player, "setup_safezonepos2");
                safezonePos2 = location;
                break;
            case "void":
                if (isWorldLobby(location, player)) return true;
                MessagesUtils.sendWithSetupPrefix(player, "setup_void");
                locationUtils.setDeathY(location.getBlockY());
                break;
            case "borderpos1":
                if (isWorldLobby(location, player)) return true;
                MessagesUtils.sendWithSetupPrefix(player, "setup_borderpos1");
                borderPos1 = location;
                break;
            case "borderpos2":
                if (isWorldLobby(location, player)) return true;
                MessagesUtils.sendWithSetupPrefix(player, "setup_borderpos2");
                borderPos2 = location;
                break;
            case "reset":
                MessagesUtils.sendWithSetupPrefix(player, "setup_reset");
                break;
            default:
                MessagesUtils.send(player, "setup_help");
        }
        if (bothPosAreSet(safezonePos1, safezonePos2))
            locationUtils.setGameSafezone(new Cuboid(safezonePos1, safezonePos2));
        if (bothPosAreSet(borderPos1, borderPos2))
            locationUtils.setGameBoundaries(new Cuboid(borderPos1, borderPos2));
        return true;
    }

    private boolean bothPosAreSet(Location pos1, Location pos2) {
        return pos1 != null && pos2 != null;
    }

    private boolean isWorldLobby(Location location, Player player) {
        if (location.getWorld() == locationUtils.getLobbySpawn().getWorld()) {
            MessagesUtils.sendWithSetupPrefix(player, "setup_same_world");
            return true;
        }
        return false;
    }

}
