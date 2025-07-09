package com.naotenhorgb.skyFight.commands;

import com.naotenhorgb.skyFight.managers.IngameManager;
import com.naotenhorgb.skyFight.utils.Game;
import com.naotenhorgb.skyFight.utils.StatusEnums;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuildCommand implements CommandExecutor {

    private final IngameManager ingameManager;
    private final Game game;

    public BuildCommand(IngameManager ingameManager, Game game) {
        this.ingameManager = ingameManager;
        this.game = game;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        if (ingameManager.hasStatus(player, StatusEnums.BUILD)) {
            game.sendToLobby(player);
        } else {
            ingameManager.setPlayer(player, StatusEnums.BUILD);
            player.setGameMode(GameMode.CREATIVE);
        }
        return false;
    }
}
