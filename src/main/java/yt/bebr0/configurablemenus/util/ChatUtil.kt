package yt.bebr0.configurablemenus.util

import org.bukkit.ChatColor
import org.bukkit.Utility
import org.bukkit.command.CommandSender
import yt.bebr0.configurablemenus.Plugin

object ChatUtil {

    private val prefix = "&5[" + Plugin.instance.name + "]: &o&6"
    private val errorPrefix = "&5[" + Plugin.instance.name + "]: &o&4"

    fun sendMessage(player: CommandSender, msg: String, isError: Boolean) {
        if (!isError)
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + msg))
        else
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', errorPrefix + msg))
    }
}