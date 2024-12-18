package fr.rainbowyoshi.aimskill.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import fr.rainbowyoshi.aimskill.utils.PlayerStatsYml;

public class Join implements Listener {

    @EventHandler
    public void onJoin(org.bukkit.event.player.PlayerJoinEvent event) {
        org.bukkit.entity.Player player = event.getPlayer();
        player.sendMessage("Welcome to AimSkill, " + player.getName() + "!");
        PlayerStatsYml playerStatsYml = new PlayerStatsYml();
        playerStatsYml.createStatsPlayerFile(player);
    }

}
