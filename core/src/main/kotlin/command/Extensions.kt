@file:Suppress("unused")

package dev.eyrond.paperkt.command

import dev.eyrond.paperkt.plugin.IKotlinPlugin
import org.bukkit.command.CommandSender

typealias TabCompleteFun = suspend (sender: CommandSender, label: String, args: List<String>) -> List<String>
typealias ExecuteFun = suspend (sender: CommandSender, label: String, args: List<String>) -> Boolean

/**
 * Creates a [KotlinCommand] from the given parameters and registers it to this plugin.
 * @param name The name of the command.
 * @param description The description of the command.
 * @param usageMessage The usage message of the command.
 * @param aliases The aliases of the command.
 * @param execute The execute function of the command.
 * @see dev.eyrond.paperkt.command.registerCommand
 */
inline fun IKotlinPlugin.command(
    name: String,
    description: String = "",
    usageMessage: String = "",
    aliases: List<String> = emptyList(),
    crossinline execute: ExecuteFun
): KotlinCommand {
    return object : KotlinCommand(this, name, description, usageMessage, aliases) {
        override suspend fun execute(
            sender: CommandSender,
            alias: String,
            args: List<String>
        ): Boolean = execute(sender, alias, args)
    }.apply { register() }
}

/**
 * Creates a [KotlinCommand] from the given parameters and registers it to this plugin.
 * @param name The name of the command.
 * @param description The description of the command.
 * @param usageMessage The usage message of the command.
 * @param aliases The aliases of the command.
 * @param tabComplete The tab complete function of the command.
 * @param execute The execute function of the command.
 * @see dev.eyrond.paperkt.command.registerCommand
 */
inline fun IKotlinPlugin.command(
    name: String,
    description: String = "",
    usageMessage: String = "",
    aliases: List<String> = emptyList(),
    crossinline tabComplete: TabCompleteFun,
    crossinline execute: ExecuteFun
): KotlinCommand {
    return object : KotlinCommand(this, name, description, usageMessage, aliases) {
        override suspend fun execute(
            sender: CommandSender,
            alias: String,
            args: List<String>
        ): Boolean = execute(sender, alias, args)

        override suspend fun tabComplete(
            sender: CommandSender,
            alias: String,
            args: List<String>
        ): List<String> = tabComplete(sender, alias, args)
    }.apply { register() }
}
