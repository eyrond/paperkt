package dev.eyrond.paperkt.brigadier

import com.destroystokyo.paper.brigadier.BukkitBrigadierCommandSource
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.tree.LiteralCommandNode
import dev.eyrond.paperkt.brigadier.dsl.LiteralArgument
import dev.eyrond.paperkt.plugin.IKotlinPlugin
import kotlin.reflect.KClass
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.jvmName

@Suppress("UNCHECKED_CAST")
internal class BukkitBrigadier(override val plugin: IKotlinPlugin) : Brigadier {

    override val dispatcher = reflectDispatcher()

    private fun reflectDispatcher(): CommandDispatcher<BukkitBrigadierCommandSource> {
        val craftServer = plugin.server
        val vanillaServer = craftServer::class.memberFunctions.first {
            it.name == "getServer"
        }.call(craftServer)!!
        val vanillaCommandDispatcher = vanillaServer::class.memberProperties.first {
            it.name == "vanillaCommandDispatcher"
        }.call(vanillaServer)!!
        val brigadierDispatcherProperty = vanillaCommandDispatcher::class.memberProperties.first {
            (it.returnType.classifier as KClass<*>).jvmName == CommandDispatcher::class.jvmName
        }
        brigadierDispatcherProperty.isAccessible = true
        val brigadierDispatcher = brigadierDispatcherProperty.call(vanillaCommandDispatcher)
        brigadierDispatcherProperty.isAccessible = false
        return brigadierDispatcher as CommandDispatcher<BukkitBrigadierCommandSource>
    }

    override fun registerCommand(rootNode: LiteralArgument): BrigadierCommand {
        val node = dispatcher.register(rootNode.builder)
        return BukkitBrigadierCommand(this, node as LiteralCommandNode<BukkitBrigadierCommandSource>)
    }

    override fun unregisterCommand(command: BrigadierCommand): Boolean {
        val node = command.rootNode
        return dispatcher.root.children.removeIf { it === node }
    }
}
