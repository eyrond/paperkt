package dev.eyrond.paperkt.brigadier

import com.destroystokyo.paper.brigadier.BukkitBrigadierCommandSource
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import dev.eyrond.paperkt.brigadier.dsl.LiteralArgument
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage

/**
 * Registers a brigadier command with the given [name].
 * @return The registered command.
 */
inline fun Brigadier.registerCommand(name: String, block: LiteralArgument.() -> Unit): BrigadierCommand {
    val dsl = LiteralArgument(plugin, LiteralArgumentBuilder.literal(name)).apply(block)
    return registerCommand(dsl)
}

/**
 * Get a string argument from the command context.
 */
fun CommandContext<*>.getStringArg(name: String): String = getArgument(name, String::class.java)

/**
 * Get an integer argument from the command context.
 */
fun CommandContext<*>.getIntArg(name: String): Int = getArgument(name, Int::class.java)

/**
 * Get a float argument from the command context.
 */
fun CommandContext<*>.getFloatArg(name: String): Float = getArgument(name, Float::class.java)

/**
 * Get a double argument from the command context.
 */
fun CommandContext<*>.getDoubleArg(name: String): Double = getArgument(name, Double::class.java)

/**
 * Get a boolean argument from the command context.
 */
fun CommandContext<*>.getBooleanArg(name: String): Boolean = getArgument(name, Boolean::class.java)

/**
 * Get a long argument from the command context.
 */
fun CommandContext<*>.getLongArg(name: String): Long = getArgument(name, Long::class.java)

/**
 * Respond to the command sender with the built message from [StringBuilder].
 * MiniMessage is used to parse the message into a component.
 */
inline fun CommandContext<BukkitBrigadierCommandSource>.respond(block: StringBuilder.() -> Unit) {
    respond(buildString(block))
}

/**
 * Respond to the command sender with the given [message].
 * MiniMessage is used to parse the message into a component.
 */
fun CommandContext<BukkitBrigadierCommandSource>.respond(message: String) {
    source.bukkitSender.sendMessage(MiniMessage.miniMessage().deserialize(message))
}

/**
 * Respond to the command sender with the given [component].
 */
fun CommandContext<BukkitBrigadierCommandSource>.respond(component: Component) {
    source.bukkitSender.sendMessage(component)
}
