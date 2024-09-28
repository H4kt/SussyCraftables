package dev.h4kt.sussycraftables

import dev.h4kt.sussycraftables.listeners.BlockPlacementListener
import dev.h4kt.sussycraftables.listeners.CraftResultListener
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.RecipeChoice
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

            val brushResults = Material.entries
                .asSequence()
                .filter { it.isItem }
                .filterNot { it.isEmpty }
                .filter { it !in Constants.suspiciousMaterialsCounterparts.values }
                .toList()

            Constants.suspiciousMaterialsCounterparts.forEach { (base, counterpart) ->
                registerRecipe(base, counterpart, brushResults)
            }

        }

        logger.info("Finished registering recipes in $timeTook")

        server.updateRecipes()

    }

    private fun registerRecipe(
        base: Material,
        counterpart: Material,
        brushResults: List<Material>
    ) {

        val key = NamespacedKey.fromString(base.name.lowercase(), this)
            ?: return

        val recipe = ShapelessRecipe(key, ItemStack(base)).apply {
            addIngredient(counterpart)
            addIngredient(RecipeChoice.MaterialChoice(brushResults))
        }

        server.addRecipe(recipe)
        recipeKeys += key

    }

}
