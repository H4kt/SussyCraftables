package dev.h4kt.sussycraftables

import dev.h4kt.sussycraftables.listeners.BlockPlacementListener
import dev.h4kt.sussycraftables.listeners.CraftResultListener
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapelessRecipe
import org.bukkit.plugin.java.JavaPlugin
import kotlin.time.measureTime

class SussyCraftables : JavaPlugin() {

    var recipeKeys: Set<NamespacedKey> = emptySet()
        private set

    override fun onEnable() {

        server.pluginManager.run {
            registerEvents(BlockPlacementListener(), this@SussyCraftables)
            registerEvents(CraftResultListener(this@SussyCraftables), this@SussyCraftables)
        }

        logger.info("Registering recipes")

        val timeTook = measureTime {
            Material.entries
                .asSequence()
                .filter { it.isItem }
                .filterNot { it.isEmpty }
                .filter { it !in Constants.suspiciousMaterials }
                .forEach {
                    Constants.suspiciousMaterials.forEach { base ->
                        registerRecipe(base, it)
                    }
                }
        }

        logger.info("Finished registering recipes in $timeTook")

        server.updateRecipes()

    }

    private fun registerRecipe(
        base: Material,
        brushResult: Material
    ) {

        val key = NamespacedKey.fromString("${brushResult}_$base".lowercase(), this)
            ?: return

        val recipe = ShapelessRecipe(key, ItemStack(base)).apply {
            addIngredient(base)
            addIngredient(brushResult)
        }

        server.addRecipe(recipe)
        recipeKeys += key

    }

}
