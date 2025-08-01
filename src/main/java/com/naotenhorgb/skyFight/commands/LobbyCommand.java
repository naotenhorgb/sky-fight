package com.naotenhorgb.skyFight.commands;

import com.naotenhorgb.skyFight.core.Game;
import com.naotenhorgb.skyFight.core.LocationUtils;
import com.naotenhorgb.skyFight.data.enums.StatusEnums;
import com.naotenhorgb.skyFight.managers.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LobbyCommand implements CommandExecutor {

    private final PlayerManager playerManager;
    private final Game game;
    private final LocationUtils locationUtils;

    public LobbyCommand(LocationUtils locationUtils, PlayerManager playerManager, Game game) {
        this.locationUtils = locationUtils;
        this.playerManager = playerManager;
        this.game = game;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player player)) {
            return true;
        }
        if (playerManager.getPlayerStatus(player).getStatus() == StatusEnums.ENTER_INGAME) {
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
