package com.naotenhorgb.skyFight.commands;

import com.naotenhorgb.skyFight.data.MessagesConfig;
import com.naotenhorgb.skyFight.data.SkyfightConfig;
import com.naotenhorgb.skyFight.utils.Cuboid;
import com.naotenhorgb.skyFight.utils.LocationUtils;
import com.naotenhorgb.skyFight.utils.MatchMaterial;
import com.naotenhorgb.skyFight.utils.MessagesUtils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("deprecation")
public class SetupCommand implements CommandExecutor {
    private final LocationUtils locationUtils;
    private final MatchMaterial materialConverter = new MatchMaterial();

    public SetupCommand(LocationUtils locationUtils) {
        this.locationUtils = locationUtils;
    }

    private int section = 0;
    private Location pos1;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        switch (section) {
            case 0:
                MessagesConfig.send(player, "setup_0");
                ++section;
                break;
            case 1:
                MessagesConfig.send(player, "setup_1");
                locationUtils.setLobbySpawn(player.getLocation());
                ++section;
                break;
            case 2:
                if (locationUtils.getLobbySpawn().getWorld() == player.getWorld()) {
                    player.sendMessage(MessagesUtils.getPrefix() + " " + MessagesConfig.get("setup_same_world"));
                    return true;
                }
                locationUtils.setGameSpawn(player.getLocation());
                MessagesConfig.send(player, "setup_2");
                ++section;
                break;
            case 3:
                MessagesConfig.send(player, "setup_3");
                pos1 = player.getLocation();
                ++section;
                break;
            case 4:
                MessagesConfig.send(player, "setup_4");
                locationUtils.setGameSafezone(new Cuboid(pos1, player.getLocation()));

                ItemStack redWool = materialConverter.getItemStack("RED_WOOL");
                ItemStack greenWool = materialConverter.getItemStack("GREEN_WOOL");

                player.sendBlockChange(pos1, redWool.getType(), redWool.getData().getData());
                player.sendBlockChange(player.getLocation(), greenWool.getType(), redWool.getData().getData());
                ++section;
                break;
            case 5:
                MessagesConfig.send(player, "setup_5");
                locationUtils.setDeathY(player.getLocation().getBlockY());
                ++section;
                break;
            case 6:
                MessagesConfig.send(player, "setup_6");
                pos1 = player.getLocation();
                ++section;
                break;
            case 7:
                MessagesConfig.send(player, "setup_7");

                Location corner1 = pos1.clone();
                Location corner2 = player.getLocation().clone();
                locationUtils.setGameBoundaries(new Cuboid(corner1, corner2));

                ItemStack blueWool = materialConverter.getItemStack("BLUE_WOOL");
                ItemStack yellowWool = materialConverter.getItemStack("YELLOW_WOOL");

                player.sendBlockChange(corner1, blueWool.getType(), blueWool.getData().getData());
                player.sendBlockChange(corner2, yellowWool.getType(), yellowWool.getData().getData());

                SkyfightConfig.load().setuped = "true";
                SkyfightConfig.load().save();
                // todo: add register events
                ++section;
                break;
            default:
                MessagesConfig.send(player, "setup_reset");
                section = 0;
                break;
        }
        return false;
    }
}
