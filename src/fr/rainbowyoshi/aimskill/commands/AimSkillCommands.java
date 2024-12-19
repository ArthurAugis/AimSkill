package fr.rainbowyoshi.aimskill.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import fr.rainbowyoshi.aimskill.utils.PlayerStatsYml;
import fr.rainbowyoshi.aimskill.utils.ArenaManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class AimSkillCommands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(!(sender instanceof org.bukkit.entity.Player)) {
            sender.sendMessage("You must be a player to use this command.");
            return true;
        }

        Player player = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("aimskill") && args.length == 0) {
            sender.sendMessage("AimSkill commands:");
            sender.sendMessage("/aimskill help - Display this help message.");
            sender.sendMessage("/aimskill stats - Display your stats.");
            return true;
        }
        else if(args.length == 1 && cmd.getName().equalsIgnoreCase("aimskill") && args[0].equalsIgnoreCase("help")) {
            sender.sendMessage("AimSkill commands:");
            sender.sendMessage("/aimskill help - Display this help message.");
            sender.sendMessage("/aimskill stats - Display your stats.");
            return true;
        } else if (args.length == 1 && cmd.getName().equalsIgnoreCase("aimskill") && args[0].equalsIgnoreCase("stats")) {
            PlayerStatsYml playerStatsYml = new PlayerStatsYml();
            FileConfiguration stats = playerStatsYml.getStatsPlayer(player);

            if (stats != null) {
                sender.sendMessage("Stats for " + stats.getString("playerName") + ":");
                sender.sendMessage("Max score: " + stats.getInt("maxScore"));
                sender.sendMessage("Min score: " + stats.getInt("minScore"));
                sender.sendMessage("Total score: " + stats.getInt("totalScore"));
                sender.sendMessage("Total games: " + stats.getInt("totalGames"));
            } else {
                sender.sendMessage("You don't have any stats yet.");
            }

            return true;
        } else if(cmd.getName().equalsIgnoreCase("aimskill") && args[0].equalsIgnoreCase("arena")) {
            ArenaManager arenaManager = new ArenaManager();
            if(args.length >= 2 && args[1].equalsIgnoreCase("create")) {
                if(args.length == 2) {
                    sender.sendMessage("Usage: /aimskill arena create <arenaName>");
                    return true;
                } else {
                    String arenaName = "";
                    for(int i = 2; i < args.length; i++) {
                        arenaName += args[i] + " ";
                    }

                    arenaName = arenaName.substring(0, arenaName.length() - 1);

                    Location loc = player.getLocation();
                    double adjustedX = Math.floor(loc.getX()) + 0.5;
                    double adjustedY = Math.floor(loc.getY());
                    double adjustedZ = Math.floor(loc.getZ()) + 0.5;

                    player.teleport(new Location(loc.getWorld(), adjustedX, adjustedY, adjustedZ));

                    player.sendMessage(arenaManager.createArena(arenaName, adjustedX, adjustedY, adjustedZ, loc.getWorld().getName()));

                }
            }
        }

        return false;
    }
}
