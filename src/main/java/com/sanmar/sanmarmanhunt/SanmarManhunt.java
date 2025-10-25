package com.sanmar.sanmarmanhunt;

import com.destroystokyo.paper.event.server.ServerTickStartEvent;
import com.sanmar.sanmarmanhunt.nonmain.Recipes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameRule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.awt.*;

public final class SanmarManhunt extends JavaPlugin implements Listener {




    @Override
    public void onEnable() {
        Recipes.registerRecipes(this);




        Scoreboard main_scoreboard = getServer().getScoreboardManager().getMainScoreboard();

        // Получаем команды или создаём, если их нет
        Team hunters = main_scoreboard.getTeam("hunters");
        if (hunters == null) {
            hunters = main_scoreboard.registerNewTeam("hunters");
        }

        Team speedrunners = main_scoreboard.getTeam("speedrunners");
        if (speedrunners == null) {
            speedrunners = main_scoreboard.registerNewTeam("speedrunners");
        }

        // Настройки hunters
        hunters.setAllowFriendlyFire(false);
        hunters.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
        hunters.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
        hunters.setColor(ChatColor.RED);

        // Настройки speedrunners
        speedrunners.setAllowFriendlyFire(false);
        speedrunners.setColor(ChatColor.GREEN);

        // Остальное
        getServer().setSpawnRadius(0);
        Bukkit.getWorld("world").setGameRule(GameRule.KEEP_INVENTORY, true);
        Bukkit.getWorld("world").setGameRule(GameRule.PLAYERS_SLEEPING_PERCENTAGE, 66);

        // Регистрируем события
        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("Plugin 'Sanmar Manhunt' is enabled!");

        // Создаём mainFunctions
        mainFunctions functions = new mainFunctions(this);

        // Создаём обработчик команд и регистрируем команду
        PluginCommands commands = new PluginCommands(this, functions);
        this.getCommand("manhunt").setExecutor(commands::onCommand);


        Nether nether = new Nether(this);
        Bukkit.getPluginManager().registerEvents(nether, this);


    }


    @EventHandler
    public void tick(ServerTickStartEvent event){
        Scoreboard board = getServer().getScoreboardManager().getMainScoreboard();
        Team hunters = board.getTeam("hunters");
        Team speedrunners = board.getTeam("speedrunners");

        for (Player player : getServer().getOnlinePlayers()) {
            if (hunters != null && hunters.hasEntry(player.getName())) player.setMaxHealth(30);
            if (speedrunners != null && speedrunners.hasEntry(player.getName())) player.setMaxHealth(20);
        }
    }

}
