package fr.rainbowyoshi.aimskill.utils;

import org.bukkit.entity.Player;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

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

    public HashMap<String, Integer> getTop(String type) {
        File playerFolder = new File("plugins/AimSkill/players");
        HashMap<String, Integer> playerScores = new HashMap<>();

        if (playerFolder.exists() && playerFolder.isDirectory()) {
            for (File file : playerFolder.listFiles()) {
                if (file.isFile() && file.getName().endsWith(".yml")) {
                    FileConfiguration config = YamlConfiguration.loadConfiguration(file);
                    String playerName = config.getString("playerName");
                    int maxScore = config.getInt(type);
                    if (playerName != null) {
                        playerScores.put(playerName, maxScore);
                    }
                }
            }

            return playerScores.entrySet()
                    .stream()
                    .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (e1, e2) -> e1,
                            LinkedHashMap::new));
        } else {
            System.out.println("Le dossier de stats n'existe pas.");
            return new HashMap<>();
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

    public String generateTestStatsFiles(int number) {
        File playerFolder = new File("plugins/AimSkill/players");

        if (!playerFolder.exists()) {
            playerFolder.mkdirs();
        }

        for (int i = 1; i <= number; i++) {
            String uuid = UUID.randomUUID().toString();

            File statsFile = new File(playerFolder, uuid + ".yml");

            if (!statsFile.exists()) {
                try {
                    statsFile.createNewFile();

                    FileConfiguration config = YamlConfiguration.loadConfiguration(statsFile);
                    config.set("playerName", "TestPlayer" + i);
                    config.set("maxScore", (int) (Math.random() * 1000));
                    config.set("minScore", (int) (Math.random() * 100));
                    config.set("totalScore", (int) (Math.random() * 5000));
                    config.set("totalGames", (int) (Math.random() * 50));

                    // Sauvegarde dans le fichier
                    config.save(statsFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return number + " fichiers de stats de test ont été créés.";
    }
}
