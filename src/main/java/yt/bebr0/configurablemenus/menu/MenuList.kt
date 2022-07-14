package yt.bebr0.configurablemenus.menu

import org.bukkit.inventory.Inventory

object MenuList {

    val menus: MutableList<Menu> = mutableListOf()

    fun get(inventory: Inventory): Menu? {
        for (menu: Menu in menus){
            if (menu.inventory == inventory){
                return menu
            }
        }

        return null
    }

    fun get(name: String): Menu? {
        for (menu: Menu in menus){
            if (menu.name == name){
                return menu
            }
        }

        return null
    }
}