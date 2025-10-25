package com.sanmar.sanmarmanhunt;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginCommands {

    private final JavaPlugin plugin;
    private final mainFunctions functions;

    public PluginCommands(JavaPlugin plugin, mainFunctions functions) {
        this.plugin = plugin;
        this.functions = functions;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!command.getName().equalsIgnoreCase("manhunt")) return false;

        if (args.length == 0) {
            sender.sendMessage("Использование: /manhunt <start|stop>");
            return true;
        }

        String subCommand = args[0].toLowerCase();

        switch (subCommand) {
            case "start" -> {
                sender.getServer().broadcastMessage("Манхант запущен! Фора 15 секунд!");
                // Берём мир по имени "world" и запускаем таймер
                World world = plugin.getServer().getWorld("world");
                if (world != null) {
                    functions.manhuntStart(world);
                } else {
                    sender.sendMessage("Мир 'world' не найден!");
                }
            }
            case "stop" -> sender.getServer().broadcastMessage("Манхант остановлен!");
            default -> sender.sendMessage("Неизвестная подкоманда. Использование: /manhunt <start|stop>");
        }

        return true;
    }
}
