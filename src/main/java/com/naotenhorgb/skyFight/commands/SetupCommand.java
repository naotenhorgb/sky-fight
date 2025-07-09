package com.naotenhorgb.skyFight.commands;

import com.naotenhorgb.skyFight.utils.Cuboid;
import com.naotenhorgb.skyFight.utils.LocationUtils;
import com.naotenhorgb.skyFight.utils.MatchMaterial;
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
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        boolean skipNext = args.length > 0 && args[0].equalsIgnoreCase("skip");

        switch (section) {
            case 0:
                player.sendMessage("Utilize /setup para setar o nPasso");
                player.sendMessage("Utilize /setup skip para pular o próximo passo");
                player.sendMessage("Próximo passo: setar o lobby do skyfight");
                ++section;
                break;

            case 1:
                player.sendMessage("Setando spawn fora do skyfight...");
                player.sendMessage("O mundo deve ser diferente do mundo anterior.");
                player.sendMessage("Próximo passo: setar spawn fora do skyfight");
                if (!skipNext) {
                    locationUtils.setLobbySpawn(player.getLocation());
                }
                ++section;
                break;

            case 2:
                if (!skipNext) {
                    if (locationUtils.getLobbySpawn().getWorld() == player.getWorld()) {
                        player.sendMessage("O mundo é o mesmo que o lobby...");
                        return true;
                    }
                    locationUtils.setGameSpawn(player.getLocation());
                }
                player.sendMessage("Setando spawn dentro do skyfight...");
                player.sendMessage("Próximo passo: setar posição 1 da zona segura");
                ++section;
                break;

            case 3:
                player.sendMessage("Setando a posição 1 da zona segura...");
                player.sendMessage("Próximo passo: setar posição 2 da zona segura");
                if (!skipNext) {
                    pos1 = player.getLocation();
                }
                ++section;
                break;

            case 4:
                player.sendMessage("Setando a posição 2 da zona segura...");
                player.sendMessage("Próximo passo: setar posição 1 da zona exterior");
                player.sendMessage("(É até onde o player pode ir, se ele sair dessa área, é considerado morte)");
                if (!skipNext) {
                    locationUtils.setGameSafezone(new Cuboid(pos1, player.getLocation()));
                }

                ItemStack redWool = materialConverter.getItemStack("RED_WOOL");
                ItemStack greenWool = materialConverter.getItemStack("GREEN_WOOL");

                player.sendBlockChange(pos1, redWool.getType(), redWool.getData().getData());
                player.sendBlockChange(pos1, greenWool.getType(), redWool.getData().getData());
                ++section;
                break;
            case 5:
                player.sendMessage("Setando a posição 1 da zona exterior");
                player.sendMessage("Próximo passo: setar posição 2 da zona exterior");
                if (!skipNext) {
                    pos1 = player.getLocation();
                }
                ++section;
                break;
            case 6:
                player.sendMessage("Setando a posição 2 da zona exterior");
                player.sendMessage("Setup encerrado.");

                int safeMin = locationUtils.getGameSafezone().getyMin();

                Location corner1 = pos1.clone();
                Location corner2 = player.getLocation().clone();

                corner1.setY(Math.min(corner1.getBlockY(), safeMin - 1));
                corner2.setY(Math.min(corner2.getBlockY(), safeMin - 1));

                if (!skipNext) {
                    locationUtils.setGameBoundaries(new Cuboid(corner1, corner2));
                }

                ItemStack blueWool   = materialConverter.getItemStack("BLUE_WOOL");
                ItemStack yellowWool = materialConverter.getItemStack("YELLOW_WOOL");
                player.sendBlockChange(corner1, blueWool.getType(),   blueWool.getData().getData());
                player.sendBlockChange(corner2, yellowWool.getType(), yellowWool.getData().getData());

                locationUtils.getConfig().save();
                ++section;
                break;
            default:
                player.sendMessage("Resetando setup, use o comando novamente para iniciar o /setup");
                section = 0;
                break;
        }
        return false;
    }
}

