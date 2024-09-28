package dev.h4kt.sussycraftables.listeners

import de.tr7zw.nbtapi.NBT
import dev.h4kt.sussycraftables.Constants
import dev.h4kt.sussycraftables.SussyCraftables
import dev.h4kt.sussycraftables.extensions.find
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.CraftItemEvent
import org.bukkit.inventory.ShapelessRecipe

class CraftResultListener(
    private val plugin: SussyCraftables
) : Listener {

    @EventHandler
    fun onCraftResult(event: CraftItemEvent) {

        val recipe = event.recipe
        if (recipe !is ShapelessRecipe) {
            return
        }

        if (recipe.key !in plugin.recipeKeys) {
            return
        }

        val clickedInventory = event.clickedInventory
            ?: return

        val brushResult = clickedInventory.find { it.type !in Constants.suspiciousMaterials }
            ?: return

        val recipeResult = recipe.result.apply {
            NBT.modify(this) {
                it.setItemStack(Constants.NBT_BRUSH_RESULT, brushResult)
            }
        }

        event.currentItem = recipeResult

    }

}
