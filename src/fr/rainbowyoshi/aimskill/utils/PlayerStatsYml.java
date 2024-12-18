package fr.rainbowyoshi.aimskill.utils;

import org.bukkit.entity.Player;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;

public class PlayerStatsYml {

    public FileConfiguration getStatsPlayer(Player player) {
        String uuid = player.getUniqueId().toString();
        File statsFile = new File("plugins/AimSkill/players", uuid + ".yml");

        if (statsFile.exists()) {
            return YamlConfiguration.loadConfiguration(statsFile);
        } else {
            System.out.println("Le fichier de stats pour " + player.getName() + " n'existe pas.");
            return null;
        }
    }

    public void setStatsPlayer(Player player, int maxScore, int minScore, int totalScore, int totalGames) {
        String uuid = player.getUniqueId().toString();
        File statsFile = new File("plugins/AimSkill/players", uuid + ".yml");

        if (statsFile.exists()) {
            FileConfiguration config = YamlConfiguration.loadConfiguration(statsFile);
            config.set("playerName", player.getName());
            config.set("maxScore", maxScore);
            config.set("minScore", minScore);
            config.set("totalScore", totalScore);
            config.set("totalGames", totalGames);

            try {
                config.save(statsFile);
                System.out.println("Stats mises à jour pour le joueur " + player.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Impossible de mettre à jour les stats. Le fichier pour " + player.getName() + " n'existe pas.");
        }
    }

    public void createStatsPlayerFile(Player player) {
        String uuid = player.getUniqueId().toString();
        File playerFolder = new File("plugins/AimSkill/players");
        if (!playerFolder.exists()) {
            playerFolder.mkdirs();
        }

        File statsFile = new File(playerFolder, uuid + ".yml");

        if (!statsFile.exists()) {
            try {
                statsFile.createNewFile();

                FileConfiguration config = YamlConfiguration.loadConfiguration(statsFile);
                config.set("playerName", player.getName());
                config.set("maxScore", 0);
                config.set("minScore", 0);
                config.set("totalScore", 0);
                config.set("totalGames", 0);
                config.save(statsFile);

                System.out.println("Fichier de stats créé pour le joueur " + player.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Le fichier de stats pour " + player.getName() + " existe déjà.");
        }
    }
}
