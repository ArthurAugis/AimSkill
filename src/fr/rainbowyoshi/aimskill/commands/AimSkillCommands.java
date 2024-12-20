package fr.rainbowyoshi.aimskill.commands;

import fr.rainbowyoshi.aimskill.utils.ArenaManager;
import fr.rainbowyoshi.aimskill.utils.PlayerStatsYml;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

                    loc.getWorld().getBlockAt(new Location(loc.getWorld(), adjustedX, adjustedY - 1, adjustedZ)).setType(Material.SEA_LANTERN);

                    player.teleport(new Location(loc.getWorld(), adjustedX, adjustedY, adjustedZ));

                    player.sendMessage(arenaManager.createArena(arenaName, adjustedX, adjustedY, adjustedZ, loc.getWorld().getName()));

                }
            } else if(args.length >= 2 && args[1].equalsIgnoreCase("delete")) {
                if(args.length == 2) {
                    sender.sendMessage("Usage: /aimskill arena delete <arenaName>");
                    return true;
                } else {
                    String arenaName = "";
                    for(int i = 2; i < args.length; i++) {
                        arenaName += args[i] + " ";
                    }
                    arenaName = arenaName.substring(0, arenaName.length() - 1);
                    player.sendMessage(arenaManager.deleteArena(arenaName));
                    return true;
                }
            } else if(args.length == 2 && args[1].equalsIgnoreCase("list")) {
                String[] arenas = arenaManager.getArenas();
                if(arenas.length == 0) {
                    player.sendMessage("No arenas found.");
                } else {
                    for(String arena : arenas) {
                        player.sendMessage(arena);
                    }
                }
            }
        } else if(args[0].equalsIgnoreCase("top")) {
            if(args.length == 1) {
                player.sendMessage("Usage: /aimskill top <score|totalscore|totalgames>");
            } else if(args.length == 2) {
                if (args[1].equalsIgnoreCase("score")) {
                    PlayerStatsYml playerStatsYml = new PlayerStatsYml();
                    HashMap<String, Integer> topScores = playerStatsYml.getTop("maxScore");

                    if (topScores.isEmpty()) {
                        player.sendMessage("§cNo scores found.");
                    } else {
                        player.sendMessage("§aTop 10 des meilleurs scores :");

                        List<Map.Entry<String, Integer>> sortedScores = new ArrayList<>(topScores.entrySet());
                        int position = 1;
                        boolean playerInTop10 = false;

                        for (Map.Entry<String, Integer> entry : sortedScores) {
                            if (position <= 10) {
                                player.sendMessage("§e#" + position + " §f" + entry.getKey() + " : §b" + entry.getValue());
                                if (entry.getKey().equalsIgnoreCase(player.getName())) {
                                    playerInTop10 = true;
                                }
                            }
                            position++;
                        }

                        if (!playerInTop10) {
                            int playerScore = playerStatsYml.getStatsPlayer(player).getInt("maxScore");
                            int playerPosition = 1;

                            for (Map.Entry<String, Integer> entry : sortedScores) {
                                if (entry.getValue() < playerScore) {
                                    break;
                                }
                                playerPosition++;
                            }

                            player.sendMessage("§7Vous êtes classé §6#" + playerPosition + "§7 avec un score de §b" + playerScore);
                        }
                    }

                } else if (args[1].equalsIgnoreCase("totalscore")) {
                    PlayerStatsYml playerStatsYml = new PlayerStatsYml();
                    HashMap<String, Integer> topScores = playerStatsYml.getTop("totalScore");

                    if (topScores.isEmpty()) {
                        player.sendMessage("§cNo scores found.");
                    } else {
                        player.sendMessage("§aTop 10 des scores globaux :");

                        List<Map.Entry<String, Integer>> sortedScores = new ArrayList<>(topScores.entrySet());
                        int position = 1;
                        boolean playerInTop10 = false;

                        for (Map.Entry<String, Integer> entry : sortedScores) {
                            if (position <= 10) {
                                player.sendMessage("§e#" + position + " §f" + entry.getKey() + " : §b" + entry.getValue());
                                if (entry.getKey().equalsIgnoreCase(player.getName())) {
                                    playerInTop10 = true;
                                }
                            }
                            position++;
                        }

                        if (!playerInTop10) {
                            int playerScore = playerStatsYml.getStatsPlayer(player).getInt("totalScore");
                            int playerPosition = 1;

                            for (Map.Entry<String, Integer> entry : sortedScores) {
                                if (entry.getValue() < playerScore) {
                                    break;
                                }
                                playerPosition++;
                            }

                            player.sendMessage("§7Vous êtes classé §6#" + playerPosition + "§7 avec un score global de §b" + playerScore);
                        }
                    }
                } else if (args[1].equalsIgnoreCase("totalgames")) {
                    PlayerStatsYml playerStatsYml = new PlayerStatsYml();
                    HashMap<String, Integer> topScores = playerStatsYml.getTop("totalGames");

                    if (topScores.isEmpty()) {
                        player.sendMessage("§cNo scores found.");
                    } else {
                        player.sendMessage("§aTop 10 du nombre de parties jouées :");

                        List<Map.Entry<String, Integer>> sortedScores = new ArrayList<>(topScores.entrySet());
                        int position = 1;
                        boolean playerInTop10 = false;

                        for (Map.Entry<String, Integer> entry : sortedScores) {
                            if (position <= 10) {
                                player.sendMessage("§e#" + position + " §f" + entry.getKey() + " : §b" + entry.getValue());
                                if (entry.getKey().equalsIgnoreCase(player.getName())) {
                                    playerInTop10 = true;
                                }
                            }
                            position++;
                        }

                        if (!playerInTop10) {
                            int playerScore = playerStatsYml.getStatsPlayer(player).getInt("totalScore");
                            int playerPosition = 1;

                            for (Map.Entry<String, Integer> entry : sortedScores) {
                                if (entry.getValue() < playerScore) {
                                    break;
                                }
                                playerPosition++;
                            }

                            player.sendMessage("§7Vous êtes classé §6#" + playerPosition + "§7 avec un nombre de parties de §b" + playerScore);
                        }
                    }
                }
            }
        }

        return false;
    }
}
