package dev.eyrond.paperkt.brigadier.dsl

import com.destroystokyo.paper.brigadier.BukkitBrigadierCommandSource
import com.mojang.brigadier.Command
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.ArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder
import com.mojang.brigadier.context.CommandContext
import dev.eyrond.paperkt.plugin.IKotlinPlugin
import kotlinx.coroutines.launch
import java.util.function.Predicate

typealias BukkitCommandContext = CommandContext<BukkitBrigadierCommandSource>
typealias BukkitAbstractArgumentBuilder = ArgumentBuilder<BukkitBrigadierCommandSource, out ArgumentBuilder<BukkitBrigadierCommandSource, *>>

/**
 * Represents a brigadier argument.
 * @property plugin The plugin that owns this argument.
 */
sealed class Argument<T : BukkitAbstractArgumentBuilder>(val plugin: IKotlinPlugin, builder: T) {

    /**
     * The brigadier builder for this argument.
     */
    var builder: T = builder
        protected set

    /**
     * Sets execution logic for this argument with suspend support.
     * @return This argument.
     */
    @BrigadierDSLMarker
    inline fun executesSuspend(crossinline command: suspend BukkitCommandContext.() -> Int) = apply {
        builder.executes {
            var result = Command.SINGLE_SUCCESS
            plugin.coroutineScope.launch {
                result = command(it)
            }
            result
        }
    }

    /**
     * Sets execution logic for this argument.
     * @return This argument.
     */
    @BrigadierDSLMarker
    inline fun executes(crossinline command: BukkitCommandContext.() -> Int) = apply {
        builder.executes { command(it) }
    }

    /**
     * Sets execution logic for this argument.
     * @return This argument.
     */
    @BrigadierDSLMarker
    fun executes(command: Command<BukkitBrigadierCommandSource>) = apply {
        builder.executes(command)
    }

    /**
     * Sets a requirement for this argument.
     * @return This argument.
     */
    @BrigadierDSLMarker
    fun requires(predicate: Predicate<BukkitBrigadierCommandSource>) = apply {
        builder.requires(predicate)
    }

    /**
     * Adds a literal argument following after this argument.
     * @return This argument.
     */
    @BrigadierDSLMarker
    inline fun literal(
        name: String,
        block: LiteralArgument.() -> Unit
    ) = then(LiteralArgument(plugin, LiteralArgumentBuilder.literal(name)).apply(block))

    /**
     * Adds a required argument following after this argument.
     * @return This argument.
     */
    @BrigadierDSLMarker
    inline fun <A> argument(
        name: String,
        type: ArgumentType<A>,
        block: RequiredArgument<A>.() -> Unit
    ) = then(RequiredArgument(plugin, RequiredArgumentBuilder.argument(name, type)).apply(block))

    /**
     * Adds the [argument] following after this argument.
     * @return This argument.
     */
    @Suppress("UNCHECKED_CAST")
    fun then(argument: Argument<out BukkitAbstractArgumentBuilder>) = apply {
        builder = builder.then(argument.builder) as T
    }
}
