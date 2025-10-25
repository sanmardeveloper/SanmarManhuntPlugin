package com.sanmar.sanmarmanhunt.nonmain;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Iterator;

public class Recipes {

    public static void registerRecipes(JavaPlugin plugin) {
        for (Iterator<Recipe> it = Bukkit.recipeIterator(); it.hasNext();) {
            Recipe recipe = it.next();

            // Проверяем, что это ShapedRecipe или ShapelessRecipe
            if (recipe instanceof ShapedRecipe shaped && shaped.getResult().getType() == Material.ENDER_EYE) {
                NamespacedKey key = shaped.getKey();
                if (key != null) Bukkit.removeRecipe(key);
            }

            if (recipe instanceof ShapelessRecipe shapeless && shapeless.getResult().getType() == Material.ENDER_EYE) {
                NamespacedKey key = shapeless.getKey();
                if (key != null) Bukkit.removeRecipe(key);
            }
        }


        ItemStack enderEye = new ItemStack(Material.ENDER_EYE, 2); // выдаёт 2 штуки

        NamespacedKey key = new NamespacedKey(plugin, "custom_ender_eye");

        ShapelessRecipe recipe = new ShapelessRecipe(key, enderEye);
        recipe.addIngredient(Material.BLAZE_POWDER);
        recipe.addIngredient(Material.ENDER_PEARL);

        Bukkit.addRecipe(recipe);

        plugin.getLogger().info("Custom shapeless recipe for Ender Eye registered!");
    }
}
