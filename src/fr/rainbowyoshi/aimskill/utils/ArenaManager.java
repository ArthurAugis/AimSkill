package fr.rainbowyoshi.aimskill.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;

public class ArenaManager {

    public String createArena(String arenaName, double x, double y, double z, String world) {
        File arenasFile = new File("plugins/AimSkill", "Arenas.yml");

        if (!arenasFile.exists()) {
            try {
                arenasFile.createNewFile();
                System.out.println("Fichier Arenas.yml créé.");
            } catch (IOException e) {
                e.printStackTrace();
                return "Une erreur est survenue lors de la création de l'arène.";
            }
        }
        FileConfiguration arenasConfig = YamlConfiguration.loadConfiguration(arenasFile);
        String path = "arenas." + arenaName;

        if (arenasConfig.contains(path)) {
            return "L'arène '" + arenaName + "' existe déjà !";
        }

        arenasConfig.set(path + ".name", arenaName);
        arenasConfig.set(path + ".x", x);
        arenasConfig.set(path + ".y", y);
        arenasConfig.set(path + ".z", z);
        arenasConfig.set(path + ".world", world);

        try {
            arenasConfig.save(arenasFile);
            return "Arène '" + arenaName + "' ajoutée avec succès !";
        } catch (IOException e) {
            e.printStackTrace();
            return "Une erreur est survenue lors de l'ajout de l'arène.";
        }
    }

}
