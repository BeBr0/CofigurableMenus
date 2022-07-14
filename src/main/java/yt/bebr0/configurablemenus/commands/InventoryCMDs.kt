package yt.bebr0.configurablemenus.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import yt.bebr0.configurablemenus.Plugin
import yt.bebr0.configurablemenus.menu.MenuList
import yt.bebr0.configurablemenus.util.ChatUtil

class InventoryCMDs(plugin: Plugin): CommandExecutor {

    init {
        plugin.getCommand("flInv")?.setExecutor(this)
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            ChatUtil.sendMessage(sender, "Только игрок может выполнять эту команду!", true)
            return true;
        }

        if (args[0].equals("open", true)) {
            if (args.size < 2) {
                ChatUtil.sendMessage(sender, "Неверное количество аргументов!", true)
                return false;
            }

            val menu = MenuList.get(args[1])
            if (menu != null){
                menu.openInventory(sender, false)
                ChatUtil.sendMessage(sender, "Инвентарь открыт!", false)
                return true
            }

            ChatUtil.sendMessage(sender, "Такого инвентаря не существует!", true)
            return true
        }

        return false
    }


}