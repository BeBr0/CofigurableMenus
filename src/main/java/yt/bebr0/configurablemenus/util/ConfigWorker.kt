package yt.bebr0.configurablemenus.util

import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import yt.bebr0.configurablemenus.Plugin
import yt.bebr0.configurablemenus.menu.Menu
import yt.bebr0.configurablemenus.menu.MenuList
import java.io.File

object ConfigWorker {

    private val path = Plugin.instance.dataFolder.absolutePath + "/menus"

    fun writeMenu() {
        val file = File(path)
        if (!file.exists())
            file.mkdirs()

        for (menu: Menu in MenuList.menus) {
            val arenaFile = File(path, menu.name + ".yml")

            val config = YamlConfiguration()

            config.set("name", menu.name)
            config.set("size", menu.size)

            val section = config.createSection("items")
            for (i in 0 until menu.inventory.size) {
                val itemSection = section.createSection(i.toString())

                val item = menu.inventory.getItem(i)

                if (item != null) {
                    itemSection.set("item", item)

                    itemSection.set("cmd", item.itemMeta.persistentDataContainer.get(NamespacedKey.fromString("cmd")!!, PersistentDataType.STRING))
                }
            }

            config.save(arenaFile)
        }
    }

    fun readMenu() {
        val file = File(path)
        if (!file.exists())
            file.mkdirs()

        for (f: File in file.listFiles()!!) {
            val config = YamlConfiguration.loadConfiguration(f)

            val menu = Menu(config.getString("name")!!, config.getInt("size"))

            val section = config.getConfigurationSection("items")

            if (section != null) {
                for (key: String in section.getKeys(false)) {
                    val itemSection = section.getConfigurationSection(key)

                    if (itemSection != null) {
                        if (itemSection.contains("cmd")) {
                            menu.addItem(
                                itemSection.getItemStack("item")!!,
                                key.toInt(),
                                itemSection.getString("cmd")!!
                            )
                        }
                        else if (itemSection.contains("item")) {
                            menu.addItem(
                                itemSection.getItemStack("item")!!,
                                key.toInt(),
                                ""
                            )
                        }
                    }
                }
            }
        }
    }
}