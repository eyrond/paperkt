package dev.eyrond.paperkt.brigadier

import com.destroystokyo.paper.brigadier.BukkitBrigadierCommandSource
import com.mojang.brigadier.tree.LiteralCommandNode

internal class BukkitBrigadierCommand(
    private val brigadier: Brigadier,
    override val rootNode: LiteralCommandNode<BukkitBrigadierCommandSource>,
) : BrigadierCommand {

    override fun unregister(): Boolean {
        return brigadier.unregisterCommand(this)
    }
}
