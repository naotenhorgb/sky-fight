package com.naotenhorgb.skyFight.commands;

import com.naotenhorgb.skyFight.data.PlayerStatus;
import com.naotenhorgb.skyFight.data.enums.StatusEnums;
import com.naotenhorgb.skyFight.managers.PlayerManager;
import com.naotenhorgb.skyFight.core.Game;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuildCommand implements CommandExecutor {

    private final PlayerManager playerManager;
    private final Game game;

    public BuildCommand(PlayerManager playerManager, Game game) {
        this.playerManager = playerManager;
        this.game = game;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return true;

        Player player = (Player) sender;
        PlayerStatus playerStatus = playerManager.getPlayerStatus(player);
        Enum<StatusEnums> status = playerStatus.getStatus();

        if (!player.hasPermission("skyfight.buildcommand")) return true;

        if (status == StatusEnums.ENTER_INGAME) {
            player.sendMessage("Necessário sair da partida para entrar no modo BUILD");
            return true;
        }

        if (status == StatusEnums.BUILD) {
            game.sendToLobby(player);
        } else {
            playerStatus.setStatus(StatusEnums.BUILD);
            player.setGameMode(GameMode.CREATIVE);
        }

        return false;
    }
}
