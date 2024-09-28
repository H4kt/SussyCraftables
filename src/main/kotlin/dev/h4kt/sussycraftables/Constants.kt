package dev.h4kt.sussycraftables

import org.bukkit.Material

object Constants {

    const val NBT_BRUSH_RESULT = "sussycraftables:brush_result"

    val suspiciousMaterialsCounterparts = mapOf(
        Material.SUSPICIOUS_SAND to Material.SAND,
        Material.SUSPICIOUS_GRAVEL to Material.GRAVEL
    )

    val suspiciousMaterials = suspiciousMaterialsCounterparts.keys

}
