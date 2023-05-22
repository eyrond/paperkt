package dev.eyrond.paperkt.brigadier

import com.destroystokyo.paper.brigadier.BukkitBrigadierCommandSource
import com.mojang.brigadier.CommandDispatcher
import dev.eyrond.paperkt.brigadier.dsl.LiteralArgument
import dev.eyrond.paperkt.plugin.IKotlinPlugin

/**
 * Represents a brigadier proxy for a plugin.
 * This class is used to manage your brigadier-style commands in your plugin.
 */
interface Brigadier {

    /**
     * The plugin that owns this brigadier proxy.
     */
    val plugin: IKotlinPlugin

    /**
     * The brigadier command dispatcher for this plugin.
     */
    val dispatcher: CommandDispatcher<BukkitBrigadierCommandSource>

    /**
     * Registers a command with the brigadier dispatcher.
     */
    fun registerCommand(rootNode: LiteralArgument): BrigadierCommand

    /**
     * Unregisters a command from the brigadier dispatcher.
     */
    fun unregisterCommand(command: BrigadierCommand): Boolean
}
