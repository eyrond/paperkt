package dev.eyrond.paperkt.brigadier

import com.destroystokyo.paper.brigadier.BukkitBrigadierCommandSource
import com.mojang.brigadier.tree.LiteralCommandNode

/**
 * Represents a registered brigadier command.
 */
interface BrigadierCommand {

    /**
     * The root node of this command.
     */
    val rootNode: LiteralCommandNode<BukkitBrigadierCommandSource>

    /**
     * Unregisters this command from the brigadier dispatcher.
     * @return true if the command was unregistered, false if it was not registered.
     */
    fun unregister(): Boolean
}
