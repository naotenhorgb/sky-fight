package com.naotenhorgb.skyFight.commands;

import com.naotenhorgb.skyFight.data.PlayerStatus;
import com.naotenhorgb.skyFight.managers.PlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SendStatusCommand implements CommandExecutor {

    private final PlayerManager playerManager;

    public SendStatusCommand(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    // temp cmd

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        PlayerStatus playerStatus = playerManager.getPlayerStatus(player);
        player.sendMessage("Status: " + playerStatus.getStatus().toString());
        if (playerStatus.getAttacker() != null) player.sendMessage("Attacker: " + playerStatus.getAttacker());
        player.sendMessage("Deaths: " + playerStatus.getStatDeaths());
        player.sendMessage("Kills: " + playerStatus.getStatKills());
        player.sendMessage("Coins: " + playerStatus.getCoins());

        return false;
    }
}
