package yt.bebr0.configurablemenus.menu

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType
import yt.bebr0.configurablemenus.util.ChatUtil

class Menu(val name: String, val size: Int) {

    val inventory: Inventory = Bukkit.createInventory(null, size, Component.text(name))

    init {
        MenuList.menus.add(this)
    }

    fun openInventory(player: Player, edit: Boolean) {
        ChatUtil.sendMessage(player, "$name открыт!", false)
        player.openInventory(inventory)

        if (edit)
            MenuEditorsList.editors.add(player)
    }

    fun addItem(item: ItemStack, slot: Int, cmd: String){
        inventory.setItem(slot, null)
        val itemMeta: ItemMeta = item.itemMeta

        itemMeta.persistentDataContainer.set(PersistentFields.COMMAND.fieldName, PersistentDataType.STRING, cmd)

        item.itemMeta = itemMeta

        inventory.setItem(slot, item)
    }
}

