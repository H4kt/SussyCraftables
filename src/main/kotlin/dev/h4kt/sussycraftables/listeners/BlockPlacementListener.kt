package dev.h4kt.sussycraftables.listeners

import de.tr7zw.nbtapi.NBT
import dev.h4kt.sussycraftables.Constants
import org.bukkit.block.BrushableBlock
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.inventory.ItemStack

class BlockPlacementListener : Listener {

    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent) {

        val item = event.itemInHand
        if (item.type !in Constants.suspiciousMaterials) {
            return
        }

        val blockState = event.blockPlaced.getState(false) as? BrushableBlock
            ?: return

        val brushResult = NBT.get<ItemStack?>(item) {
            it.getItemStack(Constants.NBT_BRUSH_RESULT)
        } ?: return

        blockState.run {
            setItem(brushResult)
            update()
        }

    }

}
