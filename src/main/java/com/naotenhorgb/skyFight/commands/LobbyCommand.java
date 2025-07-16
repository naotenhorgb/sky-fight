package com.naotenhorgb.skyFight.commands;

import com.naotenhorgb.skyFight.managers.IngameManager;
import com.naotenhorgb.skyFight.utils.Game;
import com.naotenhorgb.skyFight.utils.LocationUtils;
import com.naotenhorgb.skyFight.data.enums.StatusEnums;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LobbyCommand implements CommandExecutor {

    private final IngameManager ingameManager;
    private final Game game;
    private final LocationUtils locationUtils;

    public LobbyCommand(LocationUtils locationUtils, IngameManager ingameManager, Game game) {
        this.locationUtils = locationUtils;
        this.ingameManager = ingameManager;
        this.game = game;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        if (ingameManager.hasStatus(player, StatusEnums.INGAME)) {
            game.sendToGameSpawn(player);
            return true;
        } else {
            if (locationUtils.getGameSpawn().getWorld() == player.getWorld()) {
                game.sendToLobby(player);
            } else {
                game.sendToGameSpawn(player);
            }
        }
        return false;
    }
}
