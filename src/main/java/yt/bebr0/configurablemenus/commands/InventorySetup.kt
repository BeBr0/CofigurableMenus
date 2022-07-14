package yt.bebr0.configurablemenus.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import yt.bebr0.configurablemenus.Plugin
import yt.bebr0.configurablemenus.menu.Menu
import yt.bebr0.configurablemenus.menu.MenuList
import yt.bebr0.configurablemenus.util.ChatUtil

class InventorySetup(plugin: Plugin): CommandExecutor {

    init {
        plugin.getCommand("flInvSetup")?.setExecutor(this)
    }

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player){
            ChatUtil.sendMessage(sender, "Только игрок может выполнять эту команду!", true)
            return true;
        }

        if (!sender.isOp){
            ChatUtil.sendMessage(sender, "Недостаточно прав!", true)
            return true;
        }

        if (args[0].equals("create", true)){
            if (args.size < 3){
                ChatUtil.sendMessage(sender, "Неверное количество аргументов!", true)
                return false;
            }

            val size: Int

            try {
                size = args[2].toInt()
            }
            catch (e: java.lang.NumberFormatException){
                ChatUtil.sendMessage(sender, "Неверный порядок аргументов!", true)
                return false;
            }

            Menu( args[1], size).openInventory(sender, true)
            ChatUtil.sendMessage(sender, "Инвентарь создан!", false)
            return true;
        }
        else if (args[0].equals("edit", true)) {
            if (args.size < 2) {
                ChatUtil.sendMessage(sender, "Неверное количество аргументов!", true)
                return false;
            }

            val menu = MenuList.get(args[1])
            if (menu != null){
                menu.openInventory(sender, true)
                ChatUtil.sendMessage(sender, "Инвентарь открыт для изменения!", false)
                return true
            }

            ChatUtil.sendMessage(sender, "Такого инвентаря не существует!", true)
            return true;
        }

        return false;
    }


}