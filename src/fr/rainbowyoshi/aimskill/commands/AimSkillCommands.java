package fr.rainbowyoshi.aimskill.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import fr.rainbowyoshi.aimskill.utils.PlayerStatsYml;
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
        }

        return false;
    }
}
