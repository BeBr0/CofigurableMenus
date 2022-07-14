package yt.bebr0.configurablemenus.event

import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryAction
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.persistence.PersistentDataType
import org.bukkit.scheduler.BukkitRunnable
import yt.bebr0.configurablemenus.Plugin
import yt.bebr0.configurablemenus.menu.Commands
import yt.bebr0.configurablemenus.menu.MenuEditorsList
import yt.bebr0.configurablemenus.menu.MenuList
import yt.bebr0.configurablemenus.menu.PersistentFields
import yt.bebr0.configurablemenus.util.ChatUtil

class Events: Listener{

    companion object {
        val messages: MutableMap<Player, String> = mutableMapOf()
    }

    @EventHandler
    fun onClick(event: InventoryClickEvent){
        if (MenuList.get(event.inventory) != null) {
            if (!MenuEditorsList.editors.contains(event.whoClicked)) {
                event.isCancelled = true

                if (event.currentItem != null) {
                    val dataContainer = event.currentItem!!.itemMeta.persistentDataContainer
                    if (dataContainer.has(NamespacedKey.fromString("cmd")!!, PersistentDataType.STRING)) {

                        if (dataContainer.get(PersistentFields.COMMAND.fieldName, PersistentDataType.STRING) != Commands.EXIT.name
                            && dataContainer.get(PersistentFields.COMMAND.fieldName, PersistentDataType.STRING) != Commands.EMPTY.name) {
                            if (event.whoClicked is Player)
                                (event.whoClicked as Player).performCommand(
                                    dataContainer.get(
                                        PersistentFields.COMMAND.fieldName
                                        , PersistentDataType.STRING
                                    )!!
                                )
                        }
                        else if (dataContainer.get(PersistentFields.COMMAND.fieldName, PersistentDataType.STRING) == Commands.EXIT.name)
                            event.whoClicked.closeInventory()
                    }
                }
            }
            else {
                if (event.action == InventoryAction.PICKUP_HALF) {
                    if (event.currentItem != null) {
                        val menu = MenuList.get(event.inventory)

                        messages[event.whoClicked as Player] = ""
                        val item = event.currentItem!!.clone()
                        val slot = event.slot

                        event.whoClicked.closeInventory()
                        object : BukkitRunnable() {
                            var ctr = 10
                            override fun run() {
                                ChatUtil.sendMessage(
                                    event.whoClicked,
                                    "Введи команду на предмет (exit, чтобы создать предмет для выхода из инвентаря, \\ - отмена). " +
                                            "Осталось $ctr секунд",
                                    false
                                )

                                ctr -= 1

                                if (messages[event.whoClicked as Player] != "") {
                                    if (messages[event.whoClicked as Player] != "\\") {
                                        menu?.addItem(
                                            item,
                                            slot,
                                            messages[event.whoClicked as Player]!!
                                        )

                                        menu?.openInventory(event.whoClicked as Player, true)
                                    }

                                    cancel()
                                }

                                if (ctr <= 0) {
                                    cancel()
                                }
                            }
                        }.runTaskTimer(Plugin.instance, 0L, 20L)
                    }
                }
            }
        }
    }

    @EventHandler
    fun onMessage(event: AsyncPlayerChatEvent){
        if (messages.contains(event.player)){
            messages[event.player] = event.message
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onClose(event: InventoryCloseEvent) {
        MenuEditorsList.editors.remove(event.player)
    }
}