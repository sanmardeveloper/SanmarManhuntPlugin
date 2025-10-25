package com.sanmar.sanmarmanhunt;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class mainFunctions {


    private final JavaPlugin plugin;
    private BossBar timer_a;
    private int timer_a_value = 15;

    public mainFunctions(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void manhuntStart(World world) {
        // Создаём валидный ключ
        timer_a = Bukkit.createBossBar(NamespacedKey.minecraft("timer_a"), "Фора 15 секунд", BarColor.PURPLE, BarStyle.SOLID);

        // Добавляем всех игроков
        for (Player p : Bukkit.getOnlinePlayers()) {
            timer_a.addPlayer(p);
        }
        timer_a.setVisible(true);

        // Таймер каждую секунду
        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            timer_a_value--;
            timer_a.setProgress(timer_a_value / 15.0); // прогресс от 1 до 0

            if (timer_a_value <= 0) {
                timer_a.setVisible(false);
                timer_a.removeAll();
                Bukkit.broadcastMessage("Фора закончилась! Манхант начался!");
            }
        }, 0L, 20L);


        Nether nether = new Nether((SanmarManhunt) plugin);
        nether.start_timer_nether();
    }
}
