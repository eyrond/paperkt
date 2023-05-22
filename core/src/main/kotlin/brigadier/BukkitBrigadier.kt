package dev.eyrond.paperkt.brigadier

import com.destroystokyo.paper.brigadier.BukkitBrigadierCommandSource
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.tree.LiteralCommandNode
import dev.eyrond.paperkt.brigadier.dsl.LiteralArgument
import dev.eyrond.paperkt.plugin.IKotlinPlugin
import org.bukkit.craftbukkit.v1_19_R3.CraftServer

@Suppress("UNCHECKED_CAST")
internal class BukkitBrigadier(override val plugin: IKotlinPlugin) : Brigadier {

    override val dispatcher =
        (plugin.server as CraftServer).server.vanillaCommandDispatcher.dispatcher as CommandDispatcher<BukkitBrigadierCommandSource>

    override fun registerCommand(rootNode: LiteralArgument): BrigadierCommand {
        val node = dispatcher.register(rootNode.builder)
        return BukkitBrigadierCommand(this, node as LiteralCommandNode<BukkitBrigadierCommandSource>)
    }

    override fun unregisterCommand(command: BrigadierCommand): Boolean {
        val node = command.rootNode
        return dispatcher.root.children.removeIf { it === node }
    }
}
