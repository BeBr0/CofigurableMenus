package yt.bebr0.configurablemenus.menu

import org.bukkit.NamespacedKey
import org.bukkit.persistence.PersistentDataType

enum class PersistentFields(val fieldName: NamespacedKey) {

    COMMAND(NamespacedKey.fromString("cmd")!!)
}