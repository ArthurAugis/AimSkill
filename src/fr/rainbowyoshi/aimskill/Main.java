package fr.rainbowyoshi.aimskill;

import fr.rainbowyoshi.aimskill.commands.AimSkillCommands;
import fr.rainbowyoshi.aimskill.events.Join;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        getLogger().info("AimSkill has been enabled!");
        getServer().getPluginManager().registerEvents(new Join(), this);
        Bukkit.getPluginCommand("aimskill").setExecutor(new AimSkillCommands());
    }

    @Override
    public void onDisable() {
        getLogger().info("AimSkill has been disabled!");
    }
}
