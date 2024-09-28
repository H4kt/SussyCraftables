package dev.h4kt.sussycraftables.extensions

import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

fun Inventory.find(predicate: (ItemStack) -> Boolean): ItemStack? {

    for (i in 0 until size) {

        val item = getItem(i)
        if (item != null && predicate(item)) {
            return item
        }

    }

    return null
}
