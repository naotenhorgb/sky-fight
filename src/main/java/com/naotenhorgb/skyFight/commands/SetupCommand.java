package com.naotenhorgb.skyFight.commands;

import com.naotenhorgb.skyFight.core.Cuboid;
import com.naotenhorgb.skyFight.core.LocationUtils;
import com.naotenhorgb.skyFight.core.MessagesUtils;
import com.naotenhorgb.skyFight.core.SetupUtils;
import com.naotenhorgb.skyFight.data.PlayerStatus;
import com.naotenhorgb.skyFight.data.SkyfightConfig;
import com.naotenhorgb.skyFight.data.enums.StatusEnums;
import com.naotenhorgb.skyFight.managers.PlayerManager;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.stream.Stream;

public class SetupCommand implements CommandExecutor {

    private final LocationUtils locationUtils;
    private final PlayerManager playerManager;
    private final SetupUtils setupUtils;

    private Location safezonePos1;
    private Location safezonePos2;
    private Location borderPos1;
    private Location borderPos2;
    private Location safezoneSpawn;
    private Location lobbySpawn;
    private Location voidYloc;

    private World gameWorld;
    private World lobbyWorld;
    private boolean autoActivated;


    public SetupCommand(LocationUtils locationUtils, PlayerManager playerManager, SetupUtils setupUtils) {
        this.locationUtils = locationUtils;
        this.playerManager = playerManager;
        this.setupUtils = setupUtils;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return true;

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

        switch (subcommand) {
            case "auto":
                if(setupUtils.getExecutor() != null) {
                    MessagesUtils.send(player, "setup_auto_executing");
                    return true;
                }
                player.setGameMode(GameMode.CREATIVE);
                playerStatus.setStatus(StatusEnums.BUILD);
                MessagesUtils.send(player, "setup_auto");
                break;
            case "lobbyspawn":
                if (setupUtils.isWorldEqual(location, player, gameWorld)) return true;
                MessagesUtils.sendWithSetupPrefix(player, "setup_lobbyspawn");
                lobbySpawn = location;
                lobbyWorld = location.getWorld();
                locationUtils.setLobbySpawn(location);
                break;
            case "safezonespawn":
                if (setupUtils.isWorldEqual(location, player, lobbyWorld)) return true;
                MessagesUtils.sendWithSetupPrefix(player, "setup_safezonespawn");
                safezoneSpawn = location;
                gameWorld = location.getWorld();
                locationUtils.setGameSpawn(location);
                break;
            case "safezonepos1":
                if (setupUtils.isWorldEqual(location, player, lobbyWorld)) return true;
                MessagesUtils.sendWithSetupPrefix(player, "setup_safezonepos1");
                gameWorld = location.getWorld();
                safezonePos1 = location;
                break;
            case "safezonepos2":
                if (setupUtils.isWorldEqual(location, player, lobbyWorld)) return true;
                MessagesUtils.sendWithSetupPrefix(player, "setup_safezonepos2");
                gameWorld = location.getWorld();
                safezonePos2 = location;
                break;
            case "void":
                if (setupUtils.isWorldEqual(location, player, lobbyWorld)) return true;
                MessagesUtils.sendWithSetupPrefix(player, "setup_void");
                gameWorld = location.getWorld();
                voidYloc = location;
                locationUtils.setDeathY(location.getBlockY());
                break;
            case "borderpos1":
                if (setupUtils.isWorldEqual(location, player, lobbyWorld)) return true;
                MessagesUtils.sendWithSetupPrefix(player, "setup_borderpos1");
                gameWorld = location.getWorld();
                borderPos1 = location;
                break;
            case "borderpos2":
                if (setupUtils.isWorldEqual(location, player, lobbyWorld)) return true;
                MessagesUtils.sendWithSetupPrefix(player, "setup_borderpos2");
                gameWorld = location.getWorld();
                borderPos2 = location;
                break;
            default:
                MessagesUtils.send(player, "setup_help");
        }
        if (setupUtils.bothPosAreSet(safezonePos1, safezonePos2))
            locationUtils.setGameSafezone(new Cuboid(safezonePos1, safezonePos2));
        if (setupUtils.bothPosAreSet(borderPos1, borderPos2))
            locationUtils.setGameBoundaries(new Cuboid(borderPos1, borderPos2));
        if (everythingSet()) {
            MessagesUtils.sendWithSetupPrefix(player, "setup_finalized");
            SkyfightConfig.load().save();
        }
        return true;
    }


    public boolean everythingSet() {
        return Stream.of(lobbySpawn, safezoneSpawn, safezonePos1, safezonePos2, voidYloc, borderPos1, borderPos2)
                .noneMatch(Objects::isNull);
    }

}
