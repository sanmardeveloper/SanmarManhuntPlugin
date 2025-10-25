package com.sanmar.sanmarmanhunt;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class Nether implements Listener {

    private final SanmarManhunt plugin;

    public Nether(SanmarManhunt plugin) {
        this.plugin = plugin;
    }


    static String name_timer = "Nether will be open in ";
    static Scoreboard main = Bukkit.getServer().getScoreboardManager().getMainScoreboard();
    static Objective timer_b = main.getObjective("timer_b");
    static int default_timer_b = 60;


    public void start_timer_nether(){
        if (timer_b == null) {
            timer_b = main.registerNewObjective("timer_b", "dummy");
            timer_b.setDisplaySlot(DisplaySlot.SIDEBAR);
            timer_b.getScore(name_timer).setScore(default_timer_b);
        }

        BukkitTask[] taskHolder = new BukkitTask[1]; // обёртка, чтобы замыкание имело доступ

        taskHolder[0] = Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            if (timer_b.getScore(name_timer).getScore() <= default_timer_b) {
                timer_b.getScore(name_timer).setScore(timer_b.getScore(name_timer).getScore() - 1);
                Bukkit.getLogger().info(name_timer + timer_b.getScore(name_timer).getScore());
            } else {
                taskHolder[0].cancel();
            }
        }, 0L, 20L);



    }

    @EventHandler
    public void onPortalCreate(PortalCreateEvent event) {
        if (timer_b.getScore(name_timer).getScore() > 0) {
            event.setCancelled(true);
        } else event.setCancelled(false);
    }
}
