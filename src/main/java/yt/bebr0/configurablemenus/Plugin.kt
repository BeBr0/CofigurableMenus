package yt.bebr0.configurablemenus;

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin;
import yt.bebr0.configurablemenus.commands.InventoryCMDs
import yt.bebr0.configurablemenus.commands.InventorySetup
import yt.bebr0.configurablemenus.event.Events
import yt.bebr0.configurablemenus.util.ConfigWorker

class Plugin: JavaPlugin() {

    companion object{
        lateinit var instance: Plugin
            private set
    }

    override fun onEnable(){
        instance = this

        ConfigWorker.readMenu()

        Bukkit.getPluginManager().registerEvents(Events(), this)

        InventoryCMDs(this)
        InventorySetup(this)
    }

    override fun onDisable() {
        ConfigWorker.writeMenu()
    }
}

